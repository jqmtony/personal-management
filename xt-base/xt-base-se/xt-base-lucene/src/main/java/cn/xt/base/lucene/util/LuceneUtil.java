package cn.xt.base.lucene.util;

import cn.xt.base.model.Constant;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

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
     * 添加或者修改索引
     *
     * @param document 文档， 索引生成的依据
     * @param analyzer 分词器
     * @param indexStorePath 索引存放目录
     * @param create 创建还是修改索引，true表示创建索引
     * @param updTerms 如果是修改索引，则需要传入修改（其实是根据Term先删除在添加）的依据(通过Term）
     * @throws IOException
     */
    public static void saveOrUpdateIndex(Document document, Analyzer analyzer, String indexStorePath,boolean create,Term... updTerms) throws IOException {
        IndexWriter indexWriter = null;
        try {
            File indexHome = new File(indexStorePath);
            if(!indexHome.exists()){
                indexHome.mkdirs();
            }
            Directory indexStoreDir = FSDirectory.open(indexHome);

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LUCENE_4_10_4, analyzer);
            indexWriterConfig.setOpenMode(create? IndexWriterConfig.OpenMode.CREATE: IndexWriterConfig.OpenMode.APPEND);

            indexWriter = new IndexWriter(indexStoreDir, indexWriterConfig);
            if(create){
                indexWriter.addDocument(document);
            } else {
                if(updTerms!=null){
                    for (Term updTerm : updTerms) {
                        if(updTerm!=null){
                            indexWriter.updateDocument(updTerm,document);
                        }
                    }
                }
            }
        } finally {
            if (indexWriter != null)
                indexWriter.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Document doc = new Document();
        doc.add(new LongField("id", 10086L, Store.YES));
        doc.add(new StringField("content","myengilshisverygood,haha.中国人民共和国是一个伟大的国家",Store.YES));
        String indexDir = INDEX_STORE_PATH;
        saveOrUpdateIndex(doc,Analyzers.defaults(),indexDir,true);
    }
}
