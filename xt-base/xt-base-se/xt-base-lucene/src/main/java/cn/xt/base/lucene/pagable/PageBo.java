package cn.xt.base.lucene.pagable;

public class PageBo {
    private int page = 1;
    private int row = 20;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
