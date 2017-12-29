package cn.xt.base.lucene.service;

import cn.xt.base.lucene.model.HightLighter;
import cn.xt.base.lucene.model.SearcherBo;
import cn.xt.base.lucene.pagable.Pager;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

@Service
public class LuceneIndexServiceImpl implements LuceneIndexService {

    @Override
    public IndexWriter getIndexWriter(File indexPath, Analyzer analyzer, boolean... create) throws IOException {
        Directory directory = null;
        try {
            directory = FSDirectory.open(indexPath);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LUCENE_4_10_4, analyzer);
            if (create.length != 0) {
                boolean overwritten = create[0];
                if (overwritten) {
                    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                } else {
                    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
                }
            }
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            return indexWriter;
        } finally {
            //if(directory!=null)directory.close();
        }
    }

    @Override
    public List<String> getAnalyResults(Analyzer analyzer, String text) throws Exception {
        List<String> results = new LinkedList<>();
        //这里content可以是任意字符串，可以认为分词必须的一个占位符
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            results.add(termAttribute.toString());
        }
        tokenStream.end();
        tokenStream.close();
        return results;
    }

    @Override
    public Pager<Document> search(SearcherBo bo) throws ParseException, IOException {
        Directory directory = null;
        try {
            List<Document> documents = new LinkedList<>();
            QueryParser parser = new QueryParser(bo.getFieldName(), bo.getAnalyzer());
            Query query = parser.parse(bo.getSearchKey());
            //索引查询对象
            directory = FSDirectory.open(bo.getIndexPath());
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs hits = searcher.search(query, bo.getRow());//查找
            int totalHits = hits.totalHits;
            ScoreDoc[] scoreDocs = hits.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docid = scoreDoc.doc;
                float score = scoreDoc.score;
                Document doc = searcher.doc(docid);
                doc.add(new IntField("docId", docid, Field.Store.YES));
                doc.add(new FloatField("score", score, Field.Store.YES));
                doc.add(new IntField("totalHits", totalHits, Field.Store.YES));
                documents.add(doc);
            }
            Pager<Document> pager = new Pager<>();
            pager.setList(documents);
            pager.setTotalCount(totalHits);
            pager.setPageNo(bo.getPage());
            pager.setPageSize(bo.getRow());
            return pager;
        } finally {
            if (directory != null) directory.close();
        }
    }

    @Override
    public Pager<HightLighter> searchHighLighterText(SearcherBo bo, String prefix, String suffix, int textLen, int matchSize) throws ParseException, IOException, InvalidTokenOffsetsException {
        Directory directory = null;
        try {
            List<HightLighter> highLighters = new LinkedList<>();

            QueryParser parser = new QueryParser(bo.getFieldName(), bo.getAnalyzer());
            Query query = parser.parse(bo.getSearchKey());
            //索引查询对象
            directory = FSDirectory.open(bo.getIndexPath());
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs hits = searcher.search(query, bo.getRow());//查找
            int totalHits = hits.totalHits;
            ScoreDoc[] scoreDocs = hits.scoreDocs;

            QueryScorer scorer = new QueryScorer(query, bo.getFieldName());
            //"<span style=\"color:red;\">", "</span>"
            SimpleHTMLFormatter fors = new SimpleHTMLFormatter(prefix, suffix);//定制高亮标签
            Highlighter highlighter = new Highlighter(fors, scorer);//高亮分析器
            //设置当前高亮器可以处理的最大字符个数,超出则返回null
//            highlighter.setMaxDocCharsToAnalyze(1);//设置高亮处理的字符个数
            //设置匹配高亮的文本片段长度
            highlighter.setTextFragmenter(new SimpleFragmenter(textLen));

            for (ScoreDoc scoreDoc : scoreDocs) {
                int docid = scoreDoc.doc;
                float score = scoreDoc.score;
                Document doc = searcher.doc(docid);
                String targeText = doc.get(bo.getFieldName());
                TokenStream token = TokenSources.getAnyTokenStream(searcher.getIndexReader(), docid, bo.getFieldName(), bo.getAnalyzer());//获取tokenstream
                //matchSize 设置最多显示多少个片段
                String[] fragment = highlighter.getBestFragments(token, targeText, matchSize);

                HightLighter hl = new HightLighter();
                hl.setDocId(docid);
                hl.setDocument(doc);
                hl.setFullText(targeText);
                hl.setMatchTexts(fragment);
                hl.setScore(score);
                highLighters.add(hl);
            }
            Pager<HightLighter> pager = new Pager<>();
            pager.setList(highLighters);
            pager.setTotalCount(totalHits);
            pager.setPageNo(bo.getPage());
            pager.setPageSize(bo.getRow());
            return pager;
        } finally {
            if (directory != null) directory.close();
        }
    }
}
