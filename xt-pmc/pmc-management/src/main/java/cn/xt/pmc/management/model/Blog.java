package cn.xt.pmc.management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * create by xtao
 * create in 2017/11/19 19:12
 */
public class Blog {
    private Long id;
    @NotEmpty(message = "博客标题不能为空")
    @Length(min = 5, max = 20,message = "博客标题太短或太长")
    private String title;
    //博客类别id
    @NotNull(message = "博客类别不能为空")
    private Long classify;
    private Long classifypid;
    @NotEmpty(message = "博客内容不能为空")
    private String html;
    private String text;
    @NotEmpty(message = "博客内容不能为空")
    private String original;
    private ContentType contentType;
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private Long createBy;
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date updateTime;
    private Long updateBy;
    private BlogState state = BlogState.normal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String decode(String text) throws UnsupportedEncodingException {
        return URLDecoder.decode(text,"UTF-8");
    }

    public BlogState getState() {
        return state;
    }

    public void setState(BlogState state) {
        this.state = state;
    }

    public Long getClassify() {
        return classify;
    }

    public void setClassify(Long classify) {
        this.classify = classify;
    }

    public Long getClassifypid() {
        return classifypid;
    }

    public void setClassifypid(Long classifypid) {
        this.classifypid = classifypid;
    }
}
