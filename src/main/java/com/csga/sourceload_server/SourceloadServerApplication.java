package com.csga.sourceload_server;

import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SourceloadServerApplication implements ApplicationListener<ContextRefreshedEvent> {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(SourceloadServerApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
        System.out.println( "启动成功" );

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        SpringContextUtil.setApplicationContext(event.getApplicationContext());

    }
}
