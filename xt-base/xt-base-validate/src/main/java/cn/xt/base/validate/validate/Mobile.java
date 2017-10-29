package cn.xt.base.validate.validate;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
// 可以编写自己的验证规则
//@Constraint(validatedBy = MobileValidator.class)
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
//如果@Mobile在多个字段上使用，且验证失败多次，只显示一次错误信息
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface Mobile {
    String message() default "需要匹配正则表达式：{regexp}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    //覆盖Pattern注解的regexp属性，需要在Mobile上添加Pattern注解。
    @OverridesAttribute(constraint = Pattern.class, name = "regexp")
    String regexp() default "\\d+";

    @OverridesAttribute(constraint = Pattern.class, name = "flags")
    Pattern.Flag[] flags() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Mobile[] value();
    }
}
