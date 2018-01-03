package cn.xt.pmc.management.service;

import cn.xt.base.core.BaseDao;
import cn.xt.base.lucene.model.IndexPathConfig;
import cn.xt.base.lucene.service.LuceneIndexService;
import cn.xt.base.lucene.util.Analyzers;
import cn.xt.base.pageable.Pager;
import cn.xt.base.service.BaseServiceImpl;
import cn.xt.pmc.management.dao.BlogDao;
import cn.xt.pmc.management.exceptions.BlogNoPermissionException;
import cn.xt.pmc.management.exceptions.BlogRepeatException;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogVo;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import static cn.xt.pmc.management.util.ShiroUtil.getPrincipalId;

/**
 * create by xtao
 * create in 2017/11/19 20:00
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<Blog> implements BlogService {
    protected Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Resource
    private LuceneIndexService luceneIndexService;

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

    @Override
    public Long findRepeatBlogSize(String title, Long createBy) {
        return blogDao.findRepeatBlogSize(title,createBy);
    }

    @Transactional
    @Override
    public int insertEntity(Blog entity) throws BlogRepeatException, IOException {
        Long loginId = getPrincipalId();
        Long repeatSize = this.findRepeatBlogSize(entity.getTitle(),loginId);
        if(repeatSize>0){
            throw new BlogRepeatException("存在相同标题的博客");
        }
        entity.setCreateTime(new Date());
        entity.setCreateBy(getPrincipalId());
        int count = this.insert(entity);
        if(count>0){
            createIndex(entity);
        }
        return count;
    }

    @Transactional
    @Override
    public int updateEntity(Blog entity) throws BlogNoPermissionException, IOException {
        Blog dbBlog = this.get(entity.getId());
        //当前用户不是博客创建者
        if (!dbBlog.getCreateBy().equals(getPrincipalId())) {
            throw new BlogNoPermissionException("没有操作权限");
        }
        entity.setUpdateBy(getPrincipalId());
        entity.setUpdateTime(new Date());
        updateIndex(entity);
        return this.update(entity);
    }

    private void createIndex(Blog blog) throws IOException {
        IndexWriter iw = null;
        try {
            File path = IndexPathConfig.INDEX_STORE_DIR;
            //覆盖模式
            iw = luceneIndexService.getIndexWriter(path, Analyzers.ik(true),true);

            Document doc = new Document();
            doc.add(new LongField("id",blog.getId(), Field.Store.YES));
            doc.add(new TextField("title",blog.getTitle(), Field.Store.YES));
            doc.add(new TextField("content",blog.getText(), Field.Store.YES));
            iw.addDocument(doc);
        } finally {
            if(iw!=null){
                iw.commit();
                iw.close();
            }
        }
    }

    private void updateIndex(Blog blog) throws IOException {
        IndexWriter iw = null;
        try {
            File path = IndexPathConfig.INDEX_STORE_DIR;
            //覆盖模式
            iw = luceneIndexService.getIndexWriter(path, Analyzers.ik(true),true);

            Document doc = new Document();
            doc.add(new LongField("id",blog.getId(), Field.Store.YES));
            doc.add(new TextField("title",blog.getTitle(), Field.Store.YES));
            doc.add(new TextField("content",blog.getText(), Field.Store.YES));
            iw.updateDocument(new Term("id",blog.getId()+""),doc);
        } finally {
            if(iw!=null){
                iw.commit();
                iw.close();
            }
        }
    }

    private void convert(List<Blog> blogs) throws UnsupportedEncodingException {
        for (Blog blog : blogs) {
            convert(blog);
        }
    }
    private void convert(Blog blog) throws UnsupportedEncodingException {
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
