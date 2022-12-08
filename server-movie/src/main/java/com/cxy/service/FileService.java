package com.cxy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    //文件上传
    //成功返回保存路径
    String upload(MultipartFile multipartFile);

    //文件下载
    void downLoad(String url, HttpServletResponse response);
}
