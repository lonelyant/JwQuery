package com.ant.utils;


import com.ant.model.User;
import com.ant.service.LoginService;
import com.ant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: Ant
 * @Date: 2018/09/28 11:10
 * @Description:
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "LoginFilter")
public class LoginFilter implements Filter {


    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login") ||
                requestURI.contains("/askLogin") ||
                requestURI.contains("/aboutme") ||
                requestURI.contains("/js/") ||
                requestURI.contains("/img/") ||
                requestURI.contains("/css/") ||
                requestURI.contains("/fonts/")
                ) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!isLogin(request)) {
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public Boolean isLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) return true;

        //服务器没找到对应的会话记录，如果有cookie，则解析cookie判断是否登录
        Cookie[] cookies = request.getCookies();
        String info = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "userinfo")) {
                    info = cookie.getValue();
                    System.out.println(info);
                }
            }
        }
        if (Objects.equals(info, "")) return false;
        //获取IP
        String ip = request.getRemoteAddr();

        //获取cookie中的值
        try {
            String[] split = DES.dcodes(info).split("\\(--\\)");
            if (split.length<3){
                return false;
            }else {
                String account = split[0];
                String password = split[1];
                String cookie_ip = split[2];
                if (Objects.equals(cookie_ip, ip)){
                    user = userService.findIfExist(account, password);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                        return true;
                    }
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
