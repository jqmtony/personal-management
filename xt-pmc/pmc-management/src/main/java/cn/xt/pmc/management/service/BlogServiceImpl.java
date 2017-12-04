package cn.xt.pmc.management.service;

import cn.xt.base.core.BaseDao;
import cn.xt.base.pageable.Pager;
import cn.xt.base.service.BaseServiceImpl;
import cn.xt.pmc.management.dao.BlogDao;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogVo;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by xtao
 * create in 2017/11/19 20:00
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<Blog> implements BlogService {
    @Resource
    private BlogDao blogDao;

    @Override
    protected BaseDao<Blog> getDao() {
        return blogDao;
    }

    @Override
    public List<Blog> findByUserId(Long createBy) throws UnsupportedEncodingException {
        List<Blog> blogs = blogDao.findByUserId(createBy);
        convert(blogs);
        return blogs;
    }

    @Override
    public List<Blog> findAll() throws UnsupportedEncodingException {
        List<Blog> blogs = blogDao.findAll();
        convert(blogs);
        return blogs;
    }

    @Override
    public Pager<Blog> findConvertPage(BlogVo blogVo) throws UnsupportedEncodingException {
        Pager<Blog> pager = this.findPage(blogVo);
        convert(pager.getData());
        return pager;
    }

    private void convert(List<Blog> blogs) throws UnsupportedEncodingException {
        for (Blog blog : blogs) {
            if (StringUtils.hasText(blog.getHtml())) {
                String html = URLDecoder.decode(blog.getHtml(), "UTF-8");
                html = html.replace("\n", "").replace(" ", "");
                blog.setHtml(html);
            }

            if (StringUtils.hasText(blog.getOriginal())) {
                String original = URLDecoder.decode(blog.getOriginal(), "UTF-8");
                original = original.replace("\n", "").replace(" ", "");
                blog.setOriginal(original);
            }
            if (StringUtils.hasText(blog.getText())) {
                String text = URLDecoder.decode(blog.getText(), "UTF-8");
                text = text.replace("\n", "").replace(" ", "");
                blog.setText(text);
            }
        }
    }
}
