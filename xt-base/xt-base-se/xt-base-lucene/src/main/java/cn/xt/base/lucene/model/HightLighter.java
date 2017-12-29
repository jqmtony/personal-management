package cn.xt.base.lucene.model;

import org.apache.lucene.document.Document;

/**
 * 返回的高亮对象
 */
public class HightLighter {
    private String fullText; //完整文本
    private String[] matchTexts; //高亮文本
    private Document document; //文档
    private float score; //文档评分
    private int docId; //文档id

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String[] getMatchTexts() {
        return matchTexts;
    }

    public void setMatchTexts(String[] matchTexts) {
        this.matchTexts = matchTexts;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }
}
