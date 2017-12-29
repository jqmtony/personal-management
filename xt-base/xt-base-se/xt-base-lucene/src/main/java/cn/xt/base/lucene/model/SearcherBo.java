package cn.xt.base.lucene.model;

import cn.xt.base.lucene.pagable.PageBo;
import org.apache.lucene.analysis.Analyzer;

import java.io.File;

public class SearcherBo extends PageBo {
    private Analyzer analyzer;
    private String fieldName;
    private String searchKey;
    private File indexPath;

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public File getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(File indexPath) {
        this.indexPath = indexPath;
    }
}
