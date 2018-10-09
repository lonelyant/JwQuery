package com.ant.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Ant
 * @Date: 2018/09/07 09:29
 * @Description:
 */
public class HttpUtils {

    public static String getHtml(CloseableHttpClient httpClient, String url, String referer) throws Exception {
        String htmlbody = "";
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("X-Requested-With", "XMLHttpRequest");
        httpget.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        if (referer != null) {
            httpget.setHeader("Referer", referer);
        }
        httpget.setConfig(RequestConfig.custom().setConnectTimeout(5 * 1000).setConnectionRequestTimeout(5 * 1000).setSocketTimeout(5 * 1000).build());
        CloseableHttpResponse response = null;

        response = httpClient.execute(httpget);
        int code = response.getStatusLine().getStatusCode();
        HttpEntity entity;
        if (code == 200 || code == 302) {
            entity = response.getEntity();
            htmlbody = EntityUtils.toString(entity, "gb2312");
            EntityUtils.consume(entity);
        }
        response.close();

        return htmlbody;
    }

    public static CloseableHttpResponse post(CloseableHttpClient httpClient, Map<String, String> params, String targetUrl, String referer) throws Exception {
        CloseableHttpResponse httpResponse = null;

        HttpPost post = new HttpPost();
        post.setURI(new URI(targetUrl));
        if (referer != null) {
            post.setHeader("Referer", referer);
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> elem : params.entrySet()) {
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "gb2312");
            post.setEntity(entity);
        }
        httpResponse = httpClient.execute(post);

        return httpResponse;
    }

    public static String getHtmlFromResponse(CloseableHttpResponse httpResponse) throws Exception {
        String htmlbody = "";

        HttpEntity entity = httpResponse.getEntity();
        htmlbody = EntityUtils.toString(entity, "gb2312");
        EntityUtils.consume(entity);

        return htmlbody;
    }
}
