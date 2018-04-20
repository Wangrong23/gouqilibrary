package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Model;

import java.lang.reflect.Array;
import java.util.List;

public class Booklist extends Model<Booklist> {
    private int id;
    private String bookname;
    private String bookinfo;
    private String booktype;
    private String introduction;
    private String commentnum;
    private double commentlevel;
    private String imgsrc;
    private String href;



    public static final Booklist dao = new Booklist().dao();





}
