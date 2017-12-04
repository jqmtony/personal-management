package cn.xt.base.core;

import cn.xt.base.pageable.PageVo;
import cn.xt.base.pageable.Pager;

public interface BaseDao<T> {
    T findById(Long id);

    int insert(T bean);

    int update(T bean);

    int save(T bean);

    int delete(Long id);

    Pager<T> findPage(PageVo pageVo);
}
