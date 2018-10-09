package com.ant.controller;

import com.ant.model.User;
import com.ant.model.LoginModel;
import com.ant.service.LoginService;
import com.ant.utils.DES;
import com.ant.utils.Login;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Ant
 * @Date: 2018/09/08 17:06
 * @Description:
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/askLogin")
    public @ResponseBody
    LoginModel login(User user, @Param("isSavePwd") Boolean isSavePwd, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //获取IP
        String ip = request.getRemoteAddr();


        LoginModel loginModel = loginService.login(user, isSavePwd);
        if (loginModel.getStatus()) {
            String info = user.getAccount() + "(--)" + user.getPassword() + "(--)" + ip;
            Cookie cookie = new Cookie("userinfo", DES.ecodes(info));
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            session.setAttribute("user", loginModel.getUser());
        }
        return loginModel;
    }

    @RequestMapping("/navigate")
    public String toNavigate() {
        return "navigate";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("user");
        session.invalidate();
        response.addCookie(new Cookie("userinfo",null));
        return "redirect:login";
    }

    @RequestMapping("/login")
    public String getindex() {
        return "login";
    }

    @RequestMapping("/aboutme")
    public String aboutme() {
        return "aboutme";
    }

    @Test
    public void test() throws IOException {
        User user = new User();
        user.setAccount("2014040191031");
        user.setPassword("z78l10y125");
        LoginModel loginModel = Login.login(user);
        System.out.println(user);
        System.out.println(loginModel);
    }
}
