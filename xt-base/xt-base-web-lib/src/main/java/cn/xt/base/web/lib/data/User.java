package cn.xt.base.web.lib.data;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class User {
    @NotNull(message = "{user.id.null}")
    private Long id;

    @NotEmpty(message = "{user.name.null}")
    @Length(min = 5, max = 20, message = "{user.name.length.illegal}")
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "{user.name.illegal}")
    private String username;
    @NotNull(message = "{user.password.null}")
    private String password;
    private String nickname;
    private Integer age;
    private Date birthday;
    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @NotNull
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @NotEmpty
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", state=" + state +
                '}';
    }
}
