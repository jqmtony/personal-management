package cn.xt.base.validate.validate;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class BeanValidatorTest {

    Validator validator = null;

    @Before
    public void setUp() throws Exception {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void defaultTest() throws Exception {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        Date yesterday = c.getTime();
        c.add(Calendar.DATE, 2);
        Date tomorrow = c.getTime();

        DefaultValidBean bean = new DefaultValidBean();
        bean.setDecimal(1006.88);
        bean.setDecimalString("10088");
        bean.setFuturedate(yesterday);
        bean.setPastdate(tomorrow);
        bean.setRemark("中文。。");

        bean.setEmail("dsfds#qq.com");
        bean.setRange(666);
        bean.setLen("3432423432423");
        bean.setUrl("http://www.baidu.com:80");

        Set<ConstraintViolation<DefaultValidBean>> validateResults = validator.validate(bean);
        for(ConstraintViolation result : validateResults){
            String tip = result.getPropertyPath()+":"+result.getMessage();
            System.out.println(tip);
        }
    }

    @Test
    public void groupTest() throws Exception {
        GroupValidBean bean = new GroupValidBean();
        bean.setAnimalAge(1800);
        bean.setAnimalName("");
        bean.setPersonAge(180);
        bean.setPersonName("咚");

        Set<ConstraintViolation<GroupValidBean>> validateResults = validator.validate(bean,AnimalGroup.class,PersonGroup.class, Default.class);
        for(ConstraintViolation result : validateResults){
            String tip = result.getPropertyPath()+":"+result.getMessage();
            System.out.println(tip);
        }
    }

    @Test
    public void customerTest() throws Exception {
       CustomerValidBean bean = new CustomerValidBean();
       bean.setPhone("22323AAA");

        Set<ConstraintViolation<CustomerValidBean>> validateResults = validator.validate(bean,AnimalGroup.class,PersonGroup.class, Default.class);
        for(ConstraintViolation result : validateResults){
            String tip = result.getPropertyPath()+":"+result.getMessage();
            System.out.println(tip);
        }
    }
}
