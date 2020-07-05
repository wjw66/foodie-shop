package com.wjw.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : wjwjava01@163.com
 * @date : 23:10 2020/7/5
 * @description : 判断当前用户的是否合法
 * 不能直接使用，需要注册到spring容器中
 */
public class UserTokenInterceptor implements HandlerInterceptor {
    /**
     * 拦截请求，在访问controller之前
     * 判断当前用户的是否合法，使用的是该方法
     * @param request
     * @param response
     * @param handler
     * @return false：请求被拦截，验证不通过，驳回请求 true：请求验证通过，放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    /**
     * 拦截请求，在访问controller之后，渲染视图之前
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
