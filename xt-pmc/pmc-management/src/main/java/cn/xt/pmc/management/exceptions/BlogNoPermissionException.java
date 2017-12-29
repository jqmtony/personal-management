package cn.xt.pmc.management.exceptions;

public class BlogNoPermissionException extends Exception {
    public BlogNoPermissionException(String s) {
        super(s);
    }

    public BlogNoPermissionException(Exception e) {
        super(e);
    }
}
