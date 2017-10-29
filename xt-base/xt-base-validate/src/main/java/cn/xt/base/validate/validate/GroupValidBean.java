package cn.xt.base.validate.validate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.GroupSequence;

//分组验证bean
@GroupSequence({PersonGroup.class,AnimalGroup.class,GroupValidBean.class})
public class GroupValidBean {
    @Length(min = 5,max = 20,groups = PersonGroup.class)
    private String personName;
    @Range(min = 0,max = 100,groups = PersonGroup.class)
    private Integer personAge;
    @Length(min = 1,max = 200,groups = AnimalGroup.class)
    private String animalName;
    @Range(min = 0,max = 1000,groups = AnimalGroup.class)
    private Integer animalAge;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getPersonAge() {
        return personAge;
    }

    public void setPersonAge(Integer personAge) {
        this.personAge = personAge;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(Integer animalAge) {
        this.animalAge = animalAge;
    }
}
