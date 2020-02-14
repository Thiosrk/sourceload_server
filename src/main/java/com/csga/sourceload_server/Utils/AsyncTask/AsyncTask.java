package com.csga.sourceload_server.Utils.AsyncTask;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Service.Impl.TaskServiceImpl;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Utils.Data.DBUtils;
import com.csga.sourceload_server.Utils.Data.DataDownloadUtils;
import com.csga.sourceload_server.Utils.Data.DateUtils;
import com.csga.sourceload_server.Utils.Data.JsonUtil;
import com.csga.sourceload_server.Utils.DataBase.DataSourceManager;
import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Component
public class AsyncTask implements AsyncTaskConstructor{

    TaskInfoService service;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public void async(Integer taskInfoId) {
        this.service = (TaskInfoService) SpringContextUtil.getBean(TaskInfoService.class);

        logger.info("开始数据下载任务，任务编号："+taskInfoId);
        TaskInfo taskInfo = AsyncTaskManager.INSTANCE.getTaskInfo(taskInfoId);
        logger.info("任务数据源："+taskInfo.getDataSource());
        QueryRunner queryRunner = null;

        String postUrl = taskInfo.getRequestUrl();
        String systemId = taskInfo.getSystemId();
        Integer startPage = taskInfo.getStartPage();
        Integer pageSize = taskInfo.getPageSize();
//        Integer sequence = taskInfo.getTableSequence();
        String taskType = taskInfo.getTaskType().getName();
        String tableName = taskInfo.getTableName();
        Integer totalInLocal = taskInfo.getTotalInLocal();
        String timestampField = taskInfo.getTimestampField();
        Date updateTimestampValue = taskInfo.getUpdateTimestampValue();

        String result;
        List<Object> objectList;
        Integer totalInsert = 0;

        logger.info("任务数据表："+tableName);

        Class modelclz = null;
        Method method = null;

        logger.info("开始加载实体类：");

        try {
            modelclz = Class.forName("com.csga.sourceload_server.Model."+tableName);
//            String methodName = "setU_id";
//            method = modelclz.getDeclaredMethod(methodName,Integer.class);
        } catch (ClassNotFoundException e) {
            logger.info("加载失败，无对应实体类！");
            e.printStackTrace();
        }
        logger.info("加载完毕");

        Integer count;
        try{
            logger.info("查询数据总量：");
            //获取数据总量
            count = DataDownloadUtils.count(tableName,systemId,postUrl,"");
            taskInfo.setTotalFromSource(count);
            logger.info("请求接口数据总量："+count);
            Integer page = DataDownloadUtils.getMaxPage(count,pageSize);

            logger.info("开始抽取:");
            Integer pageNo = startPage;

            if(null==timestampField){
                logger.info("数据表无时间字段，全量更新: ");
                for (;pageNo < page+startPage;pageNo++){
                    if (service.getTaskState(taskInfoId).equals(TaskState.Stop)){
                        throw new InterruptedException();
                    }
                    result = DataDownloadUtils.loaddata(tableName,
                            systemId,
                            "",
                            "*",
                            "",
                            pageNo.toString(),
                            pageSize.toString(),
                            postUrl);
                    objectList = JsonUtil.toObjects(result, "data",modelclz);
                    for (Object object: objectList) {
//                        try {
//                            method.invoke(object,sequence++);
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
                        logger.info("数据："+object.toString());
                    }
                    totalInsert += objectList.size();
                    DataSourceManager.INSTANCE.setCurrentDataSource(taskInfo.getDataSource());
                    queryRunner= new QueryRunner(DataSourceManager.INSTANCE.getCurrentDataSource());
                    DBUtils.insertBatchSelective(tableName,objectList,queryRunner);
                    logger.info("第" + pageNo + "页数据插入完成，插入数据" + objectList.size() + "条");
                    objectList.clear();
                }
            }else {
                logger.info("同步时间字段："+timestampField);
                if (updateTimestampValue == null){
                    logger.info("上次更新时间为空，第一次更新：");
                    if (page>3000){
                        page = 3000;
                    }
                    for (;pageNo < page+startPage;pageNo++){
                        if (service.getTaskState(taskInfoId).equals(TaskState.Stop)){
                            throw new InterruptedException();
                        }
                        result = DataDownloadUtils.loaddata(tableName,
                                systemId,
                                "",
                                "*",
                                timestampField,
                                pageNo.toString(),
                                pageSize.toString(),
                                postUrl);
                        objectList = JsonUtil.toObjects(result, "data",modelclz);
                        for (Object object: objectList) {
//                            try {
//                                method.invoke(object,sequence++);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
                            logger.info("数据："+object.toString());
                        }
                        totalInsert += objectList.size();
//            DataDownloadUtils.insert(tableName,objectList.get(0),queryRunner);
                        DataSourceManager.INSTANCE.setCurrentDataSource(taskInfo.getDataSource());
                        queryRunner= new QueryRunner(DataSourceManager.INSTANCE.getCurrentDataSource());
                        DBUtils.insertBatchSelective(tableName,objectList,queryRunner);
                        logger.info("第" + pageNo + "页数据插入完成，插入数据" + objectList.size() + "条");
                        objectList.clear();
                    }
                }else {
                    logger.info("上次更新时间为："+ DateUtils.formatDate(updateTimestampValue));
                    String condition = "{\""+timestampField+"\":\">="+DateUtils.formatDate(updateTimestampValue)+"\"}";
                    Integer updateCount = DataDownloadUtils.count(
                            tableName,
                            systemId,
                            postUrl,
                            condition);
                    Integer updatePage = DataDownloadUtils.getMaxPage(updateCount,pageSize);
                    if (updatePage>3000){
                        updatePage = 3000;
                    }
                    for (;pageNo < updatePage+startPage;pageNo++){
                        if (service.getTaskState(taskInfoId).equals(TaskState.Stop)){
                            throw new InterruptedException();
                        }
                        result = DataDownloadUtils.loaddata(tableName,
                                systemId,
                                condition,
                                "*",
                                timestampField,
                                pageNo.toString(),
                                pageSize.toString(),
                                postUrl);
                        objectList = JsonUtil.toObjects(result, "data",modelclz);
                        for (Object object: objectList) {
//                            try {
//                                method.invoke(object,sequence++);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
                            logger.info("数据："+object.toString());
                        }
                        totalInsert += objectList.size();
                        DataSourceManager.INSTANCE.setCurrentDataSource(taskInfo.getDataSource());
                        queryRunner= new QueryRunner(DataSourceManager.INSTANCE.getCurrentDataSource());
                        DBUtils.insertBatchSelective(tableName,objectList,queryRunner);
                        logger.info("第" + pageNo + "页数据插入完成，插入数据" + objectList.size() + "条");
                        objectList.clear();
                    }

                }
                taskInfo.setUpdateTimestampValue(DBUtils.getLastTime(tableName,timestampField,queryRunner));
            }

            DataSourceManager.INSTANCE.clearDataSource();
            logger.info("表\""+tableName+"\"本次抽取数据完成，调用接口次数："+pageNo+",请求接口数据总量："+count+",插入数据总量："+totalInsert);
            totalInLocal +=totalInsert;
            taskInfo.setTotalInLocal(totalInLocal);
//            taskInfo.setTableSequence(sequence);
            taskInfo.setTaskState(TaskState.Finish);
            taskInfo.setStartPage(pageNo);
            taskInfo.setUpdateTime(new Date());

            this.service = (TaskInfoService) SpringContextUtil.getBean(TaskInfoService.class);

            service.addOrUpdateTaskInfo(taskInfo);


        } catch (InterruptedException e){
            logger.info("任务：{ "+tableName+" }已手动停止！");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            logger.info("接口调用有误,数据表无法访问，请测试接口可用性");
        }



    }

}
