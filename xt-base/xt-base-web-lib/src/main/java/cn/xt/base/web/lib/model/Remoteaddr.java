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
    private String homeloc;
    private String isp;

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

    public String getHomeloc() {
        return homeloc;
    }

    public void setHomeloc(String homeloc) {
        this.homeloc = homeloc;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
