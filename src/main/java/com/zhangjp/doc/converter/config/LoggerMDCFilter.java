package com.zhangjp.doc.converter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;


@WebFilter(urlPatterns = "/*",filterName = "LoggerMDCFilter")
@Configuration
@Slf4j
public class LoggerMDCFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("LoggerMDCFilter start");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String reqId = request.getParameter("reqId");
        if (StringUtils.isEmpty(reqId)) {
            reqId = UUID.randomUUID().toString().replaceAll("-","");
        }
        ThreadContext.put("req.id", reqId);
        chain.doFilter(request, response);
    }


}
