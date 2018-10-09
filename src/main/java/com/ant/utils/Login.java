package com.ant.utils;

import com.ant.model.LoginModel;
import com.ant.model.User;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @author: Ant
 * @Date: 2018/09/07 09:09
 * @Description:
 */
public class Login {

    private static Properties properties = PropertiesUtil.argumentConfigParse();

    public static LoginModel login(User user) throws IOException {
        LoginModel loginModel = new LoginModel();
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        user.setCookieStore(cookieStore);
        while (true) {
            //验证码保存路径
            //String codeSavePath = new ClassPathResource("codeImage/"+ UUID.randomUUID().toString().replaceAll("-", "") + ".gif").getPath();
            String codeSavePath = properties.getProperty("BasePath")+properties.getProperty("codeImagePath") + UUID.randomUUID().toString().replaceAll("-", "") + ".gif";
//            String codeSavePath = "/root/Ant/JW/codeImage/" + UUID.randomUUID().toString().replaceAll("-", "") + ".gif";
            System.out.println(" [ INFO ] 请求验证码...");
            String vcode = new VCodeToText().getCodeText(codeSavePath, httpClient);
            String loginhtml = null;
            try {
                loginhtml = HttpUtils.getHtml(httpClient, "http://221.232.159.27/default2.aspx", null);

                System.out.println(" [ INFO ] 获取__VIEWSTATE...");
                String __VIEWSTATE = Jsoup.parse(loginhtml).select("input[name=__VIEWSTATE]").attr("value");

                //构造登录参数map
                Map<String, String> params = new HashMap<String, String>();
                params.put("__VIEWSTATE", __VIEWSTATE);
                params.put("txtUserName", user.getAccount());
                params.put("Textbox1", "");
                params.put("Textbox2", user.getPassword());
                params.put("txtSecretCode", vcode);
                params.put("RadioButtonList1", "%D1%A7%C9%FA");
                params.put("Button1", "");
                params.put("lbLanguage", "");
                params.put("hidPdrs", "");
                params.put("hidsc", "");

                CloseableHttpResponse httpResponse = HttpUtils.post(httpClient, params, "http://221.232.159.27/default2.aspx", null);
                if (httpResponse.getStatusLine().getStatusCode() == 302) {
                    String indexhtml = HttpUtils.getHtml(httpClient, "http://221.232.159.27/xs_main.aspx?xh=" + user.getAccount(), null);
                    String name = Jsoup.parse(indexhtml).getElementById("xhxm").ownText().replace("同学", "");
                    user.setRealname(name);
                    loginModel.setStatus(true);
                    loginModel.setInfo("登录成功！");
                    break;
                } else if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    String failHtml = HttpUtils.getHtmlFromResponse(httpResponse);
                    String text = Jsoup.parse(failHtml).select("script").get(1).data();
                    String substring = text.substring(text.indexOf("'") + 1, text.indexOf(")") - 1);
                    int errorNum = 0;
                    if (substring.equals("验证码不正确！！")) {
                        System.out.println(" [INFO] 验证码识别失败，自动进行下一次登录...---------------------------");
                        errorNum++;
                        if (errorNum >5){
                            loginModel.setStatus(false);
                            loginModel.setInfo("验证码自动识别出错次数达上限");
                            break;
                        }
                    } else {
                        loginModel.setStatus(false);
                        loginModel.setInfo(substring);
                        break;
                    }
                }
            } catch (Exception e) {
                loginModel.setStatus(false);
                loginModel.setInfo("登录时出错，我猜学校教务系统服务器可能炸了 Σ(⊙▽⊙");
            }
        }
        httpClient.close();
        return loginModel;
    }

}
