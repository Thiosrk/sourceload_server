package com.csga.sourceload_server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Utils.Data.DBUtils;
import com.csga.sourceload_server.Utils.Data.ModelGenerator;
import com.csga.sourceload_server.Utils.DataBase.DataSourceManager;
import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Autowired
    ModelGenerator modelGenerator;

    private QueryRunner queryRunner;

    @Autowired
    TaskInfoService service;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        String info = "信息";
        logger.info("info：{}",info);

//        AsyncTaskManager.INSTANCE.initTaskContainer();
//        DataSourceManager.INSTANCE.init();

//        service.addOrUpdateTaskInfo(TaskInfo.builder()
//                .tableSequence(1)
//                .startPage(1)
//                .pageSize(1000)
//                .createTime(new Date())
//                .taskState(TaskState.Start)
//                .build());

//        testExcleAndInsert();

//        for (int i = 0; i < 10 ; i++) {
//            Thread.sleep(1000);
//            service.addTaskInfo(
//                    TaskInfo.builder()
//                            .createTime(new Date())
//                            .pageSize(1000)
//                            .startPage(1)
//                            .tableSequence(1)
//                            .build());
//        }

    }

    public void testExcleAndInsert() throws Exception {

        DataSourceManager.INSTANCE.setCurrentDataSource("OracleInputSource");
        /**
         * 根据excel生成model类
         */
        File file =new File("src/main/resources/templates/modelTemplate.xlsx");
        modelGenerator.generateByExcel(file);

        /**
         * 装载model类，注入bean
         */
        Class modelclz = Class.forName("com.csga.sourceload_server.Model.STD_GOD_SZYQGL_TB_DOG");
        String methodName = "setU_id";
        Method method = modelclz.getDeclaredMethod(methodName,Integer.class);

        //获取context
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder =BeanDefinitionBuilder.genericBeanDefinition(modelclz);
        //动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("STD_GOD_SZYQGL_TB_DOG",beanDefinitionBuilder.getBeanDefinition());
        /**
         * 存储数据部分测试
         */
        String text = "{\"result\":\"true\",\"data\":[{\"flag\":\"1\",\"qzsjjzdz\":\"7\",\"wjzmp\":\"3\",\"qzzlhfx\":" +
                "\"7\",\"dwsh\":\"3\",\"wjqzzlhfx\":\"7\",\"owner_flag\":\"1\",\"wjqzsh\":\"3\",\"oid\":94469," +
                "\"qzzlhhm\":\"60\",\"qzsh\":\"1\",\"qzhjxz\":\"xasidaihd\",\"odate\":1414633549000," +
                "\"dwmp\":\"3\",\"qzzlh\":\"1\",\"etl_dt\":\"2019-07-25\",\"qzfh\":\"3\",\"qzshm\":\"104\"," +
                "\"wjqzzlh\":\"7\",\"wjqzfh\":\"3\",\"qzxb\":\"2\",\"qzpcs\":\"320586431\"," +
                "\"qzsfzh\":\"320223197610021127\",\"qzxm\":\"dfsdf\",\"qzmp\":\"3\",\"qzhjdz\":\"320500\"," +
                "\"dwfh\":\"3\",\"dwzlh\":\"7\",\"qzyddh\":\"13771886601\",\"dwzlhfx\":\"7\",\"wjqzxb\":\"1\"," +
                "\"etl_job\":\"ODS_SZYQGL_TB_DOG_OWNER\",\"qzsjjzxz1\":\"32050003991\"}],\"message\":\"���óɹ�\"," +
                "\"md5\":\"abd6eca2b1104af317eb4dd9414ad062\"}";
        JSONArray jsonArray = JSONObject.parseObject(text).getJSONArray("data");

        DataSource dataSource = DataSourceManager.INSTANCE.getCurrentDataSource();

        queryRunner = new QueryRunner(dataSource);

        for (Object object : jsonArray) {
            JSONObject jsonObjectone = (JSONObject) object;
            String jsonstr = jsonObjectone.toJSONString();
            Object m  =JSONObject.parseObject(jsonstr,modelclz);
            Object m1  =JSONObject.parseObject(jsonstr,modelclz);
            method.invoke(m,2);
            method.invoke(m1,3);
            List<Object> list = new ArrayList<>();
            list.add(m);
            list.add(m1);
            DBUtils.insertBatchSelective("STD_GOD_SZYQGL_TB_DOG",list,queryRunner);
            System.out.println("insert complete!");
//            insert("STD_GOD_SZYQGL_TB_DOG",m);
        }
    }

//    private Map<String, Object> objectToMap(Object bean) throws Exception {
//        if(bean == null){
//            return null;
//        }else{
//            Field[] allFields = FieldUtils.getAllFields(bean.getClass());
//            Map<String, Object> map = new HashMap<>(allFields.length);
//
//            for (Field field : allFields) {
//                field.setAccessible(true);
//                map.put(field.getName(), field.get(bean));
//            }
//
//            return map;
//        }
//    }
//
//    public <K> Integer insert(String tableName, K k){
//
//        try {
//            Map<String, Object> params = objectToMap(k);
//            //参数
//            List<String> keysList = new ArrayList<>();
//            //问号
//            List<String> markList = new ArrayList<>(params != null ? params.size() : 1);
//            //参数值
//            List<Object> valuesList = new ArrayList<>();
//
//            if(params != null && params.size() > 0){
//                //拼接插入参数
//                for(Map.Entry<String, Object> entry : params.entrySet()){
//                    keysList.add(entry.getKey());
//                    valuesList.add(entry.getValue());
//                    markList.add("?");
//                }
//            }
//            //组装SQL
//            String sql = MessageFormat.format(JDBCConstants.BASE_INSERT, tableName
//                    , StringUtils.join(keysList, ","), StringUtils.join(markList, ","));
//
//            return queryRunner.update(sql, valuesList.toArray());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return 0;
//        }
//    }
//
//    public <K> int[] insertBatchSelective(String tableName, List<K> list){
//
//        try {
//            if(list != null && list.size() > 0){
//                //所有参数值
//                Object[][] params = new Object[list.size()][];
//                //参数
//                List<String> keysList = new ArrayList<>();
//                //问号
//                List<String> markList = new ArrayList<>();
//
//                for(int i = 0; i < list.size(); i++){
//                    K k = list.get(i);
//                    Map<String, Object> elementParams = objectToMap(k);
//                    //参数值
//                    List<Object> valuesList = new ArrayList<>();
//
//                    if(elementParams != null && elementParams.size() > 0){
//                        //拼接插入参数
//                        for(Map.Entry<String, Object> entry : elementParams.entrySet()){
//                            if(entry.getValue() != null){
//                                //只有第一次才设置参数
//                                if(i == 0){
////                                    keysList.add(com.ccx.ccxcommon.utils.StringUtils.humpToUnderline(entry.getKey()));
//                                    keysList.add(entry.getKey());
//                                    markList.add("?");
//                                }
//                                valuesList.add(entry.getValue());
//                            }
//                        }
//
//                        if(valuesList.size() > 0){
//                            params[i] = valuesList.toArray();
//                        }
//                    }
//                }
//
//                //组装SQL
//                String sql = MessageFormat.format(JDBCConstants.BASE_INSERT, tableName
//                        , StringUtils.join(keysList, ","), StringUtils.join(markList, ","));
//                return queryRunner.batch(sql, params);
//            }
//        } catch (Exception e) {
////            logger.error(e.getMessage());
//        }
//        return new int[]{0};
//    }






}
