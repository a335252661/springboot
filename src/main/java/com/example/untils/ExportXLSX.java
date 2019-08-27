package com.example.untils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class ExportXLSX {

    public static void export(String suquenceMarkShopOrderNo, List<List<Object>> data,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
        //创建一个SXSSFWorkbook
        //关闭自动刷新
        SXSSFWorkbook wb = new SXSSFWorkbook(-1);
        //创建一sheet
        Sheet sheet = wb.createSheet();
        for(int rownum=0;rownum<data.size();rownum++){
            //创建一个行
            Row row = sheet.createRow(rownum);
            for (int cellnum=0;cellnum<data.get(rownum).size();cellnum++){
                //创建单元格
                Cell cell = row.createCell(cellnum);
//                String adress = new CellReference(cell).formatAsString();
                cell.setCellValue((data.get(rownum).get(cellnum)+"").equals("null")?"":(data.get(rownum).get(cellnum)+""));//向单元格中写入数据
            }
            //手动控制行刷新到磁盘的方式
            if (rownum%10000==0){//一万行向磁盘写一次
                ((SXSSFSheet)sheet).flushRows(100);//保留最后100行并刷新所有其他行
            }
        }
        OutputStream out = null;
        String fileName = suquenceMarkShopOrderNo +  DateTimeUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        try{
            // 第六步，将文件存到浏览器设置的下载位置
            String filename = fileName + ".xlsx";
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            out = response.getOutputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            wb.write(out);// 将数据写出去
            String str = "导出" + fileName + "成功！";
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
            String str1 = "导出" + fileName + "失败！";
            System.out.println(str1);
        } finally {
            try{
                out.close();
            }catch (IOException e){
                throw new RuntimeException(e);
            }

        }


    }
}
