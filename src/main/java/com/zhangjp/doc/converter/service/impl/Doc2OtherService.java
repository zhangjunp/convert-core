package com.zhangjp.doc.converter.service.impl;

import com.zhangjp.doc.converter.service.IConvertService;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class Doc2OtherService implements IConvertService {

    @Resource
    private DocumentConverter converter;

    /**
     * @description: word  ppt Excel  转 pdf
     * @author: zhangjp
     * @date: 2020/12/7 18:13
     */
    @Override
    public void convert(InputStream inputStream, File file, String outputFormat) {
        final DocumentFormat targetFormat =
                DefaultDocumentFormatRegistry.getFormatByExtension(outputFormat);
        assert targetFormat != null;
        try {
            converter.convert(inputStream).as(targetFormat).to(file).execute();
        } catch (OfficeException e) {
            log.error("Doc2Other ex:",e);
        }
    }
}
