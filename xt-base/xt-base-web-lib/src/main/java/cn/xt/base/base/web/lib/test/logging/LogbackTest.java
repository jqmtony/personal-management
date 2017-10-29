package cn.xt.base.base.web.lib.test.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LogbackTest {
    public static void main(String[] args) {
        log();
    }
    public static void log() {
        //根据实际jar包决定用那种日志实现打印
        Logger log = LoggerFactory.getLogger(LogbackTest.class);
        log.error(UUID.randomUUID().toString());
    }
}
