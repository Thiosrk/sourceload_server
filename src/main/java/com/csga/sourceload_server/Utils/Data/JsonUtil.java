package com.csga.sourceload_server.Utils.Data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtil {

    public static List<Object> toObjects(String str, String key, Class clz){

        JSONArray jsonArray = JSONObject.parseObject(str).getJSONArray(key);
        List<Object> objectList= new ArrayList<>();
        System.out.println("data数据共："+jsonArray.size()+"条");

        //遍历JSONArray
        for (Object object : jsonArray) {
            JSONObject jsonObjectOne = (JSONObject) object;
            String jsonStr = jsonObjectOne.toJSONString();
            Object ob = JSONObject.parseObject(jsonStr,clz);
            objectList.add(ob);
        }

        return objectList;
    }

    public static Object toJson(String str,String key){

        JSONObject jsonObject = JSONObject.parseObject(str);

        JSONArray jsonArray = jsonObject.getJSONArray(key);

        return jsonArray;
    }


    public static String JsontoString(String str,String key){

        JSONObject jsonObject = JSONObject.parseObject(str);

        String jsonArray = jsonObject.getString(key);

        return jsonArray;
    }

    public static String strToJson(String str,String key){

        JSONObject jsonObject = JSONObject.parseObject(str);

//        JSONArray jsonArray = jsonObject.getJSONArray(key);

        return jsonObject.getString(key);
    }

    public static ArrayList<String> getKeys(String jsonstr,String key){

        JSONArray jsonArray = (JSONArray) toJson(jsonstr,key);

        ArrayList<String> keyList= new ArrayList<>();
        parseProperties(jsonArray,"",keyList);
        return keyList;
    }

    private static void parseProperties(Object json, String father, ArrayList<String> result){
        String className = json.getClass().getSimpleName();
        if (className.equals("JSONObject")) {
            JSONObject jsonObj = (JSONObject) json;
            Iterator<String> iter = jsonObj.keySet().iterator();
            while (iter.hasNext()) {
                String nextIndex = iter.next();
                String prefix = father == null || father == "" ? nextIndex : father + "." + nextIndex;
                Object sonJson = jsonObj.get(nextIndex);
                parseProperties(sonJson, prefix, result);
            }
        } else if (className.equals("JSONArray")) {
            JSONArray array = (JSONArray) json;
            Object sonJson = array.get(0);
            parseProperties(sonJson, father, result);
        } else {
            String prefix = father;
            if (null != result) {
                result.add(prefix);
            }
        }
    }




}
