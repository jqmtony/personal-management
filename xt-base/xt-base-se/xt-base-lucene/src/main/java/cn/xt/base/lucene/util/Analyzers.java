package cn.xt.base.lucene.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Analyzers {
    private static Analyzer defaultAnalyzer = null;
    private static Analyzer ikAnalyzer = null;

    public static Analyzer defaults() {
        if (defaultAnalyzer == null) {
            defaultAnalyzer = new StandardAnalyzer();
        }
        return defaultAnalyzer;
    }

    public static Analyzer ik() {
        if (ikAnalyzer == null) {
            ikAnalyzer = new IKAnalyzer();
        }
        return ikAnalyzer;
    }

    public static Analyzer ik(boolean useSmart) {
        if (ikAnalyzer == null) {
            ikAnalyzer = new IKAnalyzer(useSmart);
        }
        return ikAnalyzer;
    }
}