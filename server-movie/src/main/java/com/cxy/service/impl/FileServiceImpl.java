package com.cxy.service.impl;

import com.cxy.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.UUID;

@Service
//用来控制文件上传的
public class FileServiceImpl implements FileService {
    final String BASE_PATH = "D:\\vscode\\Do It\\EasyMovie\\EasyMovieServer\\server-movie\\src\\main\\resources\\static";

    @Override
    public String upload(MultipartFile multipartFile) {
        String suffix =
                multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        // 使用uuid重新生成文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String typeDir = "/image/";
        String[] videoTypeList = new String[]{".mp4", ".avi", ".flv"};
        if (Arrays.asList(videoTypeList).contains(suffix)) {
            typeDir = "/video/";
        }
        File dir = new File(BASE_PATH + typeDir);
        if (!dir.exists()) {
            // 不存在目录就创建
            dir.mkdirs();
        }
        try {
            // 将临时文件转存到目录下
            multipartFile.transferTo(new File(BASE_PATH + typeDir + fileName + suffix));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "http://localhost:8920" + typeDir + fileName + suffix;
    }

    @Override
    public void downLoad(String url, HttpServletResponse response) {
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(url));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
