package cn.xt.pmc.management.model;

import cn.xt.base.pageable.PageVo;

/**
 * create by xtao
 * create in 2017/12/4 17:07
 */
public class BlogVo extends PageVo {

    private int blogPage;
    private Long createBy;

    public int getBlogPage() {
        return blogPage;
    }

    public void setBlogPage(int blogPage) {
        this.blogPage = blogPage;
        this.setPage(blogPage);
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
