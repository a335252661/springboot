package com.example.untils;

import com.example.untils.constant.FileImportConstants;
import com.example.untils.constant.FileImportConstants;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	private static final Logger logger = Logger.getLogger(ZipUtil.class);

	/**
	 * ZIP文件解压
	 * 
	 * @param fileName ZIP文件所在路径
	 * @param Parent 文件解压路径
	 */
    public static Map<String,Object> zipDecompressing(String fileName,String Parent) {
    	//结果
    	Map<String,Object> result = new HashMap<String, Object>();
    	
        // TODO Auto-generated method stub  
        long startTime=System.currentTimeMillis();  
        try {  
        	File file = new File(fileName);
        	String zipFileName = file.getName();
        	result.put(FileImportConstants.IMPORT_FILE_ZIP_NAME, zipFileName);
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(file));//输入源zip路径  
            BufferedInputStream Bin=new BufferedInputStream(Zin);  
            File Fout=null;  
            ZipEntry entry;  
            try {
            	Map<String, String> fileMap = new HashMap<String, String>();
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
                    Fout=new File(Parent,entry.getName());  
                    if(!Fout.exists()){  
                        (new File(Fout.getParent())).mkdirs();  
                    }  
                    FileOutputStream out=new FileOutputStream(Fout);  
                    BufferedOutputStream Bout=new BufferedOutputStream(out);  
                    int b;  
                    while((b=Bin.read())!=-1){  
                        Bout.write(b);  
                    }
                    fileMap.put(entry.getName(), Fout.getPath());
                    Bout.close();  
                    out.close();  
                }  
                Bin.close();  
                Zin.close();  
                result.put(FileImportConstants.IMPORT_FILE_NAME_PATH_MAP, fileMap);
            } catch (IOException e) {  
                e.printStackTrace();  
                logger.error(e);
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            logger.error(e);
        }
        long endTime=System.currentTimeMillis();  
        result.put(FileImportConstants.IMPORT_FILE_ZIP_TIME, endTime-startTime);
        return result;
    }

    
    /**
     * 文件压缩
     * 
     * @param zipFileName
     * @param inputPath
     */
    public static void zipCompressing(String zipFileName, List<String> inputPaths){  
        ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName)); 
	        BufferedOutputStream bo = new BufferedOutputStream(out);  
	        for (String inputPath : inputPaths) {
	        	File inputFile = new File(inputPath);
	        	zip(out, inputFile, inputFile.getName(), bo);  
			}
	        bo.close();  
	        out.close(); // 输出流关闭  
		} catch (Exception e) {
        	e.printStackTrace();
        	logger.error(e);
		}
    }  
  
    private static void zip(ZipOutputStream out, File f, String base,BufferedOutputStream bo) {  
        try {
	    	if (f.isDirectory()) {  
	            File[] fl = f.listFiles();  
	            if (fl.length == 0) {  
	                out.putNextEntry(new ZipEntry(base + File.separator)); // 创建zip压缩进入点base  
	            }  
	            for (int i = 0; i < fl.length; i++) {  
	                zip(out, fl[i], base + File.separator + fl[i].getName(), bo); // 递归遍历子文件夹  
	            }  
	        } else {  
	            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base  
	            FileInputStream in = new FileInputStream(f);  
	            BufferedInputStream bi = new BufferedInputStream(in);  
	            int b;  
	            while ((b = bi.read()) != -1) {  
	                bo.write(b); // 将字节流写入当前zip目录  
	            }
	            bo.flush();
	            bi.close();  
	            in.close(); // 输入流关闭  
	        }  
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error(e);
        }
    }
	
    /**
     * 删除文件夹
     * 
     * @param filePath 
     */
    public static void deleteAllFilesOfDir(String filePath){
    	File file = new File(filePath);
    	deleteAllFilesOfDir(file);
    }
    
    /**
     * 删除文件夹
     * 
     * @param path
     */
    private static void deleteAllFilesOfDir(File path) {  
        if (!path.exists())  
            return;  
        if (path.isFile()) {  
            path.delete();  
            return;  
        }  
        File[] files = path.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            deleteAllFilesOfDir(files[i]);  
        }  
        path.delete();  
    } 

	public static void main(String[] args){
//		String fileName = "C:\\Users\\zmm\\Desktop\\test\\20150423_M.ZIP";
//		String pathString = "C:\\Users\\zmm\\Desktop\\test";
//		Map<String, Object> result = zipDecompressing(fileName,pathString);
//		System.out.println(result.get(FileImportConstants.IMPORT_FILE_ZIP_NAME));
//		System.out.println(result.get(FileImportConstants.IMPORT_FILE_NAME_PATH_MAP));
//		System.out.println(result.get(FileImportConstants.IMPORT_FILE_ZIP_TIME));
		
		String pathString = "D:\\temp\\das1";
		List<String> paths = new ArrayList<String>();
		paths.add(pathString+"\\COURSE_20150518145510658.txt");
		paths.add(pathString+"\\LOCATE_20150518145510658.txt");
		paths.add(pathString+"\\MERCHAN_20150518145510658.txt");
		paths.add(pathString+"\\STORE_20150518145510658.txt");
		paths.add(pathString+"\\VENDOR_20150518145510658.txt");
		paths.add(pathString+"\\WORKER_20150518145510658.txt");
		zipCompressing("D:\\temp\\aaa.zip",paths);
	}
}
