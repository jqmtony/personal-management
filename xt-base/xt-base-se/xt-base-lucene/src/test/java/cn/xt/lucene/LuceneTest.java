//package cn.xt.lucene;
//
//import cn.xt.base.config.AppConfig;
//import cn.xt.base.lucene.model.HightLighter;
//import cn.xt.base.lucene.model.SearcherBo;
//import cn.xt.base.lucene.pagable.Pager;
//import cn.xt.base.lucene.service.LuceneIndexService;
//import cn.xt.base.lucene.util.Analyzers;
//import cn.xt.base.lucene.util.LuceneUtil;
//import cn.xt.base.util.FileUtil;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.InputStream;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AppConfig.class})
//public class LuceneTest {
//
//    @Resource
//    private LuceneIndexService indexService;
//
//    File indexPath = null;
//    Analyzer analyzer = null;
//    @Before
//    public void setUp() throws Exception {
//        indexPath = new File(LuceneUtil.LUCENE_HOME_PATH);
//        analyzer = LuceneUtil.Analyzers.defaults();
//    }
//
//    @Test
//    public void createIndexTest() throws Exception {
//        IndexWriter indexWriter = indexService.getIndexWriter(indexPath,analyzer,false);
//        for (int i = 1; i <= 3; i++) {
//            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(i + ".txt");
//            String content = FileUtil.getContent(in);
//            String title = content.split("\r\n")[0];
//            System.out.println(indexService.getAnalyResults(analyzer, title));
//            /*Document doc = new Document();
//            doc.add(new LongField("id", i, Store.YES));
//            doc.add(new TextField("title", title, Store.YES));
//            doc.add(new TextField("content", content, Store.YES));
//            indexWriter.addDocument(doc);*/
//        }
//        indexWriter.commit();
//        indexWriter.close();
//    }
//
//    @Test
//    public void analyzerTet() throws Exception {
//        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("1.txt");
//        String content = FileUtil.getContent(in);
//        List<String> list = indexService.getAnalyResults(Analyzers.defaults(),content);
//        System.out.println(list);
//        list = indexService.getAnalyResults(Analyzers.ik(),content);
//        System.out.println(list);
//        list = indexService.getAnalyResults(Analyzers.ik(true),content);
//        System.out.println(list);
//    }
//
//    @Test
//    public void searchHighLighterTest() throws Exception {
//        SearcherBo bo = new SearcherBo();
//        bo.setAnalyzer(analyzer);
//        bo.setFieldName("content");
//        bo.setSearchKey("JSON");
//        bo.setIndexPath(indexPath);
//        Pager<HightLighter> pager = indexService.searchHighLighterText(
//                bo,
//                "<span style=\"color:red;\">",
//                "</span>",
//                100,
//                1);
//        System.out.println("------------------------------------");
//        for (HightLighter hl : pager.getList()) {
//            for (String str : hl.getMatchTexts()) {
//                System.out.println("==============="+hl.getDocId()+"/"+hl.getDocument().get("title")+"===========================");
//                System.out.println(str);
//            }
//        }
//        System.out.println("------------------------------------");
//    }
//
//    @Test
//    public void searchTest() throws Exception {
//        SearcherBo bo = new SearcherBo();
//        bo.setAnalyzer(analyzer);
//        bo.setFieldName("content");
//        bo.setSearchKey("json");
//        bo.setIndexPath(indexPath);
//        Pager<Document> pager = indexService.search(bo);
//        System.out.println("------------------------------------");
//        System.out.println(pager.getList());
//        System.out.println("------------------------------------");
//    }
//}
