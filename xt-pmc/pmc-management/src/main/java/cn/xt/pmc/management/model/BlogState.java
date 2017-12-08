package cn.xt.pmc.management.model;

import cn.xt.base.model.NameCodeEnum;

/**
 * Created by xiangtao on 2017/8/10.
 */
public enum BlogState implements NameCodeEnum {
    deleted(0,"删除"),
    normal(1,"正常");

    private final int code;
    private final String name;

    BlogState(int code, String name){
        this.code=code;
        this.name=name;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getName(){
        return name;
    }


    public static BlogState valueOf(int code){
        for(BlogState state: BlogState.values()){
            if(state.code==code){
                return state;
            }
        }
        return null;
    }
}
