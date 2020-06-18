package com.wjw.exception;

import com.wjw.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author : wjwjava01@163.com
 * @date : 0:11 2020/5/31
 * @description : 捕获spring的异常处理类
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    /**
     * 上传文件大小超过限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e){
        return JSONResult.errorMsg("文件大小超过允许上传的最大值!");
    }
}
