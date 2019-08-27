package com.example.untils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CldCommonUntils {

    protected static final Logger logger = LoggerFactory.getLogger(CldCommonUntils.class);

    /**
     * 在c盘文件夹中生成二维码
     * @param content
     * @return
     */
    public static Boolean makeQrCode(String content){
        String location = "C:\\QrCode";
        File file = new File(location);
        if(!file.exists()){
            file.mkdir();
        }
        return makeQrCode(content,location);
    }

    /**
     * 指定生成二维码位置
     * @param content
     * @param location
     * @return
     */
    public static Boolean makeQrCode(String content,String location ){
        int width=300;
        int height=300;
        return makeQrCode(width,height,content,location);
    }

    /**
     * 指定生成二维码位置以及大小
     * @param width
     * @param height
     * @param content
     * @param location
     * @return
     */
    public static Boolean makeQrCode(int width,int height,String content,String location ){
        //二维码格式
        String format = "jpg";
        //定义二维码参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN,2);
        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            Path file = new File(location+"\\img."+format).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从大集合中取等量数据
     * @param list  大集合
     * @param num   每次取的个数
     * @return
     */
    public static List<List> subList(List list , int num){
        List<List> returnList =new ArrayList();
        for(int i=0;i<list.size();i=i+num){
            //最后一次截取集合
            if(i+num>list.size()){
                num = list.size()-i;
            }
            List newList = list.subList(i, i + num);
            returnList.add(newList);
        }
        return returnList;
    }



    /**
     * MD5加密()
     * @param res
     * @return
     */
    public static String encodeMD5(String res) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(res.getBytes("utf-8"));
            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * base64编码
     * @param res
     * @return
     */
    public static String encode(String res) {
        return new String(Base64.encodeBase64(res.getBytes()));
    }


    /**
     * 流转 字节数组
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int ch;
        while ((ch = is.read(buffer)) != -1) {
            bytestream.write(buffer,0,ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }

    /**
     * 删除文件
     * @param f
     */
    public static void delete(File f,String format) {
        File[] fi=f.listFiles();
        if(fi!=null){
            for (File file : fi) {
                if(file.isDirectory()){
                    delete(file , format);
                }else if(file.getName().substring(file.getName().lastIndexOf(".")+1).equals(format)){
                    logger.info("成功删除"+file.getName());
                    file.delete();
                }
            }
        }
    }

    /**
     * 格式化json
     * @param jsonStr
     * @return
     */
    public  String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     * @param sb
     * @param indent
     */
    private  void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }


    /**
     * 压缩
     * @param str
     * @return
     * @throws IOException
     */
    public static String newcompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return "";
        }

        byte[] tArray;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        try {
            gzip.write(str.getBytes("UTF-8"));
            gzip.flush();
        } catch (Exception e){

        }finally {
            gzip.close();
        }

        tArray = out.toByteArray();
        out.close();

        BASE64Encoder tBase64Encoder = new BASE64Encoder();
        return tBase64Encoder.encode(tArray);
    }

    /**
     * 解压
     * @param str
     * @return
     * @throws IOException
     */
    public static String newuncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return "";
        }

        BASE64Decoder tBase64Decoder = new BASE64Decoder();
        byte[] t = tBase64Decoder.decodeBuffer(str);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(t);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        try {
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        }finally{
            gunzip.close();
        }
        in.close();
        out.close();
        return out.toString("UTF-8");
    }

    /**********************************************处理时间格式**********************************************************************/

    public static String yyyy_MM_dd_HH_mm_ss= "yyyy-MM-dd HH:mm:ss";
    public static String yyyy_MM_dd= "yyyy-MM-dd";

    /**
     * 格式化，yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dataToString(Date date) {
        return dataToString(date, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dataToString(Date date, String pattern) {
        if (date == null || pattern == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String result = df.format(date);
        if (result.equalsIgnoreCase("0001-01-01 00:00:00")) {
            result = "";
        }
        return result;
    }



    public static void ExportWithResponse(String sheetName,
                                          String titleName,
                                          String fileName,
                                          int columnNumber,
                                          int[] columnWidth,
                                          String[] columnName,
                                          String[][] dataList,
                                          HttpServletResponse response) throws Exception {
        if (columnNumber == columnWidth.length&& columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++)
            {
                for (int j = 0; j <= i; j++)
                {
                    if (i == j)
                    {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并列标题
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式

            // 第四.一步，创建表头的列
            for (int i = 0; i < columnNumber; i++)
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }

            // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.length; i++)
            {
                row = sheet.createRow((int) i + 2);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber; j++)
                {
                    datacell = row.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

            // 第六步，将文件存到浏览器设置的下载位置
            String filename = fileName + ".xls";
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            OutputStream out = response.getOutputStream();



            //输出Excel文件
//            OutputStream out=response.getOutputStream();
//            response.reset();
//            response.setHeader("Content-disposition", "attachment; filename=report.xls");//文件名这里可以改
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");

            try {
                wb.write(out);// 将数据写出去
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            } finally {
                out.close();
            }

        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }

    }

    public static void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void downloadFile(String path, HttpServletResponse response) throws Exception{
        File file = new File(path);
        String filename = file.getName();
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            fin = new FileInputStream(file);
            out = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));

            byte[] buffer = new byte[1024];
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();

        }
    }

    /**
     *
     * @param titleHeight 标题高度
     * @param headHeight    表头高度
     * @param sheetName     sheet名称
     * @param titleName     标题名称
     * @param fileName      下载文件名称
     * @param columnWidth       每列宽
     * @param columnName        表头名称
     * @param dataList      导出数据，为维数组
     * @param response
     * @throws Exception
     */
    public static void ExportWithResponse2( int titleHeight,
                                            int headHeight,
                                            String sheetName,
                                          String titleName,
                                          String fileName,
                                          int[] columnWidth,
                                          String[] columnName,
                                          String[][] dataList,
                                          HttpServletResponse response){
        //if (columnWidth.length == columnName.length) {


            int columnNumber=columnWidth.length;


            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++)
            {
                for (int j = 0; j <= i; j++)
                {
                    if (i == j)
                    {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            /**********************************************标题操作样式以及值*************************************************/
            // 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(titleHeight);// 设备标题的高度

            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并列标题
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式



        /**********************************************表头操作样式*************************************************/
            // 创建第1行 也就是表头
            HSSFRow row2 = sheet.createRow((int) 1);
            row2.setHeightInPoints(headHeight);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式


        /**********************************************设置表头值*****************************************************/
            // 第四.设置表头值
            for (int i = 0; i < columnNumber; i++)
            {
                HSSFCell cell = row2.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }


        /**********************************************设置数据行样式*****************************************************/
        HSSFCellStyle zidonghuanhang = wb.createCellStyle();
        HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
            // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.length; i++)
            {
                //标题行，表头行，已用去
                HSSFRow dataRow = sheet.createRow((int) i + 2);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
//                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中

                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;

                /**********************************************设置数据行值*****************************************************/
                for (int j = 0; j < columnNumber; j++)
                {
                    datacell = dataRow.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

        OutputStream out = null;
            try{
                // 第六步，将文件存到浏览器设置的下载位置
                String filename = fileName + ".xls";
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



    /**
     * 写入txt文件
     *
     * @param filePath
     * @param detailList
     * @throws Exception
     */
    public static boolean txtWriter(String filePath,List<String> detailList) throws Exception{
        BufferedWriter br = null;
        try{
            File file = new File(filePath);
            // 获取目录
            File fileT = new File(file.getParent());

            // 文件不存在，建立文件
            if(!file.exists()){
                fileT.mkdirs();
                file.createNewFile();//不存在则创建
            }
            // 往文件里面写内容
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file),"x-UTF-16LE-BOM");
            br = new BufferedWriter(writer);
            for(String temp:detailList){
                br.write(temp+"\r\n");
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            br.close();
        }
        return true;
    }


    /**
     * base64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) {
        return (new BASE64Encoder()).encodeBuffer(key.getBytes());
    }


    /**
     * base64解密
     * @param key
     * @return
     */
    public static String decryptBASE64(String key){
        String dekey="";
        try {
            dekey = new String(new BASE64Decoder().decodeBuffer(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dekey;
    }


    /**
     * 全屏截图
     * @param location
     * @param fileName
     * @param imageFormat
     */
    public static void screenshot(String location ,String fileName, String imageFormat){
        //获取屏幕分辨率
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        try {
            //拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot=(new Robot()).createScreenCapture(
                    new Rectangle(0,0,(int)d.getWidth(),(int)d.getHeight()));
//            serialNum++;
            //根据文件前缀变量和文件格式变量，自动生成文件名
            String name=location+"\\"+fileName+"."+imageFormat;
            System.out.println(name);
            File f=new File(name);
            System.out.println("Save File-"+name);
            //将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.println("..Finished");

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
