package com.xingqi.code.commonlib.utils;

import java.io.File;

public class FileUtil {

    public static File getFile(String filePath){
        File file = new File(filePath);
        return file;
    }

    public static void deleteFiles(File parentDir){
        for(File file:parentDir.listFiles()){
            file.delete();
        }
    }

    public static boolean isExistFile(File file){
        if(file.exists()){
            return true;
        }
        return false;
    }

    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
