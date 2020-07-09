package com.wjw.interceptor;

import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author : wjwjava01@163.com
 * @date : 23:10 2020/7/5
 * @description : 判断当前用户的是否合法
 * 不能直接使用，需要注册到spring容器中
 */
@Slf4j
public class UserTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisOperator redisOperator;


    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 拦截请求，在访问controller之前
     * 判断当前用户的是否合法，使用的是该方法
     *
     * @param request
     * @param response
     * @param handler
     * @return false：请求被拦截，验证不通过，驳回请求 true：请求验证通过，放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String redisUserToken = REDIS_USER_TOKEN + ":" + userId;
            String uniqueToken = redisOperator.get(redisUserToken);
            if (StringUtils.isBlank(uniqueToken)) {
                log.info("用户信息不正确,请检查登陆状态!");
                returnErrorResponse(response, JSONResult.errorMsg("用户信息不正确,请检查登陆状态!"));
                return false;
            }
            //账号在其他机器上登陆过了
            if (!Objects.equals(uniqueToken, userToken)) {
                log.info("账号在异地登陆....");
                returnErrorResponse(response, JSONResult.errorMsg("账号在异地登陆...."));
                return false;
            }
            return true;
        }
        return false;
    }

    private void returnErrorResponse(HttpServletResponse response, JSONResult result) {

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(Objects.requireNonNull(JsonUtils.objectToJson(result)).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拦截请求，在访问controller之后，渲染视图之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 拦截请求，在访问controller之后，渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
