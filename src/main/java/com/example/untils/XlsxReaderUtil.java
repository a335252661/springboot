package com.example.untils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 2007 Excel(xlsx)读取工具类
 * 
 * @author 黄志豪
 */
public class XlsxReaderUtil {
	private static Log log = LogFactory.getLog(XlsxReaderUtil.class);
	
	public final static String DATE_READ_FORMAT = "yyyy-MM-dd HH:mm:ss"; 

	/**
	 * 读取excel中某个sheet的数据
	 * 
	 * @param file
	 * @param sheetIdx
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetData(File file, int sheetIdx) throws FileNotFoundException{
		return getSheetData(new FileInputStream(file), sheetIdx);
	}
	
	/**
	 * 读取excel中某个sheet的数据
	 * 
	 * @param inputStream
	 * @param sheetIdx
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetData(InputStream inputStream, int sheetIndex){
		try {
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			XSSFSheet st=wb.getSheetAt(sheetIndex);
			return readSheetData(wb, st);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭流
			if(null!=inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 读取excel中某个sheet的数据(批量反馈)
	 * 
	 * @param inputStream
	 * @param sheetIdx
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetDataForExpBatchFeedback(InputStream inputStream, int sheetIndex){
		try {
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			XSSFSheet st=wb.getSheetAt(sheetIndex);
			return readSheetDataForExpBatchFeedback(wb, st);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭流
			if(null!=inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return null;
	}
	

	/**
	 * 读取excel中某个sheet的数据
	 * 
	 * @param file
	 * @param sheetName 
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetData(File file, String sheetName) throws FileNotFoundException{
		return getSheetData(new FileInputStream(file), sheetName);
	}
	
	/**
	 * 读取excel中某个sheet的数据
	 * 
	 * @param inputStream
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetData(InputStream inputStream, String sheetName){
		try {
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			return readSheetData(wb, wb.getSheet(sheetName));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			//关闭流
			if(null!=inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	
	/**
	 * 读取excel所有sheet的数据
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public static List<Map<String,String>> getAllData(File file) throws FileNotFoundException{
		return getAllData(new FileInputStream(file));
	}
	
	/**
	 * 读取excel
	 * 
	 * @param inputStream 输入流数据
	 * @return List<Map<String,String>> 数据以表头和值的形式保存到map中
	 */
	public static List<Map<String,String>> getAllData(InputStream inputStream){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();

		XSSFWorkbook wb=null;
		//表头所在列索引
		try {
			wb=new XSSFWorkbook(inputStream);
			for(int sheetIndex=0;sheetIndex<wb.getNumberOfSheets();sheetIndex++){
				XSSFSheet st=wb.getSheetAt(sheetIndex);
				List<Map<String,String>> sheetData = readSheetData(wb, st);
				list.addAll(sheetData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			//关闭流
			if(null!=inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return list;
	}
	
	/**
	 * 读取数据
	 * 
	 * @param wb
	 * @param sheetIdx
	 * @return
	 */
	private static List<Map<String,String>> readSheetData(XSSFWorkbook wb, XSSFSheet st){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> rowCellValue=null;
		XSSFCell cell=null;
		int colCnt=0;
		//表头所在列索引
		Map<Integer,String> cmCellIndex=new HashMap<Integer,String>();
		for(int rowIndex=0;rowIndex<=st.getLastRowNum();rowIndex++){
			rowCellValue=new HashMap<String,String>();
			XSSFRow row=st.getRow(rowIndex);
			if(row == null){
				break;
			}
			if(rowIndex==0){
				colCnt = row.getLastCellNum();
			}
			int emptyCnt = 0;	// 计算空白的单元格数
			for(int columnIndex=0;columnIndex<colCnt;columnIndex++){
				cell=row.getCell(columnIndex);
				String value=getCellFormatValue(cell);
				//将excel表头所在列位置记录
				if(rowIndex==0){
					if(StringUtils.hasText(value)){
						cmCellIndex.put(columnIndex, value);
					}
				}else{
					//将excel文档中的标题和值key-value保存
					String colName = cmCellIndex.get(columnIndex);
					if(colName != null){
						rowCellValue.put(colName, value);
					}

					if(!StringUtils.hasText(value)){
						emptyCnt++;
					}
				}
			}
			
			// 整行是空的排除掉
			if(rowIndex!=0 && emptyCnt != colCnt){
			//if(rowIndex!=0){
				list.add(rowCellValue);
			}
			
			if(rowIndex!=0 && emptyCnt == colCnt){
				break;
			}
		}
		return list;
	}
	
	/**
	 * 读取数据(批量反馈)
	 * 
	 * @param wb
	 * @param sheetIdx
	 * @return
	 */
	private static List<Map<String,String>> readSheetDataForExpBatchFeedback(XSSFWorkbook wb, XSSFSheet st){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> rowCellValue=null;
		XSSFCell cell=null;
		int colCnt=0;
		//表头所在列索引
		Map<Integer,String> cmCellIndex=new HashMap<Integer,String>();
		for(int rowIndex=1;rowIndex<=st.getLastRowNum();rowIndex++){
			if(rowIndex == 2){
				continue;
			}
			rowCellValue=new HashMap<String,String>();
			XSSFRow row=st.getRow(rowIndex);
			if(row == null){
				continue;
			}
			if(rowIndex==1){
				colCnt = row.getLastCellNum();
			}
			int emptyCnt = 0;	// 计算空白的单元格数
			for(int columnIndex=0;columnIndex<colCnt;columnIndex++){
				cell=row.getCell(columnIndex);
				String value=getCellFormatValue(cell);
				//将excel表头所在列位置记录
				if(rowIndex==1){
					if(StringUtils.hasText(value)){
						cmCellIndex.put(columnIndex, value);
					}
				}else{
					//将excel文档中的标题和值key-value保存
					String colName = cmCellIndex.get(columnIndex);
					if(colName != null){
						rowCellValue.put(colName, value);
					}

					if(!StringUtils.hasText(value)){
						emptyCnt++;
					}
				}
			}
			
			// 整行是空的排除掉
			if(rowIndex!=1 && emptyCnt != colCnt){
			//if(rowIndex!=0){
				list.add(rowCellValue);
			}
		}
		return list;
	}
	
	/**
	 * 获取cell的值
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(XSSFCell cell){
		String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case XSSFCell.CELL_TYPE_NUMERIC:
            case XSSFCell.CELL_TYPE_FORMULA: {
            	 try {  
            		 /* 
                      * 此处判断使用公式生成的字符串有问题，因为XSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
                      * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
                      */
            		 // 判断当前的cell是否为Date
                     if (DateUtil.isCellDateFormatted(cell)) {
                         // 如果是Date类型则，转化为Data格式
                         
                         //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                         //cellvalue = cell.getDateCellValue().toLocaleString();
                         
                         //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                         Date date = cell.getDateCellValue();
                         SimpleDateFormat sdf = new SimpleDateFormat(DATE_READ_FORMAT);
                         cellvalue = sdf.format(date);
                     }
                     // 如果是纯数字
                     else {
                    	 //	数值格式化：科学计数法转换位正常数字，整数不显示.0
                    	 DecimalFormat dt = new DecimalFormat("0.######"); 
                    	 Double value = cell.getNumericCellValue();
                         // 取得当前Cell的数值
                         cellvalue = dt.format(value);
                         // 科学计数法转换位正常数字
                        // BigDecimal bd = new BigDecimal(cellvalue);
                        // cellvalue = bd.toPlainString();
                     }
                     break;
            	} catch (IllegalStateException e) {  
            		// 发生转换异常，字符串无法转换为数字，以字符串方式解析单元格内容
            		cellvalue = String.valueOf(cell.getRichStringCellValue());  
            	}  
            	break;  
               
            }
            // 如果当前Cell的Type为STRIN
            case XSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        
        return StringUtils.deleteWhitespace(cellvalue);
	}
	
	
	public static void main(String[] args) {
		File file=new File("C:\\Users\\tes\\Downloads\\ROUTE_MASTER_20150813144322012.xlsx");
		try {
			List<Map<String,String>> list=XlsxReaderUtil.getSheetData(file, "sheet1");
			for(int i=0;i<list.size();i++){
				Map<String,String> map=list.get(i);
				StringBuffer s=new StringBuffer();
				for(String key:map.keySet()){
					s.append(key+"="+map.get(key));
				}
				System.out.println("*" + map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取excel中某个sheet的数据
	 * 
	 * @param inputStream
	 * @param sheetIdx
	 * @param index 循环数据的列名出现行数
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> getSheetDataNotFirstRow(InputStream inputStream, int sheetIndex,int index,int keycol,int valcol){
		try {
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			XSSFSheet st=wb.getSheetAt(sheetIndex);
			return readSheetDataNotFirstRow(wb, st,index,keycol,valcol);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭流
			if(null!=inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return null;
	}/**
	 * 读取数据
	 * 
	 * @param wb
	 * @param sheetIdx
	 * @param index 循环数据的列名出现行数
	 * @return
	 */
	private static List<Map<String,String>> readSheetDataNotFirstRow(XSSFWorkbook wb, XSSFSheet st,int index,int keycol,int valcol){
		//List<Map<String,String>> titlelist=new ArrayList<Map<String,String>>();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> rowCellValue=null;
		Map<String,String> titlerowCellValue=null;
		XSSFCell cell=null;
	    int titlecolCnt=0;	
		int colCnt=0;		
		int emptycount = 0;
		//表头所在列索引
		Map<Integer,String> cmtitleCellIndex=new HashMap<Integer,String>();
		Map<Integer,String> cmCellIndex=new HashMap<Integer,String>();
		//获取标题数据
		for(int rowIndex = 0;rowIndex < index;rowIndex++){
			titlerowCellValue=new HashMap<String,String>();
			XSSFRow row=st.getRow(rowIndex);
			if(row == null){
				continue;
			}
			titlecolCnt = index;
			
			//获取第一列的key
			cell=row.getCell(keycol);
			String key=getCellFormatValue(cell);
			if(StringUtils.hasText(key)){
				cmtitleCellIndex.put(rowIndex, key);
			}
			cell = row.getCell(valcol);
			String value=getCellFormatValue(cell);
			//获取第二列的value
			//将excel文档中的标题和值key-value保存
			String colName = cmtitleCellIndex.get(rowIndex);
			if(colName != null){
				titlerowCellValue.put(colName, value);
			}
			if(!StringUtils.hasText(value)){
				emptycount++;
			}
			list.add(titlerowCellValue);
		}
		
		for(int rowIndex=index;rowIndex<=st.getLastRowNum();rowIndex++){
			rowCellValue=new HashMap<String,String>();
			XSSFRow row=st.getRow(rowIndex);
			if(row == null){
				continue;
			}
			if(rowIndex==index){
				colCnt = row.getLastCellNum();
			}
			int emptyCnt = 0;	// 计算空白的单元格数
			for(int columnIndex=0;columnIndex<colCnt;columnIndex++){
				cell=row.getCell(columnIndex);
				String value=getCellFormatValue(cell);
				//将excel表头所在列位置记录
				if(rowIndex==index){
					if(StringUtils.hasText(value)){
						cmCellIndex.put(columnIndex, value);
					}
				}else{
					//将excel文档中的标题和值key-value保存
					String colName = cmCellIndex.get(columnIndex);
					if(colName != null){
						rowCellValue.put(colName, value);
					}

					if(!StringUtils.hasText(value)){
						emptyCnt++;
					}
				}
			}
			
			// 整行是空的排除掉
			if(rowIndex!=index && emptyCnt != colCnt){
			//if(rowIndex!=0){
				list.add(rowCellValue);
			}
		}
		return list;
	}
	
}
