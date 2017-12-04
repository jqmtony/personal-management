package cn.xt.base.web.lib.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * create by xtao
 * create in 2017/12/4 0:21
 */
public interface PmcBlogCallBack<T> {
    List<T> getBlogList(Long userId) throws UnsupportedEncodingException;
}
