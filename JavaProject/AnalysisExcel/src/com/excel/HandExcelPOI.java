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
	 * poi ��ȡexcel ֧��2003 --2007 �������ļ�
	 * 
	 * @author sunny
	 * @version V 2.0
	 * @CreatTime 2013-11-19 @
	 */
	 /** Workbook writeWB = new SXSSFWorkbook();
      * Sheet writeSheet = writeWB.createSheet(); 15W��20W���� д����
      * 
      * FileOutputStream output = new FileOutputStream(new File(xls_write_Address));  //��ȡ���ļ�·��   
      * SXSSFWorkbook wb = new SXSSFWorkbook(10000);//�ڴ��б��� 10000 �����ݣ������ڴ����������д�� Ӳ��
      */

		/**
		 * �ϲ���������ȡexcel�ļ�
		 * �����ļ����Զ�ʶ���ȡ��ʽ
		 * ֧��97-2013��ʽ��excel�ĵ�
		 * 
		 * @param fileName �ϴ��ļ���
		 * @param file �ϴ����ļ�
		 * @return �����б����ݸ�ʽ��
		 *  ÿһ�����ݶ����Զ�Ӧ�еı�ͷΪkey ����Ϊvalue ���� excel���Ϊ��
		 * ===============
		 *  A | B | C | D
		 * ===|===|===|===
		 *  1 | 2 | 3 | 4
		 * ---|---|---|--- 
		 *  a | b | c | d
		 * ---------------
		 * ����ֵ map��
		 *   map1:   A:1 B:2 C:3 D:4
		 *   map2:   A:a B:b C:d D:d
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public List<Map> readExcel(String fileName,MultipartFile file) throws Exception{
			//׼������ֵ�б�
			List<Map> valueList=new ArrayList<Map>();
            //String tempSavePath="tmp";
			//�����ļ�Ŀ¼���ļ������ƣ�struts�ã�
	        String filepathtemp="/mnt/b2b/tmp";//�����ļ�Ŀ¼
	        String tmpFileName= System.currentTimeMillis()+"."+getExtensionName(fileName);
	        String ExtensionName=getExtensionName(fileName);
            //String filepathtemp= ServletActionContext.getServletContext().getRealPath(tempSavePath);
	        //strut��ȡ��Ŀ·��
		    File filelist = new File(filepathtemp);
			if  (!filelist .exists()  && !filelist .isDirectory())      
			{       
				filelist .mkdir();    
			} 
			String filePath = filepathtemp+System.getProperty("file.separator")+tmpFileName;
	        File tmpfile = new File(filePath);
		    //�����ļ�������������Ŀ¼������Ŀ�£�
            //copy(file,tmpfile); --> stuts�õķ���
	        copy(file, filepathtemp,tmpFileName);//spring mvc�õķ���
			
			if(ExtensionName.equalsIgnoreCase("xls")){
				valueList=readExcel2003(filePath);
			}else if(ExtensionName.equalsIgnoreCase("xlsx")) {
				valueList=readExcel2007(filePath);
			}
			//ɾ�������ļ�
	        tmpfile.delete();
	        return valueList;
		 			
		}
		
		/**
		 * ��ȡ97-2003��ʽ
		 * @param filePath �ļ�·��
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public  List<Map> readExcel2003(String filePath) throws IOException{
			//���ؽ����
			List<Map> valueList=new ArrayList<Map>();
	        FileInputStream fis=null;
			try {
	            fis=new FileInputStream(filePath);
				HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// ������Excel�������ļ�������
				HSSFSheet sheet = wookbook.getSheetAt(0);	// ��Excel�ĵ��У���һ�Ź������ȱʡ������0
				int rows = sheet.getPhysicalNumberOfRows();	// ��ȡ��Excel�ļ��е���������&shy;
				Map<Integer,String> keys=new HashMap<Integer, String>();
				int cells=0;
				// ������&shy;����1��  ��ͷ�� ׼��Map���key
				HSSFRow firstRow = sheet.getRow(0);
				if (firstRow != null) {
					// ��ȡ��Excel�ļ��е����е���
					cells = firstRow.getPhysicalNumberOfCells();
					// ������
					for (int j = 0; j < cells; j++) {
						// ��ȡ���е�ֵ&shy;
						try {
							HSSFCell cell = firstRow.getCell(j);
							String cellValue = getCellValue(cell);
							keys.put(j,cellValue);						
						} catch (Exception e) {
							e.printStackTrace();	
						}
					}
				}
				// ������&shy;���ӵڶ��п�ʼ��
				for (int i = 1; i < rows; i++) {
					// ��ȡ���϶˵�Ԫ��(�ӵڶ��п�ʼ)
					HSSFRow row = sheet.getRow(i);
					// �в�Ϊ��
					if (row != null) {
						//׼����ǰ�� ������ֵ��map
						Map<String, Object> val=new HashMap<String, Object>();
						
						boolean isValidRow = false;
						
						// ������
						for (int j = 0; j < cells; j++) {
							// ��ȡ���е�ֵ&shy;
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
						//��I�����е������ݶ�ȡ��ϣ�����valuelist
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
		 * ��ȡ2007-2013��ʽ
		 * @param filePath �ļ�·��
		 * @return
		 * @throws java.io.IOException
		 */
		@SuppressWarnings("rawtypes")
		public  List<Map> readExcel2007(String filePath) throws IOException{
			List<Map> valueList=new ArrayList<Map>();
	        FileInputStream fis =null;
	        try {
	            fis =new FileInputStream(filePath);
	          //  XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(fis,1024)); //���л���Ļ���
	            
	          //  XSSFWorkbook xwbs = new XSSFWorkbook();
	            XSSFWorkbook xwb = new XSSFWorkbook(fis);	// ���� XSSFWorkbook ����strPath �����ļ�·��
	            XSSFSheet sheet = xwb.getSheetAt(0);			// ��ȡ��һ�±������
	            // ���� row��cell
	            XSSFRow row;
	            // ѭ���������еĵ�һ������   ��ͷ
	            Map<Integer, String> keys=new HashMap<Integer, String>();
	            row = sheet.getRow(0);
	            if(row !=null){
	                //System.out.println("j = row.getFirstCellNum()::"+row.getFirstCellNum());
	                //System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
	                for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
	                    // ͨ�� row.getCell(j).toString() ��ȡ��Ԫ�����ݣ�
	                    if(row.getCell(j)!=null){
	                        if(!row.getCell(j).toString().isEmpty()){
	                            keys.put(j, row.getCell(j).toString());
	                        }
	                    }else{
	                        keys.put(j, "K-R1C"+j+"E");
	                    }
	                }
	            }
	            // ѭ���������еĴӵڶ��п�ʼ����
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

	                    // ��I�����е������ݶ�ȡ��ϣ�����valuelist
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
		 * �ļ����� ��ȡ�ļ���չ��
		 * 
		 * @Author: sunny
		 * @param filename
		 *            �ļ����ư�����չ��
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

		/** -----------�ϴ��ļ�,���߷���--------- */
		private static final int BUFFER_SIZE = 2 * 1024;

		/**
		 * 
		 * @param src Դ�ļ�
		 * @param dst Ŀ��λ��
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
	     * �ϴ�copy�ļ�����(for MultipartFile)
	     * @param savePath ��linux��Ҫ��������·��
	     * @param newname �µ��ļ����ƣ� ����ϵͳʱ�����ļ�����ֹ���ı��������
	     * @throws Exception
	     */
	    public  void copy(MultipartFile file,String savePath,String newname) throws Exception {
	        try {
	            File targetFile = new File(savePath,newname);
	            if (!targetFile.exists()) {
	                //�ж��ļ����Ƿ���ڣ������ھʹ���
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
