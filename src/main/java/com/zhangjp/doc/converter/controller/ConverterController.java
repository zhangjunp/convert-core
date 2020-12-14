package com.zhangjp.doc.converter.controller;

import com.google.common.base.Charsets;
import com.zhangjp.doc.converter.base.BaseResponse;
import com.zhangjp.doc.converter.service.impl.ConvertContext;
import com.zhangjp.doc.converter.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;



@Slf4j
@Api(tags = "doc转换")
@RequestMapping
@Controller
public class ConverterController {

    @Resource
    private ConvertContext convertContext;

    @Resource
    private DocumentConverter documentConverter;

    @GetMapping("/index")
    public String index() {
        return "converter";
    }


    @PostMapping("/converter")
    @ApiOperation("在线转换&下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputFile",value = "选择要转换的文件",dataType = "__file", paramType = "form",required = true),
            @ApiImplicitParam(name = "outputFormat",value = "输出文档格式",dataType = "string", paramType = "query",required = false)
    })
    public ResponseEntity<Object> convertAndDownload(
            @RequestParam("inputFile") final MultipartFile inputFile,
            @RequestParam(name = "outputFormat", required = false,defaultValue = "pdf") final String outputFormat) {
        if (inputFile.isEmpty()) {
            return ResponseEntity.ok(BaseResponse.fail("inputFile must be not null"));
        }
        if (StringUtils.isBlank(outputFormat)) {
            return ResponseEntity.ok(BaseResponse.fail("outputFormat must be not null"));
        }
        final DocumentFormat targetFormat =
                DefaultDocumentFormatRegistry.getFormatByExtension(outputFormat);
        if (ObjectUtils.isEmpty(targetFormat)) {
            return ResponseEntity.ok(BaseResponse.fail(String.format("%s,this outputFormat is not support",outputFormat)));
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("convertAndDownload inputFile originFileName:{},originFileSize:{} kb,outputFormat:{}",inputFile.getOriginalFilename(),inputFile.getSize() / 1000 ,outputFormat);
        File file = null;
        try {
            File uploadFile = FileUtils.saveFile(inputFile.getInputStream(), FileUtils.UUID() +FilenameUtils.getExtension(inputFile.getOriginalFilename()));
            String id = UUID.randomUUID().toString().replace("-","");
            String fileName =  id + "." + targetFormat.getExtension();
            String downloadFileName = FilenameUtils.getBaseName(inputFile.getOriginalFilename()) + "."+ targetFormat.getExtension();
            log.info("local fileName:{}",fileName);
            file = new File("./file/" + fileName);
            convertContext.doConvert(uploadFile,file,outputFormat);
            // converter.convert(inputFile.getInputStream()).as(targetFormat).to(file).execute();
            log.info("convertAndDownload inputFile originFileName:{} costTime:{} ms",inputFile.getOriginalFilename(),stopWatch.getTime(TimeUnit.MILLISECONDS));
            return ResponseEntity.ok()
                    .header("Content-Disposition","attachment;filename=" + downloadFileName)
                    .body(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            log.error("convert ex",e);
        }
        return null;
    }


    @PostMapping("/docPdfConvert")
    @ApiOperation("在线转换&下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputFile",value = "选择要转换的文件",dataType = "__file", paramType = "form",required = true),
    })
    public ResponseEntity<Object> docPdfConvert(@RequestParam("inputFile") final MultipartFile inputFile){
        log.info("convertAndDownload inputFile originFileName:{},originFileSize:{} kb",inputFile.getOriginalFilename(),inputFile.getSize() / 1000);
        String extension = FilenameUtils.getExtension(inputFile.getOriginalFilename());
        // bugFix 文件名包含括号会导致转换失败
        String baseName = FilenameUtils.getBaseName(inputFile.getOriginalFilename())
                .replaceAll("\\(|\\)", "")
                .replaceAll("（","").replaceAll("）","");
        if (!"pdf".equals(extension) && !"doc".equals(extension) && !"docx".equals(extension) ) {
            return ResponseEntity.ok().body("文件格式不符");
        }
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String targetExtension = "pdf";
            String changeName = FileUtils.UUID()+"&"+baseName;
            File uploadFile = FileUtils.saveFile(inputFile.getInputStream(), changeName+"." +extension);
            File file = null;
            if ("pdf".equals(extension)) {
                targetExtension = "doc";
                file = new File(FileUtils.FILE_DIR+changeName+"."+targetExtension);
                convertContext.doConvert(uploadFile,file,targetExtension);
            }
            if ("doc".equals(extension) || "docx".equals(extension)){
                file = new File(FileUtils.FILE_DIR+changeName+"."+targetExtension);
                documentConverter.convert(uploadFile).to(file).execute();
            }
            log.info("convertAndDownload inputFile originFileName:{} costTime:{} ms",inputFile.getOriginalFilename(),stopWatch.getTime(TimeUnit.MILLISECONDS));
            return ResponseEntity.ok()
                    .body(file.getName());
        } catch (IOException e) {
            log.error("ex:",e);
        } catch (OfficeException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/download")
    public ResponseEntity<Object> download(@RequestParam(value = "fileName") String fileName) throws IOException {
        log.info("fileName:{}",fileName);
        File file = FileUtils.getFile(FileUtils.FILE_DIR, fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName, Charsets.UTF_8.name()) )
                .body(FileUtils.readFileToByteArray(file));
    }



}
