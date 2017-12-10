package cn.xt.base.web.lib.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Remoteaddr {
    private Long id;
    private String ip;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date time;
    private Long loginid;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getLoginid() {
        return this.loginid;
    }

    public void setLoginid(Long loginid) {
        this.loginid = loginid;
    }
}
