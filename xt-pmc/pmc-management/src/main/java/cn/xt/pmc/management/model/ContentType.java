package cn.xt.pmc.management.model;

import cn.xt.base.model.NameCodeEnum;

/**
 * create by xtao
 * create in 2017/11/19 19:14
 */
public enum ContentType implements NameCodeEnum{

    markdown(1,"MarkDown");

    private int code;
    private String name;
    private ContentType(int code,String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public ContentType valueOf(int code){
        for(ContentType contentType : ContentType.values()){
            if(contentType.code==code){
                return contentType;
            }
        }
        return null;
    }
}
