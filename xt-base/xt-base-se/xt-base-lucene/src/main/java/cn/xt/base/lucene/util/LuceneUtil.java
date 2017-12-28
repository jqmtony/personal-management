package cn.xt.base.lucene.util;

import cn.xt.base.model.Constant;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * create by xtao
 * create in 2017/12/17 16:35
 */
public class LuceneUtil {
    private static final String LUCENE_HOME_PATH = Constant.USER_HOME_PATH + Constant.PATH_SEPARATOR;
    private static final String INDEX_STORE_PATH = LUCENE_HOME_PATH + "LuceneIndex";
    private static final int CREATE_INDEX = 1;
    private static final int UPDATE_INDEX = 2;
    private static final int DELETE_INDEX = 3;

    public static class Analyzers {
        private static final Analyzer defaultAnalyzer = new StandardAnalyzer();

        static Analyzer defaults() throws IOException {
            return defaultAnalyzer;
        }
    }

    /**
     * 查找关键字
     *
     * @param indexPath
     * @param fieldName
     * @param key
     * @param totalRecord
     * @param analyzer
     * @throws ParseException
     * @throws IOException
     */
    public static List<Document> search(String indexPath, String fieldName, String key, int totalRecord, Analyzer analyzer) throws ParseException, IOException {
        DirectoryReader reader = null;
        List<Document> documents = new LinkedList<>();
        try {
            QueryParser parser = new QueryParser(fieldName, analyzer);
            Query query = parser.parse(key);
            //索引查询对象
            Directory indexDir = FSDirectory.open(new File(indexPath));
            reader = DirectoryReader.open(indexDir);
            IndexSearcher indexSearcher = new IndexSearcher(reader);
            TopDocs topDocuments = indexSearcher.search(query, totalRecord);
            //总共匹配条目
            int totalHits = topDocuments.totalHits;
            System.out.println("总共匹配记录:" + totalHits);
            ScoreDoc[] scoreDocs = topDocuments.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                float score = scoreDoc.score;
                Document doc = indexSearcher.doc(docId);
                doc.add(new FloatField("score", score, Store.NO));
                documents.add(doc);
            }
        } finally {
            if (reader != null)
                reader.close();
        }
        return documents;
    }


    public static IndexWriter getIndexWriter(String indexStorePath, Analyzer analyzer, int cud) throws IOException {
        File indexHome = new File(indexStorePath);
        if (!indexHome.exists()) {
            indexHome.mkdirs();
        }
        Directory indexStoreDir = FSDirectory.open(indexHome);

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                Version.LUCENE_4_10_4, analyzer);
        if (cud == CREATE_INDEX) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        } else if (cud == UPDATE_INDEX) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
        }
        IndexWriter indexWriter = new IndexWriter(indexStoreDir, indexWriterConfig);
        return indexWriter;
    }

    /**
     * 添加、删除或者修改索引
     *
     * @param document       文档， 索引生成的依据
     * @param cud            执行添加、修改还是删除
     * @param udTerms        如果是修改或者删除索引，则需要传入操作（索引修改其实是根据Term先删除在添加）的依据(通过Term）
     * @throws IOException
     */
    public static void saveOrUpdateIndex(IndexWriter indexWriter, Document document, int cud, Term... udTerms) throws IOException {
        if (cud == CREATE_INDEX) {
            indexWriter.addDocument(document);
        } else if (cud == UPDATE_INDEX) {
            if (udTerms != null) {
                for (Term updTerm : udTerms) {
                    if (updTerm != null) {
                        indexWriter.updateDocument(updTerm, document);
                    }
                }
            }
        } else if (cud == DELETE_INDEX) {
            indexWriter.deleteDocuments(udTerms);
        } else {
            throw new RuntimeException("未知的操作标识，只允许添加、修改、删除");
        }
    }

    /**
     * 获取分词结果
     *
     * @param analyzer
     * @return
     * @throws Exception
     */
    public static List<String> getAnalyzerResults(Analyzer analyzer, String text) throws Exception {
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

    public static void main(String[] args) throws Exception {

        String indexDir = INDEX_STORE_PATH;

        File file = new File(indexDir);
        if (!file.exists()) file.mkdirs();
        System.out.println(indexDir);

        for (int i = 1; i <= 3; i++) {
            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(i + ".txt");
            String content = FileUtil.getContent(in);
            String title = content.split("\r\n")[0];
            System.out.println(getAnalyzerResults(Analyzers.defaults(), title));
            Document doc = new Document();
            doc.add(new LongField("id", i, Store.YES));
            doc.add(new TextField("title", title, Store.YES));
            doc.add(new TextField("content", content, Store.YES));
//            saveOrUpdateIndex(doc, Analyzers.defaults(), indexDir, CREATE_INDEX);
        }
        //查询
        List<Document> documents = search(indexDir, "title", "git", 10, Analyzers.defaults());
        if (!CollectionUtils.isEmpty(documents)) {
            for (Document document : documents) {
                String id = document.get("id");
                String c = document.get("content");
                String title = document.get("title");
                String score = document.get("score");
                System.out.println(title + "," + score);
            }
        }
    }
}
