package com.ant.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

/**
 * @author: Ant
 * @Date: 2018/09/07 09:17
 * @Description:
 */
public class VCodeToText {

    /**
     * 保存验证码图片到本地
     * @param savePath
     * @param httpClient
     */
    public void save(String savePath, CloseableHttpClient httpClient) throws Exception{

            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI("http://221.232.159.27/CheckCode.aspx"));
            httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");

            CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                File f = new File(savePath);
                if (!f.exists()) f.createNewFile();
                OutputStream os = new FileOutputStream(f);
                int length = -1;
                byte[] bytes = new byte[1024];
                while ((length = is.read(bytes)) != -1) {
                    os.write(bytes, 0, length);
                }
                os.close();
                EntityUtils.consume(entity);
            }

    }

    /**
     * 请求验证码保存到本地，并进行识别
     * @param savePath
     * @param httpClient
     * @return 验证码识别结果
     */
    public  String getCodeText(String savePath, CloseableHttpClient httpClient){
        String vcode = "";
        try {
            save(savePath,httpClient);
            vcode = VCodeOCR.ocr(savePath);
            new File(savePath).deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vcode;
    }
}
