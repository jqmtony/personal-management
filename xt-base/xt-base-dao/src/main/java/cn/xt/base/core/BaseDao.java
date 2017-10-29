package cn.xt.base.core;

public interface BaseDao<T> {
    T findById(Long id);

    int insert(T bean);

    int update(T bean);

    int save(T bean);

    int delete(Long id);
}
