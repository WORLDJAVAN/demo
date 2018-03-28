package com.excel;

import java.io.PrintStream;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandBgExcelSax extends DefaultHandler {
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER
	};
	
	private final PrintStream output;
	//private final int minColumnCount;
	private final DataFormatter formatter;
	private xssfDataType nextDataType;
	
	private StylesTable stylesTable;
	private ReadOnlySharedStringsTable sharedStringsTable;

	private int thisColumn = -1;
	private int lastColumnNumber = -1;
	private int countrows = 0;
//	private int minColumns; 	
	private short formatIndex;
	
	private boolean vIsOpen;
	private String formatString;
	private StringBuffer value;
	
/**
 * 
 * @param styles
 * @param strings
 * @param cols
 * @param target
 */
	public HandBgExcelSax(StylesTable styles, ReadOnlySharedStringsTable strings, int cols, PrintStream target) {
		this.stylesTable = styles;
		this.sharedStringsTable = strings;
	//	this.minColumnCount = cols;
		this.output = target;
		this.value = new StringBuffer();
		this.nextDataType = xssfDataType.NUMBER;
		this.formatter = new DataFormatter();
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		if ("inlineStr".equals(name) || "v".equals(name)) {
			vIsOpen = true;
			value.setLength(0);
		}

		else if ("c".equals(name)) {
			String r = attributes.getValue("r");
			int firstDigit = -1;
			for (int c = 0; c < r.length(); ++c) {
				if (Character.isDigit(r.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			thisColumn = nameToColumn(r.substring(0, firstDigit)); 
			
			this.nextDataType = xssfDataType.NUMBER;
			this.formatIndex = -1;
			this.formatString = null;
			String cellType = attributes.getValue("t");
			String cellStyleStr = attributes.getValue("s");
			if ("b".equals(cellType))
				nextDataType = xssfDataType.BOOL;
			else if ("e".equals(cellType))
				nextDataType = xssfDataType.ERROR;
			else if ("inlineStr".equals(cellType))
				nextDataType = xssfDataType.INLINESTR;
			else if ("s".equals(cellType))
				nextDataType = xssfDataType.SSTINDEX;
			else if ("str".equals(cellType))
				nextDataType = xssfDataType.FORMULA;
			else if (cellStyleStr != null) {
				int styleIndex = Integer.parseInt(cellStyleStr);
				XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
				this.formatIndex = style.getDataFormat();
				this.formatString = style.getDataFormatString();
				if (this.formatString == null)
					this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
			}
		}
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		String thisStr = null;

		if ("v".equals(name)) {	
			switch (nextDataType) {
			case BOOL:
				char first = value.charAt(0);
				thisStr = first == '0' ? "FALSE" : "TRUE";
				break;
			case ERROR:
				thisStr = "\"ERROR:" + value.toString() + '"';
				break;
			case FORMULA:
				//thisStr = '"' + value.toString() + '"';
				thisStr =  value.toString() ;
				break;
			case INLINESTR:
				XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
				//thisStr = '"' + rtsi.toString() + '"';
				thisStr =  rtsi.toString() ;
				break;
			case SSTINDEX:
				String sstIndex = value.toString();
				try {
					int idx = Integer.parseInt(sstIndex);
					XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
					//thisStr = '"' + rtss.toString() + '"';
					thisStr = rtss.toString();
				} catch (NumberFormatException ex) {
					output.println("Failed to parse SST index '" + sstIndex
							+ "': " + ex.toString());
				}
				break;

			case NUMBER:
				String n = value.toString();
				if (this.formatString != null)
					thisStr = formatter.formatRawCellContents(
							Double.parseDouble(n), this.formatIndex,
							this.formatString);
				else
					thisStr = n;
				break;
			default:
				thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
				break;
			}

			if (lastColumnNumber == -1) {
				lastColumnNumber = 0;
			}
			
			//设置Cell单元分割符号
			for (int i = lastColumnNumber; i < thisColumn; ++i) {
				output.print(',');
			}
			
			//当前Cell值
			output.print(thisStr);
			
			if (thisColumn > -1) {
				lastColumnNumber = thisColumn;
				
			}
		} else if ("row".equals(name)) {  // 如果标签名称为 row ，这说明已到行尾
			
			/*if (minColumns > 0) {
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				for (int i = lastColumnNumber; i < (this.minColumnCount); i++) {
					output.print(',');
				}
			}	*/
			
			output.println();
			output.println("第"+countrows++ +"：行记录");
			lastColumnNumber = -1;
		}
	} 
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (vIsOpen) value.append(ch, start, length);
	}
	
	private int nameToColumn(String name) {
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}    
}
