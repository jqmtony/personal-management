package cn.xt.base.pageable;

public class PageVo {
    private int page = 1; //页码
    private int row = 20; //每页展示几条数据
    private int dboff;  //数据库从第几条数据查起(limt dboff, row)

    //是否计算总记录数，这个变量用于有些不需要分页的查询，这样就省去了查total的时间
    private boolean countable = true;

    public PageVo(){}

    public PageVo(int page,int row){
        this.page = page;
        this.row = row;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return Math.max(row,1);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getDboff() {
        return Math.max((page-1),dboff)*row;
    }

    public void setDboff(int dboff) {
        this.dboff = dboff;
    }

    public boolean getCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }
}
