package com.csga.sourceload_server.Utils.Data;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DataDownloadUtils {

    public static String loaddata(String serviceId,
                                  String systemId,
                                  String condition,
                                  String resultField,
                                  String orderBy,
                                  String pageNo,
                                  String pageSize,
                                  String requestUrl) {

        Map dataMap = new HashMap();
        dataMap.put("systemId", systemId);
        dataMap.put("serviceId", serviceId);
        dataMap.put("condition",condition );//"{\"FLAG\":\"3\"}"
        dataMap.put("resultField", resultField);
        dataMap.put("orderBy", orderBy);
        dataMap.put("pageNo", pageNo);
        dataMap.put("pageSize", pageSize);
        dataMap.put("md5Flag", "true");
//        String result = HttpClientUtils.post(requestUrl, dataMap,"utf-8");

//        return result;

        return "{\"result\":\"true\",\"data\":[{\"wbzsbm\":\"0000\",\"yonghumc\":\"0\",\"citgc_time\":\"2019-04-22 01:46:05\",\"xzqh\":\"\",\"jgmc\":\"\",\"macdz\":\"40-B0-76-48-46-C6\",\"fwdm\":\"32058310000146\",\"sxsj\":\"2019-04-22 01:40:39\",\"ipdz\":\"172.16.32.14\",\"fwqsl\":\"\",\"fwlx\":\"NETBAR\",\"xxsj\":\"\",\"weidu\":\"\",\"citgc_source\":\"\",\"swkh\":\"0000\",\"zslxdm\":\"990\",\"hhbm\":\"320583014640B0764846C620190422014028\",\"zjm\":\"CQ014\",\"gjdm\":\"CHN\",\"etl_dt\":\"\",\"etl_job\":\"\"}],\"message\":\"调用成功\"}";


    }

    public static Integer count(String serviceId,
                                String systemId,
                                String requestUrl,
                                String condition){

        String result = loaddata(serviceId,systemId,condition,"count(1) over()","","1","1",requestUrl);

        JSONArray data = (JSONArray) JsonUtil.toJson(result,"data");

//        return data.getJSONObject(0).getInteger("count");
        return 1;
    }

    public static Integer getMaxPage(Integer count, Integer pageSize){

        return (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1) ;

    }

    public static Boolean testUrl(String url_s){
        try{
            URL url = new URL(url_s);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            /**
             * public int getResponseCode()throws IOException
             * 从 HTTP 响应消息获取状态码。
             * 例如，就以下状态行来说：
             * HTTP/1.0 200 OK
             * HTTP/1.0 401 Unauthorized
             * 将分别返回 200 和 401。
             * 如果无法从响应中识别任何代码（即响应不是有效的 HTTP），则返回 -1。
             *
             * 返回 HTTP 状态码或 -1
             */
            int state = conn.getResponseCode();
            System.out.println(state);
            if(state == 200){
                return true;
            }
            else{
                return false;
            }
        }
        catch(IOException e){
            return false;
        }
    }



//    public static void main(String[] args) {
//        String url = "http://50.73.64.174:3080/szgasmp/a/smp/receive/dataQuery";
//        String url2 = "http://50.73.64.174:3080";
//        String url1 = "https://www.baidu.com";
//        System.out.println(DataDownloadUtils.testUrl(url2));
//    }



}
