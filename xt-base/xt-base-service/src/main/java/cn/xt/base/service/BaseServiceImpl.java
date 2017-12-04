package cn.xt.base.service;


import cn.xt.base.core.BaseDao;
import cn.xt.base.pageable.PageVo;
import cn.xt.base.pageable.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshun on 16-1-14.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getDao();


    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public T get(Long id) {
        return getDao().findById(id);
    }

    @Override
    public int insert(T bean) {
        int i = getDao().insert(bean);
        if (i == 0) {
            throw new RuntimeException("创建失败");
        }
        return i;
    }

    @Override
    public int update(T bean) {
        int i = getDao().update(bean);
        if (i == 0) {
            throw new RuntimeException("修改失败");
        }
        return i;
    }

    @Override
    public int delete(Long id) {
        int i = getDao().delete(id);
        if (i == 0) {
            throw new RuntimeException("删除失败");
        }
        return i;
    }

    @Override
    public Pager<T> findPage(PageVo pageVo){
        return getDao().findPage(pageVo);
    }
}
