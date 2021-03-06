package cn.xt.pmc.management.service;

import cn.xt.base.model.Constant;
import cn.xt.base.util.HttpClientUtil;
import cn.xt.base.util.JsonUtils;
import cn.xt.base.util.StringUtil;
import cn.xt.pmc.management.model.RobotMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * create by xt
 */
@Service
public class RobotServiceImpl implements RobotService {

    private static final String APIKEY = "e7a1447ed2182d57758ca845e5a0f36e";
    private static final String CHAT_TEXT_PLACEHOLDER = "{text}";
    private static final String ROBOT_CHAT_URL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info="+CHAT_TEXT_PLACEHOLDER;

    @Override
    public RobotMessage getAskText(String questionText) throws IOException {
        if(StringUtil.isNotEmpty(questionText)){
            String url = ROBOT_CHAT_URL.replace(CHAT_TEXT_PLACEHOLDER,URLEncoder.encode(questionText, Constant.UTF8));
            String ask = HttpClientUtil.get(HttpClientUtil.defaults(),url,null);
            if(StringUtil.isNotEmpty(ask)){
                RobotMessage message = JsonUtils.fromJson(ask,RobotMessage.class);;
                return message;
            }
        }
        return new RobotMessage(-1);
    }
}
