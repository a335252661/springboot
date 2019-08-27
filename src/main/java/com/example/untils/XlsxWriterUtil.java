package com.example.untils;

import com.example.untils.constant.FileExportConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 2007 Excel(xlsx)写入工具类
 * @author 黄志豪
 */
public class XlsxWriterUtil {
	private static Log log = LogFactory.getLog(XlsxReaderUtil.class);

	private XSSFWorkbook wb;

	private Map<String, SheetWriter> sheetMap = new HashMap<String, SheetWriter>(); // sheet名称， sheet 总行数

	private List<SheetWriter> sheetList = new ArrayList<SheetWriter>(3);

	private SheetWriter curSheetWriter; // 当前Sheet，可能有多个Sheet

	/**
	 *
	 *
	 */
	public XlsxWriterUtil() {
		this("sheet1");
	}

	public XlsxWriterUtil(String sheetName) {
		wb = new XSSFWorkbook();
		curSheetWriter = new SheetWriter(sheetName, (XSSFSheet) wb.createSheet(sheetName));
	}

	/**
	 * 初始化宏模板
	 * @param macroTemplate
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public XlsxWriterUtil(File macroTemplate) throws InvalidFormatException, IOException {
		this(macroTemplate, "Sheet1");
	}

	/**
	 * 初始化宏模板
	 * @param macroTemplate
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public XlsxWriterUtil(File macroTemplate, String sheetName) throws InvalidFormatException, IOException {
		wb = new XSSFWorkbook(
	            OPCPackage.open(macroTemplate)
	        );
		int sheetCount = wb.getNumberOfSheets();
		for(int i = 0; i< sheetCount;i++){
			wb.removeSheetAt(0);
		}
		XSSFSheet sheet = (XSSFSheet) wb.createSheet(sheetName);
		curSheetWriter = new SheetWriter(sheetName, sheet);
	}
	
	/**
	 * 
	 * @param inputStream
	 * @throws IOException
	 */
	public XlsxWriterUtil(InputStream inputStream, int sheetIdx) throws IOException {
		wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(sheetIdx);
		String sheetName = wb.getSheetName(sheetIdx);
		curSheetWriter = new SheetWriter(sheetName, sheet);
	}

	/**
	 * 
	 * @param inputStream
	 */
	public XlsxWriterUtil(InputStream inputStream) throws IOException {
		this(inputStream, 0);
	}

	public void createNewSheet() {
		String sheetName = "sheet" + sheetList.size();
		if (sheetMap.get(sheetName) != null) {
			sheetName += "_" + System.currentTimeMillis();
		}
		createNewSheet(sheetName);
	}

	public void createNewSheet(String name) {
		curSheetWriter = new SheetWriter(name, wb.createSheet(name));
	}

	public void changeSheet(int idx) {
		Sheet sheet = wb.getSheetAt(idx);
		changeSheet(sheet.getSheetName());
	}

	public void changeSheet(String sheetName) {
		SheetWriter sheetWriter = (SheetWriter) sheetMap.get(sheetName);
		if (sheetWriter == null) {
			throw new IllegalStateException(" can not find sheet " + sheetName);
		}
		this.curSheetWriter = sheetWriter;
	}
	
	public void renameSheet(String rename){
		wb.setSheetName(wb.getSheetIndex(this.curSheetWriter.sheet), rename);
	}

	public void write(int row, int column, Object value) {
		write(row, column, value, null);
	}

	public void write(int row, int column, Object value, XSSFCellStyle style) {
		curSheetWriter.writeCell(row, column, value, style);
	}
	
	public void writeMergeCell(int rowFrom,int colFrom, int rowTo, int colTo,String value, XSSFCellStyle style) {
			curSheetWriter.writerMergeCell(rowFrom, colFrom, rowTo, colTo, value, style); 
	}	
	
	public void writeMergeCell(int rowFrom,int colFrom, int rowTo, int colTo,String value) {
		curSheetWriter.writerMergeCell(rowFrom, colFrom, rowTo, colTo, value, null); 
	}
	
	public void writeMergeRow(int row,int col,String value) {
		curSheetWriter.writerMergeCell(row, 0, row, col, value, null); 
	}	
	
	public void writeMergeRow(int row,int col,String value,XSSFCellStyle style) {
		curSheetWriter.writerMergeCell(row, 0, row, col, value, style); 
	}
	public void writeMergeRow1(int row,int col,String value,XSSFCellStyle style) {
		curSheetWriter.writerMergeCell1(row, 0, row, col, value, style);
	}
	public void writeGridTitle(List<Object> list,int row){
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(setFont(true, "宋体", (short)11));
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		curSheetWriter.appendRow(list, 0,style);
	}
	
	

	public void writeRows(List<List<Object>> data, int colOffset) {
		for(List<? extends Object> d : data){
			curSheetWriter.appendRow(d, colOffset);
		}
	}
	
	public void writeRows(List<List<Object>> data, int colOffset,XSSFCellStyle style) {
		for(List<? extends Object> d : data){
			curSheetWriter.appendRow(d, colOffset,style);
		}
	}

	//代码重写优化
	public void writeRowsNew(List<List<Object>> data, int colOffset,boolean withStyle) {
		XSSFCellStyle mainStyle = wb.createCellStyle();
		mainStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		mainStyle.setFillPattern(XSSFCellStyle.NO_FILL);
		mainStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		mainStyle.setWrapText(true);
		mainStyle.setFont(setFont(false, "宋体", (short)9));
		mainStyle.setBorderTop(CellStyle.BORDER_THIN);
		mainStyle.setBorderRight(CellStyle.BORDER_THIN);
		mainStyle.setBorderBottom(CellStyle.BORDER_THIN);
		mainStyle.setBorderLeft(CellStyle.BORDER_THIN);

		//下面应该是三种对应样式
		List<? extends Object> rowStyle = data.get(0);
		List<? extends Object> widthRow = (List<Object>) data.get(1);
		List<? extends Object> typeRow = (List<Object>) data.get(2);
		//然后要移除不影响data
		data.remove(0);
		data.remove(0);
		data.remove(0);

		HashMap<String,XSSFCellStyle> styleMap = new HashMap<String,XSSFCellStyle>();

		//设置宽度
		for (Object object : widthRow) {
			BigDecimal o = (BigDecimal) object;
			curSheetWriter.colSizeList.add(o.shortValueExact());
		}
		XSSFSheet sheet   =  curSheetWriter.sheet;
		int totalRow = sheet.getLastRowNum()+1;

		//开始循环行
		for(List<? extends Object> detail : data){

			int writeRow = totalRow;

			for (int i = 0; i < detail.size(); i++) {
				Object value = detail.get(i);
				XSSFCellStyle nowStyle;

				String textAlign = String.valueOf(rowStyle.get(i)==null?"":rowStyle.get(i)).toUpperCase();// 对齐方式
				String type = String.valueOf(typeRow.get(i)==null?"":typeRow.get(i)).toUpperCase();// 类型设置

				String mapKey = textAlign +"_"+ type;
				if (Date.class.isInstance(value)) mapKey = mapKey+"_date";
				if(styleMap.containsKey(mapKey))
					nowStyle =  styleMap.get(mapKey);
				else{
					nowStyle = (XSSFCellStyle) mainStyle.clone();
					if(FileExportConstants.TEXT_ALIGN_MIDDLE.equals(textAlign))
						nowStyle.setAlignment(HorizontalAlignment.CENTER);
					else if(FileExportConstants.TEXT_ALIGN_RIGHT.equals(textAlign))
						nowStyle.setAlignment(HorizontalAlignment.RIGHT);
					else if(FileExportConstants.TEXT_ALIGN_LEFT.equals(textAlign))
						nowStyle.setAlignment(HorizontalAlignment.LEFT);
					DataFormat format= wb.createDataFormat();
					if(StringUtils.hasText(type))
						nowStyle.setDataFormat(format.getFormat(type));
					else
						nowStyle.setDataFormat(format.getFormat("General"));
					if (Date.class.isInstance(value))
						nowStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("yyyy/m/d h:mm:ss"));
							 	/*CellStyle newcellStyle = wb.createCellStyle();
								BeanUtils.copyProperties(nowStyle, newcellStyle);*/
					styleMap.put(mapKey, nowStyle);
				}
				if (value == null) value = "";		//如果写入excel对象为空对象，则默认给一个空字符串

				int column =   i + colOffset;

				while (totalRow <= writeRow) {
					sheet.createRow(totalRow);
					totalRow++;
					curSheetWriter.totalRow = totalRow;//修复兼容
				}
				XSSFRow workRow = (XSSFRow) sheet.getRow(writeRow);
				XSSFCell cell = (XSSFCell) workRow.createCell(column);
				if (value != null) {
					if (String.class.isInstance(value)) {
						cell.setCellStyle(nowStyle);
						cell.setCellValue(new XSSFRichTextString((String) value));
					} else if (Double.class.isInstance(value)
							|| Integer.class.isInstance(value)
							|| Long.class.isInstance(value)
							|| Float.class.isInstance(value)
							|| BigDecimal.class.isInstance(value)) {
						cell.setCellStyle(nowStyle);
						cell.setCellValue(Double.parseDouble(String.valueOf(value)));
					} else if (Date.class.isInstance(value)) {
						cell.setCellStyle(nowStyle);
						cell.setCellValue((Date) value);
					} else if (Calendar.class.isInstance(value)) {
						cell.setCellValue((Calendar) value);
					} else if (Formula.class.isInstance(value)) {
						Formula formula = (Formula)value;
						cell.setCellFormula(formula.getFormulaStr());
						cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					} else {
						cell.setCellValue(new XSSFRichTextString((String.valueOf(value)).trim()));
					}
				}
			}
		}
	}

	public void writeRows(List<List<Object>> data, int colOffset,boolean withStyle) {
		XSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillPattern(XSSFCellStyle.NO_FILL);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		style.setWrapText(true);
		style.setFont(setFont(false, "宋体", (short)9));
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		List<? extends Object> rowStyle = data.get(0);
		List<? extends Object> widthRow = (List<Object>) data.get(1);
		List<? extends Object> typeRow = (List<Object>) data.get(2);
		data.remove(0);
		data.remove(0);
		data.remove(0);
		for(List<? extends Object> d : data){
			
			// curSheetWriter.appendRow(d, colOffset,style);
			curSheetWriter.appendRowStyle(d, colOffset,style,rowStyle,widthRow,typeRow);
		}
	}
	public void writeRowsWithTitle(List<List<Object>> data,int startRow, int colOffset,boolean withStyle) {
		writeGridTitle(data.remove(0),startRow);
		//writeRows(data,colOffset,withStyle);
		//xlsx优化后方法
		writeRowsNew(data,colOffset,withStyle);
	}
	
	/**
	 * 设置指给合并单元格
	 * 
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 * @param value
	 * @param style
	 */
	public void writerMergeCell(int rowFrom, int colFrom, int rowTo, int colTo,
			Object value, XSSFCellStyle style) {
		curSheetWriter.writerMergeCell(rowFrom, colFrom, rowTo, colTo, value,
				style);
	}

	public void writeCellFormula(int rowPos, int columnPos, String formula,
			XSSFCellStyle style) {
		curSheetWriter.writeCellFormula(rowPos, columnPos, formula, style);
	}

	public void setColumnWidth(short column, short width) {
		curSheetWriter.setColumnWidth(column, width);
	}
	
	public void setColumnAutoWidth(short column){
		curSheetWriter.setColumnAutoWidth(column);
	}

	/**
	 * set height in points
	 * 
	 * @param row
	 * @param height
	 */
	public void setRowHeight(short row, short height) {
		curSheetWriter.setRowHeight(row, height);
	}
	
	
	public void setDisplayGridlines(boolean bool){
		curSheetWriter.setDisplayGridlines(bool);
	}

	public XSSFCellStyle newCellStyle() {
		return (XSSFCellStyle) wb.createCellStyle();
	}

	public XSSFFont newFont() {
		return (XSSFFont) wb.createFont();
	}

	public XSSFDataFormat newDataFormat() {
		return (XSSFDataFormat) wb.createDataFormat();
	}
	
	public void adjustRowHeight(){
		curSheetWriter.adjustRowHeight();
	}
	
	public void autoSizeColumn(int column){
		curSheetWriter.autoSizeColumn(column);
	}
	
	public void setColumnSizes(){
		curSheetWriter.setColumnSize();
	}

	public short createDataFormat(String format){
		return wb.createDataFormat().getFormat(format);
	}
	
	public void writeExcel(OutputStream outputStream) {
		try {
			wb.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null!=outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	// sheet 对象
	private class SheetWriter {
		@SuppressWarnings("unused")
		private String sheetName;

		private int totalRow;

		private String dataTimeFormat = "yyyy/m/d h:mm:ss";

		private XSSFSheet sheet;
		//记录最长列宽
		private List<Short> colSizeList;

		public SheetWriter(String sheetName, XSSFSheet sheet) {
			this.sheetName = sheetName;
			this.sheet = sheet;
			sheetList.add(this);
			sheetMap.put(sheetName, this);
			totalRow = this.sheet.getLastRowNum();
			colSizeList = new ArrayList<Short>();
		}

		private void createRowIfNecessary(int writeRow) {
			while (totalRow <= writeRow) {
				sheet.createRow(totalRow);
				totalRow++;
			}
		}
		
		public void appendRow(List<? extends Object> data, int colOffset){
			int r = totalRow;
			for (int i = 0; i < data.size(); i++) {
				writeCell(r, i + colOffset, data.get(i), null);
			}
		}
		
		public void appendRow(List<? extends Object> data, int colOffset,XSSFCellStyle style){
			int r = totalRow;
			for (int i = 0; i < data.size(); i++) {
				writeCell(r, i + colOffset, data.get(i), style);
			}
		}
		
		public void appendRowStyle(List<? extends Object> data, int colOffset, XSSFCellStyle style, List<? extends Object> dataStyle, List<? extends Object> widthRow
				, List<? extends Object> typeRow){
			//widthRow
			for (Object object : widthRow) {
				BigDecimal o = (BigDecimal) object;
				colSizeList.add(o.shortValueExact());
			}
			int r = totalRow;
			for (int i = 0; i < data.size(); i++) {
				// 对齐方式
				String textAlign = String.valueOf(dataStyle.get(i)==null?"":dataStyle.get(i)).toUpperCase();
				// 文字对齐方式：居中
				if(FileExportConstants.TEXT_ALIGN_MIDDLE.equals(textAlign)){
					style.setAlignment(HorizontalAlignment.CENTER);
					// 文字对齐方式：右	
				}else if(FileExportConstants.TEXT_ALIGN_RIGHT.equals(textAlign)){
					style.setAlignment(HorizontalAlignment.RIGHT);
					// 文字对齐方式：左
				}else if(FileExportConstants.TEXT_ALIGN_LEFT.equals(textAlign)){
					style.setAlignment(HorizontalAlignment.LEFT);
				}
				// 类型设置
				String type = String.valueOf(typeRow.get(i)==null?"":typeRow.get(i)).toUpperCase();
				DataFormat format= wb.createDataFormat();
				// 格式设置
				if(StringUtils.hasText(type)){
					style.setDataFormat(format.getFormat(type));
				}else{
					style.setDataFormat(format.getFormat("General"));
				}
				writeCell(r, i + colOffset, data.get(i), style);
			}
		}
		
		public void writeCell(int row, int column, Object value,
				XSSFCellStyle style) {
			if (value == null)
			{ 
				//return;	
				value = "";		//如果写入excel对象为空对象，则默认给一个空字符串
			}
			createRowIfNecessary(row);

			XSSFRow workRow = (XSSFRow) sheet.getRow(row);
			XSSFCell cell = (XSSFCell) workRow.createCell( column);
			if (style != null)
				
				cell.setCellStyle(style);
//			cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
//			cell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
//			cell.setCellType(XSSFCell.CELL_TYPE_ERROR);
//			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//			cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			writeObjectValue(cell, value);
		}

		/**
		 * 
		 * @param rowFrom
		 * @param colFrom
		 * @param rowTo
		 * @param colTo
		 * @param value
		 * @param style
		 */
		public void writerMergeCell(int rowFrom, int colFrom, int rowTo, int colTo, 
				Object value, XSSFCellStyle style) {
			createRowIfNecessary(rowTo);
			//Region region = new Region(rowFrom,  colFrom, rowTo,  colTo);
			CellRangeAddress region = new CellRangeAddress(rowFrom, rowTo, colFrom, colTo);
			sheet.addMergedRegion(region);
			//去除边框线
			sheet.setDisplayGridlines(false);
			for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
				XSSFRow row = (XSSFRow) sheet.getRow(i);
				for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
					XSSFCell cell = (XSSFCell) row.createCell(j);
					if (style != null) {
						cell.setCellStyle(style);
					}
				}
			}

			XSSFRow workRow = (XSSFRow) sheet.getRow(rowFrom);
			XSSFCell cell = (XSSFCell) workRow.getCell(colFrom);

			writeObjectValue(cell, value);

		}

		/**
		 *
		 * @param rowFrom
		 * @param colFrom
		 * @param rowTo
		 * @param colTo
		 * @param value
		 * @param style
		 */
		public void writerMergeCell1(int rowFrom, int colFrom, int rowTo, int colTo,
									Object value, XSSFCellStyle style) {
			createRowIfNecessary(rowTo);
			//Region region = new Region(rowFrom,  colFrom, rowTo,  colTo);
			CellRangeAddress region = new CellRangeAddress(rowFrom, rowTo, colFrom, colTo);
			sheet.addMergedRegion(region);
			//去除边框线
			sheet.setDisplayGridlines(false);
			for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
				XSSFRow row = (XSSFRow) sheet.getRow(i);
				for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
					XSSFCell cell = (XSSFCell) row.createCell(j);
					if (style != null) {
						cell.setCellStyle(style);
					}
				}
			}

			XSSFRow workRow = (XSSFRow) sheet.getRow(rowFrom);
			XSSFCell cell = (XSSFCell) workRow.getCell(colFrom);

			if (value != null) {
				if (String.class.isInstance(value)) {
					cell.setCellStyle(style);
					cell.setCellValue(new XSSFRichTextString((String) value));
				} else if (Double.class.isInstance(value)
						|| Integer.class.isInstance(value)
						|| Long.class.isInstance(value)
						|| Float.class.isInstance(value)
						|| BigDecimal.class.isInstance(value)) {
					cell.setCellStyle(style);
					cell.setCellValue(Double.parseDouble(String.valueOf(value)));
				} else if (Date.class.isInstance(value)) {
					cell.setCellStyle(style);
					cell.setCellValue((Date) value);
				} else if (Calendar.class.isInstance(value)) {
					cell.setCellValue((Calendar) value);
				} else if (XlsxWriterUtil.Formula.class.isInstance(value)) {
					XlsxWriterUtil.Formula formula = (XlsxWriterUtil.Formula)value;
					cell.setCellFormula(formula.getFormulaStr());
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				} else {
					cell.setCellValue(new XSSFRichTextString((String.valueOf(value)).trim()));
				}
			}
		}

		private void writeCellFormula(int rowPos, int columnPos,
				String formula, XSSFCellStyle style) {
			createRowIfNecessary(rowPos);
			XSSFRow workRow = (XSSFRow) sheet.getRow(rowPos);
			XSSFCell cell = (XSSFCell) workRow.createCell( columnPos);
			if (style != null)
				cell.setCellStyle(style);
			cell.setCellFormula(formula);
		}

		private void writeObjectValue(XSSFCell cell, Object value) {
			if (value != null) {
				if (String.class.isInstance(value)) {
					CellStyle cellStyle = cell.getCellStyle();
					CellStyle cellStyle2 = wb.createCellStyle();
					BeanUtils.copyProperties(cellStyle, cellStyle2);
					cell.setCellStyle(cellStyle2);
					cell.setCellValue(new XSSFRichTextString((String) value));
//					cell.setCellValue(new XSSFRichTextString(((String) value)
//							.trim()));
				} else if (Double.class.isInstance(value)
						|| Integer.class.isInstance(value)
						|| Long.class.isInstance(value)
						|| Float.class.isInstance(value)
						|| BigDecimal.class.isInstance(value)) {
					CellStyle cellStyle = cell.getCellStyle();
					CellStyle cellStyle2 = wb.createCellStyle();
					BeanUtils.copyProperties(cellStyle, cellStyle2);
					cell.setCellStyle(cellStyle2);
					cell.setCellValue(Double.parseDouble(String
									.valueOf(value)));
				} else if (Date.class.isInstance(value)) {
					CellStyle cellStyle = cell.getCellStyle();
					CellStyle cellStyle2 = wb.createCellStyle();
					BeanUtils.copyProperties(cellStyle, cellStyle2);
//					cellStyle2.setDataFormat(
//					    wb.getCreationHelper().createDataFormat().getFormat(dataTimeFormat));
					cell.setCellStyle(cellStyle2);
					cell.setCellValue((Date) value);
				} else if (Calendar.class.isInstance(value)) {
					cell.setCellValue((Calendar) value);
				} else if (Formula.class.isInstance(value)) {
					Formula formula = (Formula)value;
					cell.setCellFormula(formula.getFormulaStr());
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				} else {
					cell.setCellValue(new XSSFRichTextString((String.valueOf(value)).trim()));
				}
			}
		}

		public void setColumnWidth(short column, short width) {
			sheet.setColumnWidth(column, width);
		}
		
		public void setDisplayGridlines(boolean bool){
			sheet.setDisplayGridlines(bool);
		}
		
		public void setColumnAutoWidth(short column){
			sheet.autoSizeColumn(column);
		}

		/**
		 * set height in points
		 * 
		 * @param row
		 * @param height
		 */
		public void setRowHeight(short row, short height) {
			createRowIfNecessary(row);
			XSSFRow workRow = (XSSFRow) sheet.getRow(row);
			workRow.setHeightInPoints(height);
		}
		
		public void adjustRowHeight() {
			// 默认行间距，pixel为单位
			float defaultRowGapInPoint = 4f;
			// 得到所有的合并区域
			List<CellRangeAddress> regions = new ArrayList<CellRangeAddress>();
			for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				regions.add(sheet.getMergedRegion(i));
			}
			int rows = sheet.getPhysicalNumberOfRows();
			for (int r = 0; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				int cells = row.getPhysicalNumberOfCells();
				for (short c = 0; c < cells; c++) {
					XSSFCell cell = row.getCell(c);
					// 只对String类型单元格调整高度
					if (cell != null
							&& cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						boolean isBelongToRegion = false;
						for (int i = 0; i < regions.size(); i++) {
							CellRangeAddress region = regions.get(i);
							if (region.isInRange(r, c)) {
								isBelongToRegion = true;
								int rowFrom = region.getFirstRow();
								int rowTo = region.getLastRow();
								int colFrom = region.getFirstColumn();
								int colTo = region.getLastColumn();
								int regionWidths = 0;
								for (int ii = colFrom; ii <= colTo; ii++) {
									regionWidths += sheet.getColumnWidth(ii);
								}
								long stringWidths = cell
										.getRichStringCellValue().toString()
										.getBytes().length * 256;
								// 计算所需高度为默认高度的多少倍
								long aRows = stringWidths / regionWidths + 1 + 1;
								// 得到每行应该有的高度
								XSSFFont font = cell.getCellStyle().getFont();
								float rowHeightInPoint = font
										.getFontHeightInPoints()
										+ defaultRowGapInPoint;
								float height = rowHeightInPoint * aRows
										/ (rowTo - rowFrom +1 );
								for (int jj = rowFrom; jj <= rowTo; jj++) {
									XSSFRow RegionRow = sheet.getRow(jj);
									if (RegionRow.getHeightInPoints() < height) {
										RegionRow.setHeightInPoints(height);
									}
								}
								break;
							}
						}
						if (!isBelongToRegion) {
							long stringWidths = cell.getRichStringCellValue()
									.toString().getBytes().length * 256;
							// 得到列宽为一个字符的1/256
							long colWidth = sheet.getColumnWidth(c);
							long aRows = stringWidths / colWidth + 1;
							// 得到每行应该有的高度
							XSSFFont font = cell.getCellStyle().getFont();
							float rowHeightInPoint = font
									.getFontHeightInPoints()
									+ defaultRowGapInPoint;
							if (row.getHeightInPoints() < aRows
									* rowHeightInPoint) {
								row.setHeightInPoints(aRows * rowHeightInPoint);
							}
						}
					}
				}
			}
		}

		public void autoSizeColumn(int column){
			column = colSizeList.size()<column?colSizeList.size():column;
			for (int i = 0; i <= column; i++) {
				sheet.autoSizeColumn(i);//自动调整列宽
				int autoWidth= sheet.getColumnWidth(i);
				int length = colSizeList.get(i);
				if(length*256>autoWidth){
					if(length>255){
						sheet.setColumnWidth(i, 255*256);
					}else{
						sheet.setColumnWidth(i, length*256);
					}
				}
			}
		}
		public void setColumnSize(){
			for (int i=0;i<colSizeList.size();i++) {
				sheet.setColumnWidth(i, colSizeList.get(i)*256);
			}
		}
	}
	
	// 公式对象
	public static class Formula implements Serializable{
		private static final long serialVersionUID = 1L;
		private String formulaStr;

		public Formula() {
			super();
		}
		public Formula(String formulaStr) {
			super();
			this.formulaStr = formulaStr;
		}

		public String getFormulaStr() {
			return formulaStr;
		}

		public void setFormulaStr(String formulaStr) {
			this.formulaStr = formulaStr;
		}
	}
	
	public static Formula makeFormula(String formula) {
		Formula f = new Formula(formula);
		return f;
	}
	
	public XSSFFont setFont(boolean bold, String fontName, short size){
		XSSFFont font = wb.createFont();
		font.setBold(bold);
		font.setFontName(fontName);
		font.setFontHeightInPoints(size);
		return font;
	}

	public static void main(String[] args) {
		File file=new File("C:\\Users\\zmm\\Desktop\\新建文件夹\\test.xlsx");
		try {
			// u=new ExcelReadUtil(file);
			XlsxWriterUtil writer = new XlsxWriterUtil("aaaa");
			List<List<Object>> fileData = new ArrayList<List<Object>>();
			List<Object> s = new ArrayList<Object>();
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add("一123");
			s.add(new Date());
			//s.add(XlsxWriterUtil.makeFormula("A1+B1"));
			fileData.add(s);
			List<Object> s1 = new ArrayList<Object>();
			s1 = s;
			fileData.add(s1);
			int length = fileData.get(0).size()-1;
			XSSFCellStyle style = writer.newCellStyle();
			XSSFCellStyle style2 = writer.newCellStyle();

			style.setAlignment(HorizontalAlignment.CENTER);
			style2.setAlignment(HorizontalAlignment.RIGHT);

			style.setFont(writer.setFont(true, "宋体", (short)11));
			style2.setFont(writer.setFont(false, "宋体", (short)11));
			writer.writeMergeRow(0, length, "测试",style);
			writer.writeMergeRow(1,length,"导出日期",style2);
			writer.writeGridTitle(s,4);
			writer.writeRows(fileData, 0,true);
			writer.autoSizeColumn(length);
			writer.writeExcel(new FileOutputStream(file));
			System.out.println("**");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 行复制功能
	 *
	 * @param fromRow
	 * @param toRow
	 */
	public void copyRow(XSSFRow fromRow, XSSFRow toRow) {
		toRow.setHeight(fromRow.getHeight());
		for(Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
			XSSFCell tmpCell = (XSSFCell) cellIt.next();
			XSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(tmpCell, newCell, true);
		}
		Sheet worksheet = fromRow.getSheet();
		for(int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if(cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(), (toRow.getRowNum() +
						(cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
						.getFirstColumn(), cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

	/**
	 * 复制单元格
	 *
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag true则连同cell的内容一起复制
	 */
	public void copyCell(XSSFCell srcCell, XSSFCell distCell, boolean copyValueFlag) {
		XSSFCellStyle newStyle = wb.createCellStyle();
		XSSFCellStyle srcStyle = srcCell.getCellStyle();
		newStyle.cloneStyleFrom(srcStyle);
		newStyle.setFont(wb.getFontAt(srcStyle.getFontIndex()));
		//样式
		distCell.setCellStyle(newStyle);
		//评论
		if(srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if(copyValueFlag) {
			if(srcCellType == XSSFCell.CELL_TYPE_NUMERIC) {
				if(DateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if(srcCellType == XSSFCell.CELL_TYPE_STRING) {
				distCell.setCellValue(srcCell.getRichStringCellValue());
			} else if(srcCellType == XSSFCell.CELL_TYPE_BLANK) {

			} else if(srcCellType == XSSFCell.CELL_TYPE_BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if(srcCellType == XSSFCell.CELL_TYPE_ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if(srcCellType == XSSFCell.CELL_TYPE_FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else {
			}
		}
	}

	public void setColumnSize() {

	}
}
