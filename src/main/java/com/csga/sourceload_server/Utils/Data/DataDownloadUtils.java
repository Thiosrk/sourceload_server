package com.csga.sourceload_server.Utils.Data;

import com.alibaba.fastjson.JSONArray;
import com.csga.sourceload_server.Utils.DataBase.JDBCConstants;
import com.csga.sourceload_server.Utils.Http.HttpClientUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String result = HttpClientUtils.post(requestUrl, dataMap,"utf-8");

//        return result;

//        return "{\"result\":\"true\",\"data\":[{\"count\":\"11111\",\"qzsjjzdz\":\"7\",\"wjzmp\":\"3\",\"qzzlhfx\":" +
//                "\"7\",\"dwsh\":\"3\",\"wjqzzlhfx\":\"7\",\"owner_flag\":\"1\",\"wjqzsh\":\"3\",\"oid\":94469," +
//                "\"qzzlhhm\":\"60\",\"qzsh\":\"1\",\"qzhjxz\":\"xasidaihd\",\"odate\":1414633549000," +
//                "\"dwmp\":\"3\",\"qzzlh\":\"1\",\"etl_dt\":\"2019-07-25\",\"qzfh\":\"3\",\"qzshm\":\"104\"," +
//                "\"wjqzzlh\":\"7\",\"wjqzfh\":\"3\",\"qzxb\":\"2\",\"qzpcs\":\"320586431\"," +
//                "\"qzsfzh\":\"320223197610021127\",\"qzxm\":\"dfsdf\",\"qzmp\":\"3\",\"qzhjdz\":\"320500\"," +
//                "\"dwfh\":\"3\",\"dwzlh\":\"7\",\"qzyddh\":\"13771886601\",\"dwzlhfx\":\"7\",\"wjqzxb\":\"1\"," +
//                "\"etl_job\":\"ODS_SZYQGL_TB_DOG_OWNER\",\"qzsjjzxz1\":\"32050003991\"}],\"message\":\"���óɹ�\"," +
//                "\"md5\":\"abd6eca2b1104af317eb4dd9414ad062\"}";

        return "{\"result\":\"false\",\"data\":[{\"count\":\"11111\",\"qzsjjzdz\":\"7\",\"wjzmp\":\"3\",\"qzzlhfx\":" +
                "\"7\",\"dwsh\":\"3\",\"wjqzzlhfx\":\"7\",\"owner_flag\":\"1\",\"wjqzsh\":\"3\",\"oid\":94469," +
                "\"qzzlhhm\":\"60\",\"qzsh\":\"1\",\"qzhjxz\":\"xasidaihd\",\"odate\":1414633549000," +
                "\"dwmp\":\"3\",\"qzzlh\":\"1\",\"etl_dt\":\"2019-07-25\",\"qzfh\":\"3\",\"qzshm\":\"104\"," +
                "\"wjqzzlh\":\"7\",\"wjqzfh\":\"3\",\"qzxb\":\"2\",\"qzpcs\":\"320586431\"," +
                "\"qzsfzh\":\"320223197610021127\",\"qzxm\":\"dfsdf\",\"qzmp\":\"3\",\"qzhjdz\":\"320500\"," +
                "\"dwfh\":\"3\",\"dwzlh\":\"7\",\"qzyddh\":\"13771886601\",\"dwzlhfx\":\"7\",\"wjqzxb\":\"1\"," +
                "\"etl_job\":\"ODS_SZYQGL_TB_DOG_OWNER\",\"qzsjjzxz1\":\"32050003991\"}],\"message\":\"���óɹ�\"," +
                "\"md5\":\"abd6eca2b1104af317eb4dd9414ad062\"}";

    }

    public static Integer count(String serviceId,
                                String systemId,
                                String requestUrl){

        String result = loaddata(serviceId,systemId,"","count(1) over()","","1","1",requestUrl);

        JSONArray data = (JSONArray) JsonUtil.toJson(result,"data");

//        return data.getJSONObject(0).getInteger("count");
        return 1;
    }

    public static Integer getMaxPage(Integer count, Integer pageSize){

        return (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1) ;

    }

    public static <K> Integer insert(String tableName, K k, QueryRunner queryRunner){

        try {
            Map<String, Object> params = objectToMap(k);
            //参数
            List<String> keysList = new ArrayList<>();
            //问号
            List<String> markList = new ArrayList<>(params != null ? params.size() : 1);
            //参数值
            List<Object> valuesList = new ArrayList<>();

            if(params != null && params.size() > 0){
                //拼接插入参数
                for(Map.Entry<String, Object> entry : params.entrySet()){
                    keysList.add(entry.getKey());
                    valuesList.add(entry.getValue());
                    markList.add("?");
                }
            }
            //组装SQL
            String sql = MessageFormat.format(JDBCConstants.BASE_INSERT, tableName
                    , StringUtils.join(keysList, ","), StringUtils.join(markList, ","));

            return queryRunner.update(sql, valuesList.toArray());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static  <K> int[] insertBatchSelective(String tableName, List<K> list, QueryRunner queryRunner){

        try {
            if(list != null && list.size() > 0){
                //所有参数值
                Object[][] params = new Object[list.size()][];
                //参数
                List<String> keysList = new ArrayList<>();
                //问号
                List<String> markList = new ArrayList<>();

                for(int i = 0; i < list.size(); i++){
                    K k = list.get(i);
                    Map<String, Object> elementParams = objectToMap(k);
                    //参数值
                    List<Object> valuesList = new ArrayList<>();

                    if(elementParams != null && elementParams.size() > 0){
                        //拼接插入参数
                        for(Map.Entry<String, Object> entry : elementParams.entrySet()){
                            if(entry.getValue() != null){
                                //只有第一次才设置参数
                                if(i == 0){
//                                    keysList.add(com.ccx.ccxcommon.utils.StringUtils.humpToUnderline(entry.getKey()));
                                    keysList.add(entry.getKey());
                                    markList.add("?");
                                }
                                valuesList.add(entry.getValue());
                            }
                        }

                        if(valuesList.size() > 0){
                            params[i] = valuesList.toArray();
                        }
                    }
                }

                //组装SQL
                String sql = MessageFormat.format(JDBCConstants.BASE_INSERT, tableName
                        , StringUtils.join(keysList, ","), StringUtils.join(markList, ","));
                return queryRunner.batch(sql, params);
            }
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }
        return new int[]{0};
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

    private static Map<String, Object> objectToMap(Object bean) throws Exception {
        if(bean == null){
            return null;
        }else{
            Field[] allFields = FieldUtils.getAllFields(bean.getClass());
            Map<String, Object> map = new HashMap<>(allFields.length);

            for (Field field : allFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(bean));
            }

            return map;
        }
    }


//    public static void main(String[] args) {
//        String url = "http://50.73.64.174:3080/szgasmp/a/smp/receive/dataQuery";
//        String url2 = "http://50.73.64.174:3080";
//        String url1 = "https://www.baidu.com";
//        System.out.println(DataDownloadUtils.testUrl(url2));
//    }



}
