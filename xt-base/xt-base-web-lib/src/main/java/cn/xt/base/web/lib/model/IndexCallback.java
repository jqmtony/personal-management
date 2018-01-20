package cn.xt.base.web.lib.model;

import java.util.List;

/**
 * 首页回调接口
 */
public interface IndexCallback {
    /**
     * 返回菜单下拉数据
     * @return
     */
    List getHeaderMenus();
}
