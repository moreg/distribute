package com.jdsw.distribute.util;

public class PathUtil {


    //确定该系统下的标识符"/"或"\"
    //private static String seperator = System.getProperty("file.separator");

    //确定系统再设置不同存放图片的基础路径
    public static String getBasePath(){
        //检测系统是window还是linux
        String os=System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            //window系统设置路径
            basePath="E:\\resources";
        }else {
            //linux系统设置路径
            basePath="/home/resources";
        }
        //将路径中的标识符换成对应系统标识符
        //basePath=basePath.replace("/",seperator);
        return basePath;
    }

    //确定图片存放的绝对路径（未加上文件名）
    public static String getImgPath(Integer userId,String str){
        String imagPath="/"+str+"/"+userId+"/upload/images/";
        //imagPath=imagPath.replace("/",seperator)
        return imagPath;
    }
    //确定音频存放的绝对路径（未加上文件名）
    public static String getVideoPath(Integer userId,String str){
        String videoPath="/"+str+"/"+userId+"/upload/video/";
        //videoPath=videoPath.replace("/",seperator)
        return videoPath;
    }


}
