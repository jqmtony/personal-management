package cn.xt.base.validate.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//自定义验证器
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public void initialize(Mobile constraintAnnotation) {
        //这里可以获取Mobile注解元数据
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regx = "1\\d{10}";
        Pattern p = Pattern.compile(regx);
        Matcher matcher = p.matcher(value);
        return matcher.find();
    }
}
