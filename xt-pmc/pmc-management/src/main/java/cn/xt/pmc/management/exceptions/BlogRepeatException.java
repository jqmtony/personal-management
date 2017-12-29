package cn.xt.pmc.management.exceptions;

public class BlogRepeatException extends Exception {
    public BlogRepeatException(String s) {
        super(s);
    }

    public BlogRepeatException(Exception e) {
        super(e);
    }
}
