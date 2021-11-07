package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter("/**")
@Component
public class TraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        log.debug("enter TraceFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 从请求头中获取traceId
        String traceId = request.getHeader(Constants.TRACE_ID);

        // 不存在就生成一个
        if (traceId == null || "".equals(traceId)) {
            traceId = MdcUtils.createTraceId();
            log.debug("traceId not exists, new created: {}", traceId);
        } else {
            log.debug("traceId exists. {}", traceId);
        }
        // 放入MDC中，本文来源于工从号彤哥读源码
        MDC.put(Constants.TRACE_ID, traceId);
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
