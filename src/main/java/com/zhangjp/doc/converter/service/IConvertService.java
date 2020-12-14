package com.zhangjp.doc.converter.service;

import java.io.File;
import java.io.InputStream;

public interface IConvertService {

    void convert(InputStream inputStream, File file, String outputFormat) ;
}
