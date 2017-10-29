package cn.xt.base.pageable;

import java.util.List;

public class Pager<T>  implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private int page;
    private int row;
    private int totalcount;
    private List<T> data;

    public Pager(){}

    public Pager(int page,int row,int totalcount,List<T> data){
        this.page = page;
        this.row = row;
        this.totalcount = totalcount;
        this.data = data;
    }

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

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
