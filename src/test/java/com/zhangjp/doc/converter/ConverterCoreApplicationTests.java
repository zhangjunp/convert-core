package com.zhangjp.doc.converter;

import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

@SpringBootTest
@Slf4j
class ConverterCoreApplicationTests {

    @Resource
    private DocumentConverter documentConverter;


    String filePath = "/Users/zhangjunping/Desktop/1024/liuzhenghao.pdf";

    @Test
    void contextLoads() {
        DocumentFormat documentFormat = DefaultDocumentFormatRegistry.DOCX;
        try {
            log.info("start convert ……{}",documentFormat.getExtension());
            File inputFile = new File(filePath);
            File file = new File("./file/"+ UUID.randomUUID()+"."+documentFormat.getExtension());
            documentConverter.convert(inputFile).as(documentFormat).to(file).execute();
            log.info("end convert ……");
        } catch (OfficeException e) {
            log.error("parse office e",e);
        }
    }

}
