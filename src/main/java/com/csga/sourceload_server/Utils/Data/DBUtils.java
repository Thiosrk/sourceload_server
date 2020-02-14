package com.csga.sourceload_server.Utils.Data;

import com.csga.sourceload_server.Utils.DataBase.JDBCConstants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.sql.Timestamp;


public class DBUtils {

    public static void createTable(String sql,QueryRunner queryRunner){

        try {
            queryRunner.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Date getLastTime(String tableName, String timeField, QueryRunner queryRunner){
        try {
            String sql = "select max("+timeField+") from "+tableName;
            return queryRunner.query(sql,new ScalarHandler<>());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

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
//                    System.out.println(k.toString());
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
                                System.out.println(entry.getValue());
                                System.out.println(entry.getKey());
                                if (entry.getValue() instanceof Date){
                                    valuesList.add(new Timestamp(((Date) entry.getValue()).getTime()));
                                }else {
                                    valuesList.add(entry.getValue());
                                }
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
            e.printStackTrace();
        }
        return new int[]{0};
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


}
