package cn.xt.base.service;


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


}
