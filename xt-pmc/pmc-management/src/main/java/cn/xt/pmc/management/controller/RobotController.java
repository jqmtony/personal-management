package cn.xt.pmc.management.controller;

import cn.xt.pmc.management.service.RobotService;
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

    @RequestMapping(value = "chat", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String chat(String text) throws IOException {
        return robotService.getAskText(text);
    }
}
