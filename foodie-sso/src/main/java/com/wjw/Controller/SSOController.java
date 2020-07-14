package com.wjw.Controller;

import com.wjw.UserService;
import com.wjw.pojo.Users;
import com.wjw.pojo.vo.UserVO;
import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.MD5Utils;
import com.wjw.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author : wjwjava01@163.com
 * @date : 23:54 2020/7/10
 * @description :
 */
@Controller
public class SSOController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisOperator redisOperator;
    /**
     * 用户会话
     */
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    /**
     * 用户全局通行证
     */
    public static final String REDIS_USER_TICKET = "redis_user_ticket";
    /**
     * 用户临时通行证
     */
    public static final String REDIS_TMP_TICKET = "redis_tmp_ticket";
    /**
     * CAS端CookieName全局通行证的key
     */
    public static final String COOKIE_USER_TICKET = "cookie_user_ticket";

    @GetMapping("/login")
    public String login(String returnUrl, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        //是否登录校验
        // 1. 获取userTicket门票，如果cookie中能够获取到，证明用户登录过，此时签发一个一次性的临时票据并且回跳
        String userTicket = getCookie(request);

        boolean isVerified = verifyUserTicket(userTicket);
        if (isVerified) {
            String tmpTicket = createTmpTicket();
            return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
        }
        //用户从未登录过，第一次登录跳转到CAS统一登录页面
        return "login";
    }

    /**
     * CAS的统一登录接口
     * 目的：
     * 1. 登录后创建用户的全局会话                 ->  uniqueToken
     * 2. 创建用户全局门票，用以表示在CAS端是否登录  ->  userTicket
     * 3. 创建用户的临时票据，用于回跳回传          ->  tmpTicket
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String returnUrl, Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        model.addAttribute("returnUrl", returnUrl);

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            model.addAttribute("errmsg", "用户名或密码不能为空");
            return "login";
        }
        //2.实现登陆
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (Objects.isNull(userResult)) {
            model.addAttribute("errmsg", "用户名或密码不正确");
            return "login";
        }
        String userId = userResult.getId();

        //3.创建用户会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        String userTokenKey = REDIS_USER_TOKEN + ":" + userId;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userResult, userVO);
        userVO.setUserUniqueToken(uniqueToken);
        //k为userTokenKey,v为用户的实体类信息
        redisOperator.set(userTokenKey, JsonUtils.objectToJson(userVO));

        //4.生成ticket门票，全局门票，代表用户在CAS端登录过
        String userTicket = UUID.randomUUID().toString().trim();
        //4.1将生成的门票放入CAS端的cookie中
        setCookie(COOKIE_USER_TICKET, userTicket, response);

        //5.将ticket门票和用户id关联并放入redis中
        redisOperator.set(REDIS_USER_TICKET + ":" + userTicket, userId);

        //6.生成临时票据,回跳到用户端,由CAS发送的一次性临时票据
        String tmpTicket = createTmpTicket();
//        return "login";
        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    /**
     * 使用一次性临时票据来验证用户是否登录，如果登录过，把用户会话信息返回给站点
     * 使用完毕后，需要销毁临时票据
     *
     * @param tmpTicket
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public JSONResult verifyTmpTicket(String tmpTicket,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String redisTicketValue = redisOperator.get(REDIS_TMP_TICKET + ":" + tmpTicket);
        String userTicketValue = MD5Utils.getMD5Str(tmpTicket);
        //1.用户票据校验
        if (StringUtils.isBlank(redisTicketValue) && Objects.equals(redisTicketValue, userTicketValue)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }

        //2.校验通过，删除临时票据，返回用户信息
        redisOperator.del(REDIS_TMP_TICKET + ":" + tmpTicket);

        //3.返回用户信息给调用者
        String userTicket = getCookie(request);
        if (StringUtils.isBlank(userTicket)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }

        //3.1 redis中获取userId
        String userId = redisOperator.get(REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }

        //3.2 redis中获取user信息
        String userInfo = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userInfo)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }
        //3.3 返回user信息给调用者
        return JSONResult.ok(JsonUtils.jsonToPojo(userId, UserVO.class));
    }

    @PostMapping("/logout")
    @ResponseBody
    public JSONResult logout(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        // 0. 获取CAS中的用户门票
        String userTicket = getCookie(request);

        // 1. 清除userTicket票据，redis/cookie
        deleteCookie(COOKIE_USER_TICKET, response);
        redisOperator.del(REDIS_USER_TICKET + ":" + userTicket);

        // 2. 清除用户全局会话（分布式会话）
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);

        return JSONResult.ok();
    }
    /**
     * 校验CAS全局用户门票
     * @param userTicket
     * @return
     */
    private boolean verifyUserTicket(String userTicket) {

        // 0. 验证CAS门票不能为空
        if (StringUtils.isBlank(userTicket)) {
            return false;
        }

        // 1. 验证CAS门票是否有效
        String userId = redisOperator.get(REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)) {
            return false;
        }

        // 2. 验证门票对应的user会话是否存在
        String userRedis = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userRedis)) {
            return false;
        }

        return true;
    }
    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(userTicket -> userTicket.getName().equals(COOKIE_USER_TICKET))
                .map(Cookie::getValue)
                .findFirst().orElse(null);
    }

    /**
     * 创建临时票据
     */
    private String createTmpTicket() {
        String tmpTicket = UUID.randomUUID().toString().trim();

        try {
            redisOperator.set(REDIS_TMP_TICKET + ":" + tmpTicket, MD5Utils.getMD5Str(tmpTicket), 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpTicket;
    }

    private void setCookie(String key, String val, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    private void deleteCookie(String key,
                              HttpServletResponse response) {

        Cookie cookie = new Cookie(key, null);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

}
