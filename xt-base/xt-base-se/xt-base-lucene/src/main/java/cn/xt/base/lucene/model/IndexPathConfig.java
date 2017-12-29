package cn.xt.base.lucene.model;

import cn.xt.base.model.Constant;

import java.io.File;

public class IndexPathConfig {
    private static final String LUCENE_HOME_PATH = Constant.USER_HOME_PATH + Constant.PATH_SEPARATOR;
    private static final String INDEX_STORE_PATH = LUCENE_HOME_PATH + "LuceneIndex";
    public static final File INDEX_STORE_DIR = new File(INDEX_STORE_PATH);
    static {
        if(!INDEX_STORE_DIR.exists())INDEX_STORE_DIR.mkdirs();
    }
}
