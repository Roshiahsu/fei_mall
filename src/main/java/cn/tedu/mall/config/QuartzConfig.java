package cn.tedu.mall.config;

import cn.tedu.mall.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName QuartzConfig
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午6:53
 */
@Configuration
@Slf4j
public class QuartzConfig {

    //創建JobDetail對象
    //JobDetail負責封裝Job，也就是任務
    @Bean
    public JobDetail showTime(){
        log.debug("showTime方法運行");
        //綁定QuartzJob任務
        return JobBuilder.newJob(QuartzJob.class)
                //給當前的JobDetail取名字
                .withIdentity("redisUpdate")
                //沒有綁定觸發器，也不會被刪除
                .storeDurably()
                .build();
    }

    //觸發器
    @Bean
    public Trigger showTimeTrigger(){
        log.debug("showTimeTrigger方法運行");
        //cron表達式
        CronScheduleBuilder cronScheduleBuilder =
                //每30分鐘執行一次
                CronScheduleBuilder.cronSchedule("0 0/30 * * * ? ");
        return TriggerBuilder.newTrigger()
                .forJob(showTime())
                .withIdentity("redisUpdateTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
