package cn.xt.pmc.management.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * create by xt
 */
public class RobotMessage {
    private int code = Integer.MAX_VALUE;
    private String question;
    private String text;
    private String url;
    private List<Object> list;

    public RobotMessage(){}

    public RobotMessage(int code){this.code = code;}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Object> getList() {
        List<Object> newList = new LinkedList<>();
        Map<Integer,Boolean> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(list)){
            Collections.shuffle(list);
            for(int i=0; i<Math.min(list.size(),5); i++){
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
