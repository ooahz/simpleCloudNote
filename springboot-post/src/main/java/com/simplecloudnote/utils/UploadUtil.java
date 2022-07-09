package com.simplecloudnote.utils;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.Format;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 文件上传工具类
*/
public class UploadUtil {

//    读取配置文件设置的地址
    @Value("${upload.path}")
    private String filePath;

    /**
     *
     * @param file 文件流
     * @param filePath 文件上传路径
     * @param fileName 文件上传名
     * @return
     */
    public static String toUpload(MultipartFile file, String filePath, String fileName){
        try {
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file.transferTo(new File(folder, fileName));
            return ReturnResult.result("true", "上传成功！");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnResult.result("false", "上传失败！");
        }
    }
}
