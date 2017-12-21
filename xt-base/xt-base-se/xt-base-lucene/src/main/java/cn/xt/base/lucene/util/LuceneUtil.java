package cn.xt.base.lucene.util;

import cn.xt.base.model.Constant;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
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
            ScoreDoc[] scoreDocs = topDocuments.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                float score = scoreDoc.score;
                Document doc = indexSearcher.doc(docId);
                documents.add(doc);
            }
            System.out.println(totalHits);
        } finally {
            if (reader != null)
                reader.close();
        }
        return documents;
    }

    /**
     * 添加或者修改索引
     *
     * @param document       文档， 索引生成的依据
     * @param analyzer       分词器
     * @param indexStorePath 索引存放目录
     * @param create         创建还是修改索引，true表示创建索引
     * @param updTerms       如果是修改索引，则需要传入修改（其实是根据Term先删除在添加）的依据(通过Term）
     * @throws IOException
     */
    public static void saveOrUpdateIndex(Document document, Analyzer analyzer, String indexStorePath, boolean create, Term... updTerms) throws IOException {
        IndexWriter indexWriter = null;
        try {
            File indexHome = new File(indexStorePath);
            if (!indexHome.exists()) {
                indexHome.mkdirs();
            }
            Directory indexStoreDir = FSDirectory.open(indexHome);

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LUCENE_4_10_4, analyzer);
            indexWriterConfig.setOpenMode(create ? IndexWriterConfig.OpenMode.CREATE : IndexWriterConfig.OpenMode.APPEND);

            indexWriter = new IndexWriter(indexStoreDir, indexWriterConfig);
            if (create) {
                indexWriter.addDocument(document);
            } else {
                if (updTerms != null) {
                    for (Term updTerm : updTerms) {
                        if (updTerm != null) {
                            indexWriter.updateDocument(updTerm, document);
                        }
                    }
                }
            }
        } finally {
            if (indexWriter != null)
                indexWriter.close();
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
        System.out.println(results);
        return results;
    }

    /*public static void displayAllTokenInfo(Analyzer analyzer, String fieldName,String fieldVal) throws IOException {
        TokenStream stream = a.tokenStream(fieldName,new StringReader(fieldVal));
        //位置增量的属性，存储语汇单元之间的距离
        PositionIncrementAttribute pis=stream.addAttribute(PositionIncrementAttribute.class);
        //每个语汇单元的位置偏移量
        OffsetAttribute oa=stream.addAttribute(OffsetAttribute.class);
        //存储每一个语汇单元的信息（分词单元信息）
        CharTermAttribute cta=stream.addAttribute(CharTermAttribute.class);
        //使用的分词器的类型信息
        TypeAttribute ta=stream.addAttribute(TypeAttribute.class);
        stream.reset();
        int lastOffset = -1;
        while(stream.incrementToken()) {
            if(oa.startOffset() < lastOffset) continue;
            lastOffset = oa.endOffset();
//                System.out.print("增量:"+pis.getPositionIncrement()+":");
//                System.out.print("分词:"+cta+"位置:["+oa.startOffset()+"~"+oa.endOffset()+"]->类型:"+ta.type()+"\n");
            System.out.print("["+cta+"]");
        }
        stream.end();
    }*/

    public static void main(String[] args) throws Exception {

        String indexDir = INDEX_STORE_PATH;

        File file = new File(indexDir);
        if (!file.exists()) file.mkdirs();
        System.out.println(indexDir);

        String content = "这是一段中文，this is a length of English";
        getAnalyzerResults(Analyzers.defaults(), content);

        for(int i=0; i<100; i++){
            Document doc = new Document();
            doc.add(new LongField("id", i, Store.YES));
            //StringField不分词
            doc.add(new TextField("content", content, Store.YES));

            String c = content+"_"+i;
            saveOrUpdateIndex(doc, Analyzers.defaults(), indexDir, true);
        }
        //查询
        List<Document> documents = search(indexDir, "content", "English", 10, Analyzers.defaults());
        if (!CollectionUtils.isEmpty(documents)) {
            for (Document document : documents) {
                String id = document.get("id");
                String c = document.get("content");
                System.out.println(id+","+c);
            }
        }
    }
}
