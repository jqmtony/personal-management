package cn.xt.base.service;


import cn.xt.base.pageable.PageVo;
import cn.xt.base.pageable.Pager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by heshun on 16-1-14.
 */
public interface BaseService<T> {

    T get(Long id);

    @Transactional
    int insert(T bean);

    @Transactional
    int update(T bean);

    @Transactional
    int delete(Long id);

    Pager<T> findPage(PageVo pageVo);
}
