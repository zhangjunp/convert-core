package com.zhangjp.doc.converter.service.impl;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.zhangjp.doc.converter.service.IConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
@Slf4j
public class Pdf2WordService implements IConvertService {

    @Override
    public void convert(InputStream inputStream, File file, String outputFormat) {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromStream(inputStream);
        pdfDocument.saveToFile(file.getPath(), FileFormat.DOCX);
    }
}
