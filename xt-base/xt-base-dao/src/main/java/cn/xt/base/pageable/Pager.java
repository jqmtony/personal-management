package cn.xt.base.pageable;

import java.util.LinkedList;
import java.util.List;

public class Pager<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int page;
    private int row;
    private int totalcount;
    private int pageSize;
//    自定义请求参数
    private String reqParams;
    private List<T> data;
    private int pageNumSize = 20; //分页栏数量
    private List<Integer> pageNums = new LinkedList<>();

    public Pager() {
    }

    public Pager(int page, int row, int totalcount, List<T> data) {
        this.page = page;
        this.row = row;
        this.totalcount = totalcount;
        this.data = data;
    }

    public int getPageSize() {
        return (totalcount % row == 0) ? (totalcount / row) : (totalcount / row + 1);
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

    public int getPageNumSize() {
        return pageNumSize;
    }

    public void setPageNumSize(int pageNumSize) {
        this.pageNumSize = pageNumSize;
    }

    public List<Integer> getPageNums() {
        int pagesize = getPageSize();

        if(pagesize==0){
            return null;
        }

        int zeroPage = Math.max(page - 1, 0);

        int offset = pageNumSize % 2;
        int middleVal = (offset == 0) ? pageNumSize / 2 : (pageNumSize / 2 + 1);
        int leftOffset = middleVal - 1 - offset;
        int rightOffset = middleVal + 1;

        int start = zeroPage - leftOffset;
        int end = zeroPage + rightOffset;
        int startTemp = start, endTemp = end;
        if (startTemp < 0) {
            end = end + Math.abs(startTemp);
            if (end > pagesize) end = pagesize;
        }
        if (endTemp > pagesize) {
            int o = Math.max((endTemp - pagesize), 0);
            start = start - o;
            if (start < 0) start = 0;
        }
        if (start < 0) start = 0;
        if (end > pagesize) end = pagesize;
        for (int i = start + 1; i <= end; i++) {
            pageNums.add(i);
        }
        return pageNums;
    }

    public void setPageNums(List<Integer> pageNums) {
        this.pageNums = pageNums;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }
}
