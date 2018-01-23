package cn.xt.pmc.management.controller;

import cn.xt.base.util.StringUtil;
import cn.xt.pmc.management.model.RobotMessage;
import cn.xt.pmc.management.service.RobotService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * create by xt
 */
@RequestMapping("robot")
@Controller
public class RobotController {
    @Resource
    private RobotService robotService;

    @RequestMapping(value = "chat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RobotMessage chat(String text) throws IOException {
        RobotMessage message = null;
        if (StringUtil.isNotEmpty(text)) {
            message = robotService.getAskText(text);
            message.setQuestion(text);
        } else {
            //自动生成文本
            String autoAsk = "你好啊";
            message = new RobotMessage();
            message.setText(autoAsk);
        }
        return message;
    }
}
