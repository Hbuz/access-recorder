package com.hbuz.accessrecord.config;

import com.hbuz.accessrecord.repository.AccessRecordStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class AccessRecordStoreConfig {

    @Bean
    public AccessRecordStore AccessRecordStore(){
        return new AccessRecordStore(new ArrayList<>());
    }
}
