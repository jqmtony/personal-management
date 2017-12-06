package cn.xt.base.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * json操作类
 *
 * @author fengwx
 */
public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper JSON_MAPPER;

    static {
        JSON_MAPPER = new ObjectMapper();
        JSON_MAPPER.setSerializationInclusion(Include.NON_NULL);
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object obj) {
        try {
            return JSON_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("error on to json", e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> cla) {
        try {
            return JSON_MAPPER.readValue(json, cla);
        } catch (Exception e) {
            LOG.error("error on fromJson", e);
        }
        return null;
    }

    public static <T> List<T> fromJsonList(String json, Class<T> cla) {
        try {
            if (!json.startsWith("[")) {
                json = "[" + json + "]";
            }
            JavaType javaType = getCollectionType(ArrayList.class, cla);
            return JSON_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("error on fromJsonList", e);
        }
        return null;
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return JSON_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
