package com.svj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Bean
    public Logger getLogger(){
        return LoggerFactory.getLogger(AppConfig.class);
    }
}
