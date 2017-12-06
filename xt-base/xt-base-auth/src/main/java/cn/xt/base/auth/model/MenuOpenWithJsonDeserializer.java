package cn.xt.base.auth.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * 枚举字符串转对象的自定义类
 *
 * 比如redis中事先存入ShiroResource的json字符串缓存
 * {\"menuOpenWith\":{\"code\":1}}
 * 现在要将它映射到ShiroResource的menuOpenWith属性
 */
public class MenuOpenWithJsonDeserializer extends JsonDeserializer<MenuOpenWith> {
    @Override
    public MenuOpenWith deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int code = node.get("code").intValue();
        return MenuOpenWith.valueOf(code);
    }
}
