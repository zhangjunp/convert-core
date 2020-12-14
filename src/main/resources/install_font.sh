#!/bin/bash
cd /tmp
wget http://kkfileview.keking.cn/fonts.zip && unzip -o fonts.zip
cp -r zhFonts/ /usr/share/fonts
cd /usr/share/fonts
yum install mkfontscale
yum install fontconfig
mkfontscale && mkfontdir && fc-cache


cd /tmp
wget http://ftp.tu-chemnitz.de/pub/linux/dag/redhat/el7/en/x86_64/rpmforge/RPMS/cabextract-1.4-1.el7.rf.x86_64.rpm
wget https://rpmfind.net/linux/sourceforge/m/ms/mscorefonts2/rpms/msttcore-fonts-installer-2.6-1.noarch.rpm
rpm -ivh cabextract-1.4-1.el7.rf.x86_64.rpm
rpm -ivh msttcore-fonts-installer-2.6-1.noarch.rpm
echo success
