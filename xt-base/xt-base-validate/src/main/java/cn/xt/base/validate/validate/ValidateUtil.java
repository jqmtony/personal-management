/*
package cn.xt.base.validate.validate;

import cn.xt.base.validate.model.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidateUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void main(String[] args) {
        ValidBean vb = new ValidBean();
        vb.setDate(new Date());
        vb.setEmail("14356#qq.com");
        vb.setPhone("13558636632a@");
        vb.setId(1000L);

        GroupValidBean gvb = new GroupValidBean();
        gvb.setAddress("xXXXXXXXXXXXXx");
        gvb.setIp("111");
        gvb.setPassword(null);
        gvb.setUrl(null);
        vb.setGroupValidBean(gvb);

        Set<ConstraintViolation<ValidBean>> set = validator.validate(vb,Default.class,Tag2.class,Tag1.class);
        for(ConstraintViolation cv : set){
            System.out.println(cv.getPropertyPath()+":"+cv.getMessage());
        }

       */
/* Set<ConstraintViolation<GroupValidBean>> set2 = validator.validate(gvb,Tag2.class);
        for(ConstraintViolation cv : set2){
            System.out.println(cv.getMessage());
        }*//*

    }
}
*/
