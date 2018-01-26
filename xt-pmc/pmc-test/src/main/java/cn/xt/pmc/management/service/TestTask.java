package cn.xt.pmc.management.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestTask {

    @Scheduled(fixedRate = 1000)
    public void process(){
        System.out.println(new Date().toLocaleString());
    }
}
