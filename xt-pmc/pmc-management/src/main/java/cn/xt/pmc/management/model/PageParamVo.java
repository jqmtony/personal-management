package cn.xt.pmc.management.model;

import cn.xt.base.pageable.PageVo;

/**
 * 用于封装首页参数
 *
 * create by xt
 */
public class PageParamVo extends PageVo{
    private int blogPage;

    public int getBlogPage() {
        return blogPage;
    }

    public void setBlogPage(int blogPage) {
        this.setPage(blogPage);
        this.blogPage = blogPage;
    }
}
