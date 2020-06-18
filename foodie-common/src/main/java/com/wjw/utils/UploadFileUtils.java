package com.wjw.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author : wjwjava01@163.com
 * @date : 18:32 2020/5/30
 * @description :
 */
public class UploadFileUtils {
    private static final String[] IMG_SUFFIX = {"BMP", "JPG", "JPEG", "PNG"};

    public static boolean imgChecked(String suffixName) {

        if (StringUtils.isBlank(suffixName)) {
            throw new RuntimeException("图片后缀名不正确!");
        }

        for (String imgSuffix : IMG_SUFFIX) {
            if (imgSuffix.equalsIgnoreCase(suffixName)) {
                return true;
            }
        }
        return false;
    }
}
