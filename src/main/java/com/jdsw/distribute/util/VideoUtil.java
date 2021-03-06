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

public class VideoUtil {
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 处理视频并保存视频
     * 并返回新生成图片的相对值路径
     */
    public static String saveVideo(String userId, MultipartFile file,String str) throws IOException {
        //从pathUtil中获取图片基础路径和相对路径
        String basePath = PathUtil.getBasePath();
        String uploadPath = PathUtil.getVideoPath(userId,str);

        //输入输出流
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (file != null ) {
                //获取文件原本名
                String fileName = file.getOriginalFilename();
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
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
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
        return uploadPath;
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
     * 获取输入文件流的扩展名,如.mp4，.mp3, .avi
     */
    public static String getFileExtension(String fileName) {
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
