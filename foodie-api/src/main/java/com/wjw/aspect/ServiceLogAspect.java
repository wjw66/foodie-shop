package com.wjw.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author : wjwjava01@163.com
 * @date : 11:34 2020/5/5
 * @description : service切面,记录运行时间
 */
@Slf4j
@Aspect
@Component
public class ServiceLogAspect {
    private final Integer WARN_TIME = 2000;
    private final Integer ERROR_TIME = 3000;
    /**
     * AOP通知：
     * 1. 前置通知：在方法调用之前执行
     * 2. 后置通知：在方法正常调用之后执行
     * 3. 环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4. 异常通知：如果在方法调用过程中发生异常，则通知
     * 5. 最终通知：在方法调用之后执行(不管是否成功)
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *代表所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.wjw.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("======== 开始执行 {}.{} ========",
                //获取类名和方法名
                joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

        Long begin = System.currentTimeMillis();
        //执行目标service
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();

        Long takeTime = end - begin;

        String str = "====== 执行结束，耗时：{} 毫秒 ======";

        if (takeTime > ERROR_TIME) {
            log.error(str, takeTime);
        } else if (takeTime > WARN_TIME) {
            log.warn(str, takeTime);
        } else {
            log.info(str, takeTime);
        }
        return result;
    }
}
