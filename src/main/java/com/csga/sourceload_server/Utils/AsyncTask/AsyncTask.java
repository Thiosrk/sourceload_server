package com.csga.sourceload_server.Utils.AsyncTask;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Repository.TaskInfoRepository;
import com.csga.sourceload_server.Service.Impl.TaskServiceImpl;
import com.csga.sourceload_server.Utils.Data.DataDownloadUtils;
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

    TaskInfoRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public void async(Integer taskInfoId) throws Exception {

        logger.info("开始数据下载任务，任务编号："+taskInfoId);
        TaskInfo taskInfo = AsyncTaskManager.INSTANCE.getTaskInfo(taskInfoId);
        logger.info("任务数据源："+taskInfo.getDataSource());
        DataSourceManager.INSTANCE.setCurrentDataSource(taskInfo.getDataSource());

        QueryRunner queryRunner = new QueryRunner(DataSourceManager.INSTANCE.getCurrentDataSource());

        String postUrl = taskInfo.getRequestUrl();
        String systemId = taskInfo.getSystemId();
        Integer startPage = taskInfo.getStartPage();
        Integer pageSize = taskInfo.getPageSize();
        Integer sequence = taskInfo.getTableSequence();
        String taskType = taskInfo.getTaskType().getName();
        String tableName = taskInfo.getTableName();
        Integer totalInLocal = taskInfo.getTotalInLocal();

        String result;
        List<Object> objectList;
        Integer totalInsert = 0;

        logger.info("任务数据表："+tableName);

        Class modelclz = null;
        Method method = null;

        logger.info("开始加载实体类：");

        try {
            modelclz = Class.forName("com.csga.sourceload_server.Model."+tableName);
            String methodName = "setU_id";
            method = modelclz.getDeclaredMethod(methodName,Integer.class);
        } catch (ClassNotFoundException e) {
            logger.info("加载失败，无对应实体类！");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        logger.info("加载完毕");

//        logger.info("测试url地址：");
//        if (DataDownloadUtils.testUrl(postUrl)){
//            logger.info("连接成功");
//        }else {
//            logger.info("连接失败");
//        }

        logger.info("查询数据总量：");
        //获取数据总量
        Integer count = DataDownloadUtils.count(tableName,systemId,postUrl);
        taskInfo.setTotalFromSource(count);
        logger.info("请求接口数据总量："+count);
        Integer page = DataDownloadUtils.getMaxPage(count,pageSize);

        //
        if (page >= 3000){
            logger.info("数据总页数大于3000，剩余未抽取页数："+(page-3000)+",下次抽取起始页："+(startPage+3000));
            page = 3000;
        }else {
            logger.info("数据总页数小于3000，共抽取页数："+page);
        }

        logger.info("开始抽取:");
        logger.info("抽取起始页："+startPage);
        Integer pageNo = startPage;
        for (;pageNo < page+startPage;pageNo++){
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
                method.invoke(object,sequence++);
                logger.info("数据："+object.toString());
            }
            totalInsert += objectList.size();
//            DataDownloadUtils.insert(tableName,objectList.get(0),queryRunner);
            DataDownloadUtils.insertBatchSelective(tableName,objectList,queryRunner);
            logger.info("第" + pageNo + "页数据插入完成，插入数据" + objectList.size() + "条");
            objectList.clear();
        }
        DataSourceManager.INSTANCE.clearDataSource();
        logger.info("表\""+tableName+"\"本次抽取数据完成，调用接口次数："+pageNo+",请求接口数据总量："+count+",插入数据总量："+totalInsert);
        totalInLocal +=totalInsert;
        taskInfo.setTotalInLocal(totalInLocal);
        taskInfo.setTableSequence(sequence);
        taskInfo.setTaskState(TaskState.Finish);
        taskInfo.setStartPage(pageNo);
        taskInfo.setUpdateTime(new Date());

        this.repository = (TaskInfoRepository) SpringContextUtil.getBean(TaskInfoRepository.class);
        repository.saveAndFlush(taskInfo);

    }
}
