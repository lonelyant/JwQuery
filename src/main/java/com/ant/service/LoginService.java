package com.ant.service;

import com.ant.dao.UserMapper;
import com.ant.model.LoginModel;
import com.ant.model.User;
import com.ant.utils.DES;
import com.ant.utils.HttpUtils;
import com.ant.utils.Login;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author: Ant
 * @Date: 2018/09/08 17:09
 * @Description:
 */
@Service("CJQueryService")
public class LoginService {
    @Autowired
    private UserMapper userMapper;

    public LoginModel login(User user,Boolean isSavePwd){
        LoginModel loginModel = new LoginModel();
        try {
            loginModel = Login.login(user);
            if(loginModel.getStatus()) {
                user.setPassword(DES.ecodes(user.getPassword()));
                getStudentInfo(user);

                if (!isSavePwd) {
                    //保存教务CookieStore
                    user.setCookieStore(null);
                }
                //保存或更新用户信息到数据库
                //System.out.println(user);
                saveOrUpdate(user);
            }
            loginModel.setUser(user);
        } catch (Exception e) {
            loginModel.setStatus(false);
            loginModel.setInfo("程序错误，请联系QQ:891575283\n"+e.getMessage());
        }
        return loginModel;
    }

    private void saveOrUpdate(User user){
        User user1 = userMapper.find(user.getAccount());
        if (user1!=null){
            userMapper.update(user);
        }else {
            userMapper.save(user);
        }
    }

    private void getStudentInfo(User user){
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(user.getCookieStore()).build();
        try {
            String url = "http://221.232.159.27/xsgrxx.aspx?xh="+user.getAccount()+"&xm="+ URLEncoder.encode(user.getRealname(), "gb2312")+"&gnmkdm=N121501";
            String studentInfo = HttpUtils.getHtml(httpClient, url, "http://221.232.159.27/xs_main.aspx?xh="+user.getAccount());
            //System.out.println(studentInfo);
            if (!Objects.equals(studentInfo, "") && studentInfo.length()>0) {
                Document doc = Jsoup.parse(studentInfo);

                user.setGrade(Integer.parseInt(doc.getElementById("lbl_dqszj").ownText().trim()));//年级
                user.setGender(doc.getElementById("lbl_xb").ownText().trim());//性别
                user.setMajor(doc.getElementById("lbl_zymc").ownText().trim());//专业
                user.setClassname(doc.getElementById("lbl_xzb").ownText().trim());//班级名
                user.setBirth(doc.getElementById("lbl_sfzh").ownText().trim().substring(6,14));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
