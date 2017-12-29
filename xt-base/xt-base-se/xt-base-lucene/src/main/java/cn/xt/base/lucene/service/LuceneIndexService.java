package cn.xt.base.lucene.service;

import cn.xt.base.lucene.model.HightLighter;
import cn.xt.base.lucene.model.SearcherBo;
import cn.xt.base.lucene.pagable.Pager;
import cn.xt.base.model.Constant;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface LuceneIndexService {
    /**
     * 获取indexWriter
     *
     * @param indexPath
     * @param analyzer
     * @param create    true表示覆盖，false表示追加
     * @return
     */
    IndexWriter getIndexWriter(File indexPath, Analyzer analyzer, boolean... create) throws IOException;

    /**
     * 获取分词结果
     * @param analyzer
     * @param text
     * @return
     * @throws Exception
     */
    List<String> getAnalyResults(Analyzer analyzer, String text) throws Exception;

    /**
     * 查找文档
     * @param bo
     * @return
     */
    Pager<Document> search(SearcherBo bo) throws ParseException, IOException;

    /**
     * 查找文档并返回高亮文本
     * @param bo
     * @param prefix 高亮前缀
     * @param suffix 高亮后缀
     * @param matchSize 设置最多匹配几个高亮项目，如果该值大于实际匹配的片段数量，则相应片段文本长度会变长
     * @param textLen 设置匹配的高亮片段文本长度
     * @return
     * @throws ParseException
     * @throws IOException
     */
    Pager<HightLighter> searchHighLighterText(SearcherBo bo, String prefix, String suffix, int textLen, int matchSize) throws ParseException, IOException, InvalidTokenOffsetsException;
}
