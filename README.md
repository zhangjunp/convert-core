# pdf & word convert

### 介绍
由于市面上的文档转换，总是有页数限制或者收费，然而工作我的小伙伴们中时而会遇到转换类的需求，所以自己专门做了个简单的转换服务来解决……


### 本项目快速开始
底层技术基于 jodConvert 和 spire.pdf.free实现 office文档转换及pdf转word功能:
* jodConvert实现office文档转换依赖于 openOffice 或者 libOffice，需要提前安装好相关组件
* 参考 [jodConvert documentation](https://github.com/sbraconnier/jodconverter/wiki) 了解jodConvert

* step1：安装好openOffice （PS:如果是linux系统,可运行 install.sh install_font.sh 完成openOffice及字体库的安装）或者 libOffice
* step2：根目录下创建 tmp/,启动本项目 [http://localhost:port/index] 实现转换


### 市面上常见解决方案
* 文件文档在线预览项目解决方案，业内付费产品有【 [永中office](http://dcs.yozosoft.com/) 】【 [office365](http://www.officeweb365.com/) 】【[idocv](https://www.idocv.com/) 】等
* 开源解决方案：[kkFileView](https://kkfileview.keking.cn/zh-cn/docs/home.html) 此项目为开源文档预览服务,本项目中的install.sh 借鉴于此项目中
