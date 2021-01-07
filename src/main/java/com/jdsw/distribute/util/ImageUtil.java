package com.jdsw.distribute.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {


    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 处理图片并对图片进行本地保存
     * 并返回新生成图片的相对值路径
     */
    public static String saveImage(Integer userId, MultipartFile[] files,String str) throws IOException {
        //从pathUtil中获取图片基础路径和相对路径
        String basePath = PathUtil.getBasePath();
        String uploadPath = null;

        //输入输出流
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        StringBuffer str2 = new StringBuffer();
        try {
            if (files != null && files.length > 0) {
                for (int i = 0;i<files.length;i++){
                    uploadPath = PathUtil.getImgPath(userId,str);
                    //获取文件原本名
                    String fileName = files[i].getOriginalFilename();
                    if (StringUtils.isNotBlank(fileName)) {
                        // 获取不重复的随机名
                        String realFileName = getRandomFileName();
                        // 获取文件的扩展名如png,jpg等
                        String extension = getFileExtension(fileName);
                        //生成新的文件名
                        fileName = realFileName + extension;
                        //生成最终路径
                        String fileFinallyPath = basePath + uploadPath + fileName;
                        //生成相对路径，存储与数据库
                        uploadPath = uploadPath + fileName;
                        File outputFile = new File(fileFinallyPath);
                        makeDirPath(outputFile);

                        fileOutputStream = new FileOutputStream(outputFile);
                        inputStream = files[i].getInputStream();
                        IOUtils.copy(inputStream, fileOutputStream);

                    }
                    str2.append(uploadPath).append(",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //文件操作结束时关闭文件
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return str2.substring(0, str2.length()-1);
    }

    /**
     * 创建目标路径所涉及到的目录
     */
    private static void makeDirPath(File outputFile) {
        if(outputFile.getParentFile()!=null||!outputFile.getParentFile().isDirectory()){
            //如果文件夹未创建，即逐层创建
            outputFile.getParentFile().mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名,如jpg,png
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

}
