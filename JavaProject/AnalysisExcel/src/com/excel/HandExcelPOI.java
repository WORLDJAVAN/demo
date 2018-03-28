package com.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class HandExcelPOI {
	
	/**
	 * poi 读取excel 支持2003 --2007 及以上文件
	 * 
	 * @author sunny
	 * @version V 2.0
	 * @CreatTime 2013-11-19 @
	 */
	 /** Workbook writeWB = new SXSSFWorkbook();
      * Sheet writeSheet = writeWB.createSheet(); 15W、20W正常 写操作
      * 
      * FileOutputStream output = new FileOutputStream(new File(xls_write_Address));  //读取的文件路径   
      * SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数据，以免内存溢出，其余写入 硬盘
      */

		/**
		 * 合并方法，读取excel文件
		 * 根据文件名自动识别读取方式
		 * 支持97-2013格式的excel文档
		 * 
		 * @param fileName 上传文件名
		 * @param file 上传的文件
		 * @return 返回列表内容格式：
		 *  每一行数据都是以对应列的表头为key 内容为value 比如 excel表格为：
		 * ===============
		 *  A | B | C | D
		 * ===|===|===|===
		 *  1 | 2 | 3 | 4
		 * ---|---|---|--- 
		 *  a | b | c | d
		 * ---------------
		 * 返回值 map：
		 *   map1:   A:1 B:2 C:3 D:4
		 *   map2:   A:a B:b C:d D:d
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public List<Map> readExcel(String fileName,MultipartFile file) throws Exception{
			//准备返回值列表
			List<Map> valueList=new ArrayList<Map>();
            //String tempSavePath="tmp";
			//缓存文件目录的文件夹名称（struts用）
	        String filepathtemp="/mnt/b2b/tmp";//缓存文件目录
	        String tmpFileName= System.currentTimeMillis()+"."+getExtensionName(fileName);
	        String ExtensionName=getExtensionName(fileName);
            //String filepathtemp= ServletActionContext.getServletContext().getRealPath(tempSavePath);
	        //strut获取项目路径
		    File filelist = new File(filepathtemp);
			if  (!filelist .exists()  && !filelist .isDirectory())      
			{       
				filelist .mkdir();    
			} 
			String filePath = filepathtemp+System.getProperty("file.separator")+tmpFileName;
	        File tmpfile = new File(filePath);
		    //拷贝文件到服务器缓存目录（在项目下）
            //copy(file,tmpfile); --> stuts用的方法
	        copy(file, filepathtemp,tmpFileName);//spring mvc用的方法
			
			if(ExtensionName.equalsIgnoreCase("xls")){
				valueList=readExcel2003(filePath);
			}else if(ExtensionName.equalsIgnoreCase("xlsx")) {
				valueList=readExcel2007(filePath);
			}
			//删除缓存文件
	        tmpfile.delete();
	        return valueList;
		 			
		}
		
		/**
		 * 读取97-2003格式
		 * @param filePath 文件路径
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public  List<Map> readExcel2003(String filePath) throws IOException{
			//返回结果集
			List<Map> valueList=new ArrayList<Map>();
	        FileInputStream fis=null;
			try {
	            fis=new FileInputStream(filePath);
				HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
				HSSFSheet sheet = wookbook.getSheetAt(0);	// 在Excel文档中，第一张工作表的缺省索引是0
				int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数&shy;
				Map<Integer,String> keys=new HashMap<Integer, String>();
				int cells=0;
				// 遍历行&shy;（第1行  表头） 准备Map里的key
				HSSFRow firstRow = sheet.getRow(0);
				if (firstRow != null) {
					// 获取到Excel文件中的所有的列
					cells = firstRow.getPhysicalNumberOfCells();
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值&shy;
						try {
							HSSFCell cell = firstRow.getCell(j);
							String cellValue = getCellValue(cell);
							keys.put(j,cellValue);						
						} catch (Exception e) {
							e.printStackTrace();	
						}
					}
				}
				// 遍历行&shy;（从第二行开始）
				for (int i = 1; i < rows; i++) {
					// 读取左上端单元格(从第二行开始)
					HSSFRow row = sheet.getRow(i);
					// 行不为空
					if (row != null) {
						//准备当前行 所储存值的map
						Map<String, Object> val=new HashMap<String, Object>();
						
						boolean isValidRow = false;
						
						// 遍历列
						for (int j = 0; j < cells; j++) {
							// 获取到列的值&shy;
							try {
								HSSFCell cell = row.getCell(j);
								String cellValue = getCellValue(cell);
								val.put(keys.get(j),cellValue);	
								if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
									isValidRow = true;
								}
							} catch (Exception e) {
								e.printStackTrace();		
							}
						}
						//第I行所有的列数据读取完毕，放入valuelist
						if(isValidRow){
							valueList.add(val);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
	            fis.close();
	        }
	        return valueList;
		}
		/**
		 * 读取2007-2013格式
		 * @param filePath 文件路径
		 * @return
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public  List<Map> readExcel2007(String filePath) throws IOException{
			List<Map> valueList=new ArrayList<Map>();
	        FileInputStream fis =null;
	        try {
	            fis =new FileInputStream(filePath);
	          //  XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(fis,1024)); //带有缓冲的机制
	            
	          //  XSSFWorkbook xwbs = new XSSFWorkbook();
	            XSSFWorkbook xwb = new XSSFWorkbook(fis);	// 构造 XSSFWorkbook 对象，strPath 传入文件路径
	            XSSFSheet sheet = xwb.getSheetAt(0);			// 读取第一章表格内容
	            // 定义 row、cell
	            XSSFRow row;
	            // 循环输出表格中的第一行内容   表头
	            Map<Integer, String> keys=new HashMap<Integer, String>();
	            row = sheet.getRow(0);
	            if(row !=null){
	                //System.out.println("j = row.getFirstCellNum()::"+row.getFirstCellNum());
	                //System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
	                for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
	                    // 通过 row.getCell(j).toString() 获取单元格内容，
	                    if(row.getCell(j)!=null){
	                        if(!row.getCell(j).toString().isEmpty()){
	                            keys.put(j, row.getCell(j).toString());
	                        }
	                    }else{
	                        keys.put(j, "K-R1C"+j+"E");
	                    }
	                }
	            }
	            // 循环输出表格中的从第二行开始内容
	            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
	                row = sheet.getRow(i);
	                if (row != null) {
	                    boolean isValidRow = false;
	                    Map<String, Object> val = new HashMap<String, Object>();
	                    for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
	                        XSSFCell cell = row.getCell(j);
	                        if (cell != null) {
	                            String cellValue = null;
	                            if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
	                                if(DateUtil.isCellDateFormatted(cell)){
	                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
	                                }
	                                else{
	                                    cellValue = String.valueOf(cell.getNumericCellValue());
	                                }
	                            }
	                            else{
	                                cellValue = cell.toString();
	                            }
	                            if(cellValue!=null&&cellValue.trim().length()<=0){
	                                cellValue=null;
	                            }
	                            val.put(keys.get(j), cellValue);
	                            if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
	                                isValidRow = true;
	                            }
	                        }
	                    }

	                    // 第I行所有的列数据读取完毕，放入valuelist
	                    if (isValidRow) {
	                        valueList.add(val);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            fis.close();
	        }

	        return valueList;
		}
		
		/**
		 * 文件操作 获取文件扩展名
		 * 
		 * @Author: sunny
		 * @param filename
		 *            文件名称包含扩展名
		 * @return
		 */
		public  String getExtensionName(String filename) {
			if ((filename != null) && (filename.length() > 0)) {
				int dot = filename.lastIndexOf('.');
				if ((dot > -1) && (dot < (filename.length() - 1))) {
					return filename.substring(dot + 1);
				}
			}
			return filename;
		}

		/** -----------上传文件,工具方法--------- */
		private static final int BUFFER_SIZE = 2 * 1024;

		/**
		 * 
		 * @param src 源文件
		 * @param dst 目标位置
		 */
		@SuppressWarnings("unused")
		private  void copy(File src, File dst) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	    /**
	     * 上传copy文件方法(for MultipartFile)
	     * @param savePath 在linux上要保存完整路径
	     * @param newname 新的文件名称， 采用系统时间做文件名防止中文报错的问题
	     * @throws Exception
	     */
	    public  void copy(MultipartFile file,String savePath,String newname) throws Exception {
	        try {
	            File targetFile = new File(savePath,newname);
	            if (!targetFile.exists()) {
	                //判断文件夹是否存在，不存在就创建
	                targetFile.mkdirs();
	            }

	            file.transferTo(targetFile);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
		
		private  String getCellValue(HSSFCell cell) {
			DecimalFormat df = new DecimalFormat("#");
			String cellValue=null;
			if (cell == null)
				return null;
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					if(HSSFDateUtil.isCellDateFormatted(cell)){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						break;
					}
					cellValue=df.format(cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:			
					cellValue=String.valueOf(cell.getStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					cellValue=String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					cellValue=null;
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					cellValue=String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					cellValue=String.valueOf(cell.getErrorCellValue());
					break;
			}
			if(cellValue!=null&&cellValue.trim().length()<=0){
				cellValue=null;
			}
			return cellValue;
		}
}
