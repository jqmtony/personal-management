package cn.xt.pmc.management.service;

import java.io.IOException;

public interface RobotService {
    /**
     * 根据请求文本获取回答文本
     * @param questionText
     * @return
     */
    String getAskText(String questionText) throws IOException;
}
