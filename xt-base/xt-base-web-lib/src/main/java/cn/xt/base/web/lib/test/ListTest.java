package cn.xt.base.web.lib.test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("444");
        list.subList(2,list.size()).clear();
        System.out.println(list);
    }
}
