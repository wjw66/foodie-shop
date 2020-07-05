package com.wjw.controller.center;

import com.wjw.center.CenterUserService;
import com.wjw.controller.BaseController;
import com.wjw.pojo.Users;
import com.wjw.pojo.bo.center.CenterUserBO;
import com.wjw.pojo.vo.UsersVO;
import com.wjw.resource.FileUpload;
import com.wjw.utils.CookieUtils;
import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.UploadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.wjw.exception.BindingResultException.getErrors;

/**
 * @author : wjwjava01@163.com
 * @date : 23:39 2020/5/25
 * @description :
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CentUserController extends BaseController {
    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

        if (Objects.isNull(file)) {
            return JSONResult.errorMsg("文件不能为空!");
        }
        //3.获得文件上传的名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            return JSONResult.errorMsg("文件名称不合法!");
        }
        //4.文件参数检测
        String[] suffixNameArr = filename.split("\\.");
        String suffixName = suffixNameArr[suffixNameArr.length - 1];
        boolean isPassChecked = UploadFileUtils.imgChecked(suffixName);
        if (!isPassChecked) {
            return JSONResult.errorMsg("图片格式不正确!");
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = "face-" + uuid + "." + suffixName;
        //5.头像保存的文件路径
        //在路径上为每个用户增加一个userId,区分不同用户上传的路径
        String uploadPathPrefix = userId;
        String imgPath = fileUpload.getImageUserFaceLocation() + File.separator + uploadPathPrefix + File.separator + newFileName;

        File outFile = new File(imgPath);

        if (outFile.getParentFile() != null) {
            //6.创建文件夹
            outFile.getParentFile().mkdirs();
        }
        //7.文件输出
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //8.图片地址入库
        String faceImgServerUrl = fileUpload.getFaceImgServerUrl();
        //用于提供web访问的地址
        uploadPathPrefix += ("/" + newFileName);
        String finalFaceUrl = faceImgServerUrl + uploadPathPrefix;
        Users userResult = centerUserService.updateUserFace(userId, finalFaceUrl);

        //增加token令牌，会整合进redis，使用分布式会话
        UsersVO usersVO = getUsersVO(userResult);
        //更新cookie信息
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

        return JSONResult.ok();
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Validated CenterUserBO centUserBO,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return JSONResult.errorMap(errorMap);
        }


        Users userResult = centerUserService.updateUserInfo(userId, centUserBO);
        //增加token令牌，会整合进redis，使用分布式会话
        UsersVO usersVO = getUsersVO(userResult);

        //更新cookie信息
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

        return JSONResult.ok();
    }

    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
    }
}
