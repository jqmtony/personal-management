package cn.xt.pmc.management.model;

import cn.xt.base.pageable.PageVo;

/**
 * 用于封装首页参数
 *
 * create by xt
 */
public class PageParamVo extends PageVo{
    private int blogPage = 1;
    private Long blogClassify;

    public int getBlogPage() {
        return blogPage;
    }

    public void setBlogPage(int blogPage) {
        this.setPage(blogPage);
        this.blogPage = blogPage;
    }

    public Long getBlogClassify() {
        return blogClassify;
    }

    public void setBlogClassify(Long blogClassify) {
        this.blogClassify = blogClassify;
    }
}
