package com.zhangjp.doc.converter.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtils extends org.apache.commons.io.FileUtils {
    public static final String FILE_DIR = "./file/";
    public static final String UPLOAD_FILE_DIR = "./fileUpload/";

    public static File saveFile(InputStream inputStream,String fileName) throws IOException {
        File file = new File(UPLOAD_FILE_DIR +fileName);
        FileUtils.copyInputStreamToFile(inputStream,file);
        return file;
    }

    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
