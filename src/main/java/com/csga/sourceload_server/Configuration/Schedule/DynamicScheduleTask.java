//package com.csga.sourceload_server.Configuration.Schedule;
//
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//
//import java.util.concurrent.Executors;
//
//
//public class DynamicScheduleTask implements SchedulingConfigurer {
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
//        scheduledTaskRegistrar.addTriggerTask(
//                () -> System.out.println(" "),
//                triggerContext -> {
//                    String cron = "";
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
//    }
//}
