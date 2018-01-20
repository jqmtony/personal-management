package cn.xt.pmc.management.model;

import cn.xt.base.pageable.PageVo;

/**
 * create by xtao
 * create in 2017/12/4 17:07
 */
public class BlogVo extends PageVo {

    private String title;
    private Long createBy;
    private BlogState state = BlogState.normal;
    /**
     * 博客分类id
     */
    private Long classify;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public BlogState getState() {
        return state;
    }

    public void setState(BlogState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getClassify() {
        return classify;
    }

    public void setClassify(Long classify) {
        this.classify = classify;
    }
}
