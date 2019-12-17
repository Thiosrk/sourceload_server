package com.csga.sourceload_server.Utils.Data;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static File MultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File tempFile = File.createTempFile(UUIDGenerator.getUUID(), prefix);
        // MultipartFile to File
        multipartFile.transferTo(tempFile);
        return tempFile;
    }

    public static void deleteFile(File... files){
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
