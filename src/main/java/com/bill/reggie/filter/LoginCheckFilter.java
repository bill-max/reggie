package com.bill.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.bill.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取uri
        String uri = request.getRequestURI();
        //放行url
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        Object employee = request.getSession().getAttribute("employee");
        //判断是否放行==> 满足放行url  || 已登录
        if (check(urls, uri) || request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
//            log.info("放行url:" + uri);
//            log.info((String) employee);
        } else {
            //未登录时，前端跳转到登录界面，后端回写数据
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//            log.info("filter url :" + uri);
//            log.info((String) employee);
        }
    }

    /**
     * 判断是否放行
     *
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            if(PATH_MATCHER.match(url, requestURI))
                return true;
        }
        return false;
    }
}
