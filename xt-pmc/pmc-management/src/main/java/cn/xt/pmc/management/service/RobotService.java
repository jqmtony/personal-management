package cn.xt.pmc.management.service;

import cn.xt.pmc.management.model.RobotMessage;

import java.io.IOException;

public interface RobotService {
    /**
     * 根据请求文本获取回答文本
     * @param questionText
     * @return
     */
    RobotMessage getAskText(String questionText) throws IOException;
}
