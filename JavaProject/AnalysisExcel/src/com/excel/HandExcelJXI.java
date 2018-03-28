package com.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.RowsExceededException;

/**
 * JXI包只能处理2003及之前版本Excel
 * @author JOE
 *
 */
public class HandExcelJXI {

	public void handleExcel(File sourcefile) {
		jxl.Workbook workbook = null;
		// 第一步：创建Workbook（术语：工作薄）
		try {
			// 方式一，从输入流创建Workbook读取excel数据表
			InputStream is = new FileInputStream(sourcefile);
			workbook = Workbook.getWorkbook(is);
			// 方式二，直接从本地文件（.xls）创建Workbook
			workbook = Workbook.getWorkbook(new File("excelfile"));
			// 方式三，参数对象传入（.xls）创建Workbook
			workbook = Workbook.getWorkbook(sourcefile);

			// 第二步：访问sheet。
			Sheet rs = workbook.getSheet(0);

			// 第三步：访问单元格cell
			Cell c00 = rs.getCell(0, 0);
			@SuppressWarnings("unused")
			String strc00 = c00.getContents();

			/* 操作数据按单元格中的内容类型  */
			if (c00.getType() == CellType.LABEL) {
				LabelCell labelc00 = (LabelCell) c00;
				strc00 = labelc00.getString();
			}
			if (c00.getType() == CellType.NUMBER){
				NumberCell numc10 = (NumberCell) c00;
				@SuppressWarnings("unused")
				double strc10 = numc10.getValue();
			}
			if (c00.getType() == CellType.DATE) {
				DateCell datec11 = (DateCell) c00;
				@SuppressWarnings("unused")
				Date strc11 = datec11.getDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			workbook.close();
		}
	}
	
	/**
	 * 读取Excel文件,利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄
	 * 
	 * @param filePath
	 */
	public void readExcel(String filePath) {

		jxl.Workbook rdworkbook = null;

		try {
			InputStream instream = new FileInputStream(filePath);
			rdworkbook = Workbook.getWorkbook(instream);

			// 读取第一个Sheet工作表,从下标开始
			Sheet rdsheet = rdworkbook.getSheet(0);
			/* 按文件名方式读取 */
			// Sheet rdsheet = rdworkbook.getSheet("sheetName");

			int rsColumns = rdsheet.getColumns();
			int rsRows = rdsheet.getRows();

			// 读取Excel中单元格内容
			for (int i = 0; i < rsRows; i++) {
				for (int j = 0; j < rsColumns; j++) {

					// 通用的获取cell值的方式,返回字符串
					Cell cell = rdsheet.getCell(i, j);
					String value = cell.getContents();
					System.out.println(value);

					if (cell.getType() == CellType.LABEL) {
						LabelCell labelCell = (LabelCell) cell;
						value = labelCell.getString();
					}

				}
			}

			// WritableWorkbook wrWorkbook = Workbook.createWorkbook
			// (new File(targetfile));

			// 利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄
			jxl.write.WritableWorkbook wrWorkbook = Workbook.createWorkbook(
					new File("Other_fileName.xls"), rdworkbook);
			jxl.write.WritableSheet wrSheet = wrWorkbook.getSheet(0);
			jxl.write.WritableCell wrCell = wrSheet.getWritableCell(0, 0);

			// 判断单元格的类型, 做出相应的转化
			if (wrCell.getType() == CellType.LABEL) {
				Label label = (Label) wrCell;
				label.setString("new value");
			}
			// 写入Excel对象
			wrWorkbook.write();
			wrWorkbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rdworkbook.close();
		}
	}

	public void writeExcel(OutputStream outStream) throws RowsExceededException {
		try {
			WritableWorkbook wwb = Workbook.createWorkbook(outStream);

			// 创建Excel工作表 指定名称和位置
			WritableSheet writesheet = wwb.createSheet("target Sheet name", 0);

			// 1.添加Label对象
			Label label = new Label(0, 0, "target value");
			writesheet.addCell(label);

			WritableFont cellfont = new WritableFont(WritableFont.TIMES, 18,
					WritableFont.BOLD, true);
			WritableCellFormat cellformat = new WritableCellFormat(cellfont);
			Label labelf = new Label(1, 0, "this is a label test", cellformat);
			writesheet.addCell(labelf);

			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.DARK_YELLOW);
			WritableCellFormat wcellformat = new WritableCellFormat(wfc);
			Label labelwithFormat = new Label(1, 0, "Ok", wcellformat);
			writesheet.addCell(labelwithFormat);

			// 2.添加Number对象
			Number labelN = new Number(0, 1, 3.1415926);
			writesheet.addCell((WritableCell) labelN);

			// 添加带有formatting的Number对象
			NumberFormat nf = new NumberFormat("#.##");
			WritableCellFormat wcfN = new WritableCellFormat(nf);
			Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			writesheet.addCell(labelNF);

			// 3.添加Boolean对象
			jxl.write.Boolean labelB = new jxl.write.Boolean(0, 2, true);
			writesheet.addCell(labelB);
			jxl.write.Boolean labelB1 = new jxl.write.Boolean(1, 2, false);
			writesheet.addCell(labelB1);

			// 4.添加DateTime对象
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3,
					new java.util.Date());
			writesheet.addCell(labelDT);

			// 5.添加带有formatting的DateFormat对象
			DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
			WritableCellFormat wcfDF = new WritableCellFormat(df);
			DateTime labelDTF = new DateTime(1, 3, new java.util.Date(), wcfDF);
			writesheet.addCell(labelDTF);

			// 6.添加图片对象,jxl只支持png格式图片
			File image = new File("f:\\1.png");
			WritableImage wimage = new WritableImage(0, 4, 6, 17, image);
			writesheet.addImage(wimage);

			// 7.写入工作表
			wwb.write();
			wwb.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modifyExcel(File file1, File file2) {

		readExcel("F:/红楼人物.xls");

		// 输出EXCEL
		File filewrite = new File("F:/红楼人物2.xls");
		try {
			Workbook rwb = Workbook.getWorkbook(filewrite);
			WritableWorkbook writebook = Workbook.createWorkbook(file2, rwb);
			WritableFont writefont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false,

					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			WritableCellFormat wcfFC = new WritableCellFormat(writefont);
			WritableSheet ws = writebook.getSheet(0);
			WritableCell wc = ws.getWritableCell(0, 0);

			// 判断单元格的类型,做出相应的转换
			if (wc.getType() == CellType.LABEL) {
				Label labelCF = new Label(0, 0, "人物（新）", wcfFC);
				ws.addCell(labelCF);
				// Label label = (Label)wc;
				// label.setString("被修改");
			}
			writebook.write();
			writebook.close();
			rwb.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
