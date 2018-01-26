package cn.xt.test;

import cn.xt.base.config.AppConfig;
import cn.xt.pmc.management.service.TestTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AppConfig.class})
public class ScheduleTest {
    @Resource
    private TestTask testTask;

    @Test
    public void schedule() throws Exception {
        System.out.println(111);
        System.out.println(testTask);
        while (true){}
    }
}
