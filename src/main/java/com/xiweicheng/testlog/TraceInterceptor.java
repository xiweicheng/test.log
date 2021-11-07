package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TraceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("enter TraceInterceptor");

        // 清空
//        MDC.clear();

        MdcUtils.setTraceIdIfAbsent();

        //后续逻辑... ...
        return true;
    }
}
