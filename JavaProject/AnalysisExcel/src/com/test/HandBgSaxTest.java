package com.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.excel.HandBgExcelSax;

public class HandBgSaxTest {
	private OPCPackage xlsxPackage;
	private int minColumns;
	private PrintStream output;

	public static void main(String[] args) {
		File xlsxFile = new File("C:\\Users\\long\\Desktop\\´ý´¦Àí\\test.xlsx");
		if (!xlsxFile.exists()) {
			System.err
					.println("Not found or not a file: " + xlsxFile.getPath());
			return;
		}
		int minColumns = -1;
		try {
			OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
			HandBgSaxTest xlsx2csv = new HandBgSaxTest(p, System.out,minColumns);
			xlsx2csv.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HandBgSaxTest(OPCPackage pkg, PrintStream output, int minColumns) {
		this.xlsxPackage = pkg;
		this.output = output;
		this.minColumns = minColumns;
	}

	public void process() throws IOException, OpenXML4JException,
			ParserConfigurationException, SAXException {
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(
				this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader
				.getSheetsData();
		int index = 0;
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			String sheetName = iter.getSheetName();
			this.output.println();
			this.output.println(sheetName + " [index=" + index + "]:");
			processSheet(styles, strings, stream);
			stream.close();
			++index;
		}
	}
	
	public void processSheet(StylesTable styles,
			ReadOnlySharedStringsTable strings, InputStream sheetInputStream)
			throws IOException, ParserConfigurationException, SAXException {
		
		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		ContentHandler handler = new HandBgExcelSax(styles, strings,
				this.minColumns, this.output);
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
	}

}
