package com.ant.controller;

import com.ant.model.User;
import com.ant.utils.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Properties;

/**
 * @author: Ant
 * @Date: 2018/10/09 16:54
 * @Description:
 */
@Controller
public class JiangXJController {
    private static Properties properties = PropertiesUtil.argumentConfigParse();

    @RequestMapping("/checkpdf")
    public @ResponseBody Boolean check(HttpSession session){
        String account = ((User)session.getAttribute("user")).getAccount();
        File f = new File(properties.getProperty("BasePath")+properties.getProperty("PDFPath")+account+".pdf");
        return f.exists();
    }
}
