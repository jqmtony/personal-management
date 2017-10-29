package cn.xt.base.base.web.lib.data;

import cn.xt.base.base.model.NameCodeEnum;

/**
 * Created by xiangtao on 2017/8/10.
 */
public enum State implements NameCodeEnum {
    normaldata(1,"正常"),
    tabledamage(2,"表损坏"),
    tablenotexists(3,"表不存在"),
    dbnotexists(4,"库不存在");

    private final int code;
    private final String name;

    State(int code, String name){
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


    public static State valueOf(int code){
        for(State insertType: State.values()){
            if(insertType.code==code){
                return insertType;
            }
        }
        return null;
    }
}
