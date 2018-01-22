package cn.xt.pmc.qwkxq.controller;

import cn.xt.pmc.qwkxq.service.RobotService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by xt
 */
@RequestMapping("robot")
@Controller
public class RobotController {
    @Resource
    private RobotService robotService;

    @RequestMapping(value = "chat",method = RequestMethod.POST)
    @ResponseBody
    public String chat(String text) throws IOException {
        return robotService.getAskText(text);
    }
}
