package com.csga.sourceload_server.Service.Impl;

import com.csga.sourceload_server.Service.GenerateModelService;
import com.csga.sourceload_server.Utils.Data.ModelGenerator;
import com.csga.sourceload_server.Utils.Data.ModelGeneratorConstants;
import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GenerateModelServiceImpl implements GenerateModelService {

    private static final Logger logger = LoggerFactory.getLogger(GenerateModelService.class);

    String modelName;

    @Autowired
    ModelGenerator modelGenerator;

    @Override
    public Boolean genarateModel(File file) {
        try {

            modelGenerator.generateByExcel(file);
            modelName = modelGenerator.getModelName();

            //类加载
            Class modelclz = Class.forName(ModelGeneratorConstants.modelOutPath+"."+modelName);

            //获取context
            ApplicationContext context = SpringContextUtil.getApplicationContext();
            //获取BeanFactory
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
            //创建bean信息.
            BeanDefinitionBuilder beanDefinitionBuilder =BeanDefinitionBuilder.genericBeanDefinition(modelclz);
            //动态注册bean.
            defaultListableBeanFactory.registerBeanDefinition(modelName,beanDefinitionBuilder.getBeanDefinition());

            logger.info("实体类{}已注入Bean",modelName);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
