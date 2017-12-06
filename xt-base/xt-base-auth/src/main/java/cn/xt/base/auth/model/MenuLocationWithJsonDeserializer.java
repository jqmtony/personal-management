package cn.xt.base.auth.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * 枚举字符串转对象的自定义类
 */
public class MenuLocationWithJsonDeserializer extends JsonDeserializer<MenuLocation> {
    @Override
    public MenuLocation deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int code = node.get("code").intValue();
        return MenuLocation.valueOf(code);
    }
}
