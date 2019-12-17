package com.csga.sourceload_server.Utils.Http;

import org.apache.commons.lang.ObjectUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

//import com.google.gson.Gson;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.ObjectUtilsls;
//import org.apache.log4j.Logger;


public class HttpClientUtils {
    //    private static Logger logger = org.apache.log4j.Logger.getLogger(HttpClientUtils.class.getSimpleName());
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";
    private final static String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static String get(String url, String charset) {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
//            logger.error(url, e);
        } finally {
//            IOUtils.closeQuietly(httpClient);
        }

        return result;
    }


    public static String get(String url, Map<String, String> headers, Map<String, String> data, String charset) {
        StringBuilder para = new StringBuilder();
        for (String item : data.keySet()) {
            para.append("&");
            para.append(item);
            para.append("=");
//            para.append(URLEncoder.encode(data.get(item)));
        }

        if (para.length() > 0) {
            url = url.indexOf("?") > 0 ? url + para : url + "?" + para.substring(1);
        }
        return get(url, headers, charset);
    }


    public static String get(String url, Map<String, String> headers, String charset) {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            if (headers != null) {
                for (String item : headers.keySet()) {
                    httpGet.addHeader(item, headers.get(item));
                }
            }
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
//            logger.error(url, e);
        } finally {
//            IOUtils.closeQuietly(httpClient);
        }
        return result;
    }


    public static String post(String url, Map map, String charset) {
        return request(url, "post", null, map, charset);
    }


    public static String post(String url, Map<String, String> header, Map map, String charset) {
        return request(url, "post", header, map, charset);
    }

    public static String put(String url, Map<String, String> header, Map map, String charset) {
        return request(url, "put", header, map, charset);
    }

    public static String request(String url, String method, Map<String, String> header, Map map, String charset) {
        CloseableHttpClient httpClient = null;
        httpClient = HttpClients.custom().build();

        HttpEntityEnclosingRequestBase hp = null;
        String result = null;
        if ("put".equals(method)) {
            hp = new HttpPut(url);
        } else {
            hp = new HttpPost(url);

        }
        if (header != null) {
            for (String item : header.keySet()) {
                hp.addHeader(item, header.get(item));
            }
        }

        try {
            if (map != null && map.size() > 0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Set keys = map.keySet();
                for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
                    String name = ObjectUtils.toString(iterator.next());
                    String value = ObjectUtils.toString(map.get(name));
                    params.add(new BasicNameValuePair(name, value));
                }
                hp.setEntity(new UrlEncodedFormEntity(params, charset));
            }

            HttpResponse response = httpClient.execute(hp);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
//            logger.error(url, e);
        } finally {
//            IOUtils.closeQuietly(httpClient);
        }
        return result;
    }

    public static String postContent(String url, Map map, String charset) {
        CloseableHttpClient httpclient = null;
        String ret = null;
        httpclient = HttpClients.custom().build();

        HttpPost httppost = new HttpPost(url);
        if (map != null && map.size() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Set keys = map.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
                String name = (String) iterator.next();
                String value = (String) map.get(name);
                params.add(new BasicNameValuePair(name, value));
            }
            try {
                httppost.setEntity(new UrlEncodedFormEntity(params, charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            CloseableHttpResponse response = null;
            response = httpclient.execute(httppost);
            if (null != response) {
                HttpEntity entity = null;
                try {
                    entity = response.getEntity();
                    if (entity != null) {
//                        ret = new String(IOUtils.toByteArray(entity.getContent()), getResCharset(response, charset));
//                        logger.debug("解析后的数据: " + ret);
                    }
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            }

        } catch (Exception e) {
//            logger.error(url, e);
        } finally {
//            IOUtils.closeQuietly(httpclient);
        }
        return ret;
    }

    public static String postContent(String url, String content, String charset) {
        String responseContent = "";
        CloseableHttpClient httpclient = HttpClients.custom().build();
        ;

        HttpPost httppost = new HttpPost(url);
        HttpEntity postEntity = new StringEntity(content, charset);
        httppost.setEntity(postEntity);
        System.out.println("executing request" + httppost.getRequestLine());
        try {

            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
            } catch (Exception e) {
                return "";
            }
            if (null != response) {
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        try {
//                            responseContent = new String(IOUtils.toByteArray(entity.getContent()), getResCharset(response, charset));
//                            logger.debug( responseContent);
                        } catch (Exception ee) {
//                            logger.error(ee.getMessage());
                        }
                    }
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            }

        } catch (Exception e) {
//            logger.debug(e);
        }
        return responseContent;
    }

    public static String getResCharset(CloseableHttpResponse response, String charset) {
        Header[] contentTypes = response.getHeaders("Content-Type");
        String resCharset = charset;
        int totalTypes = null != contentTypes ? contentTypes.length : 0;
        for (int i = 0; i < totalTypes; i++) {
            String[] value = contentTypes[i].getValue().toLowerCase().split(";");
            for (int j = 0; j < value.length; j++) {
                if (value[j].startsWith("charset=")) {
                    resCharset = value[j].substring("charset=".length());
                }
            }

        }
        return resCharset;
    }

    /**
     * 参数 json格式
     *
     * @param url
     * @param param
     * @return
     */
    public static String postJsonRequest(String url, Map<String, Object> param) {

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
//                Gson gson = new Gson();
//                String parameter = gson.toJson(param);
//                StringEntity se = new StringEntity(parameter);
//                se.setContentType(CONTENT_TYPE_TEXT_JSON);
//                httpPost.setEntity(se);
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
