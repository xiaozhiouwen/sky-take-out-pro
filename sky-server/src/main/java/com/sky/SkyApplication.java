package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableCaching//开发缓存注解功能
@EnableScheduling //开启任务调度
public class SkyApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SkyApplication.class, args);
        log.info("server started");
        
        // 初始化店铺状态为营业中 (1)
        try {
            StringRedisTemplate redisTemplate = context.getBean(StringRedisTemplate.class);
            Object status = redisTemplate.opsForValue().get("SHOP_STATUS");
            if (status == null) {
                redisTemplate.opsForValue().set("SHOP_STATUS", "1");
                log.info("初始化店铺状态为：营业中");
            } else {
                log.info("店铺当前状态为：{}", status);
            }
        } catch (Exception e) {
            log.error("初始化店铺状态失败：{}", e.getMessage());
        }
    }
}
