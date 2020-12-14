package com.zhangjp.doc.converter.service.impl;

import com.zhangjp.doc.converter.service.IConvertService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@Slf4j
public class ConvertContext {

    @Resource
    private Map<String, IConvertService> convertServiceMap;


    public void doConvert(File srcFile, File targetFile, String outputFormat){
        String extension = FilenameUtils.getExtension(srcFile.getName());
        try {
        if ("pdf".equals(extension)) {
            convertServiceMap.get("pdf2WordService").convert(FileUtils.openInputStream(srcFile),targetFile,outputFormat);
        }else {
            convertServiceMap.get("doc2OtherService").convert(FileUtils.openInputStream(srcFile),targetFile,outputFormat);
        }
        } catch (IOException e) {
            log.error("ex:",e);
        }
    }
}
