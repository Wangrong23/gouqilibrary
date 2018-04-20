package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Page;

import java.util.List;

public class ShareMethod {
    public static String type;
    public static int staticReaderId;
    public static String keyword;
    public static int page;

    public static void setType(String btype){
        type = btype;
    }
    public static String getType(){
        return type;
    }
    Booklist booklist = new Booklist();
    Mark mark = new Mark();
    Back back = new Back();
    Borrow borrow = new Borrow();
    public List toFindByType(String booktype){
        List<Booklist> booklists = booklist.find("select * from booklist where booktype = '"+booktype+"'");
        return booklists;
    }
    public List toFindById(int id){
        List<Booklist> booklists = booklist.find("select * from booklist where id = "+id);
        return booklists;
    }
    public static void setPage(int pageNum){
        page = pageNum;
    }
    public static int getPage(){
        return page;
    }
    public List toFind(int page){
        int allNum = booklist.find("select count(id) from booklist").get(0).getInt("count(id)");
        List<Booklist> booklists = booklist.paginate(page,120,"select *" ,"from booklist").getList();
        return booklists;
    }
    public List toShelfs(){
        List<Booklist> booklists = booklist.find("select distinct booktype from booklist");
        return booklists;
    }

    public static void setReaderId(int readerId){
        staticReaderId = readerId;
    }
    public static int getReaderId(){
        return staticReaderId;
    }
    public List toFindMyMark(int readerId){
        List<Mark> markList = mark.find("select * from mark where reader_id = "+readerId);
        return markList;
    }
    public List toFindMyReadIt(int readerId){
        List<Back> backList = back.find("select * from back where reader_id = "+readerId);
        return backList;
    }
    public List toFindMyBorrowed(int readerId){
        List<Borrow> borrowList = borrow.find("select * from borrow where reader_id = "+readerId);
        return borrowList;
    }
    public static void setKeyword(String word){
        keyword = word;
    }
    public static String getKeyword(){
        return keyword;
    }
    public List toSearch(String keyword){
        List<Booklist> booklists = booklist.find("SELECT * FROM booklist where bookname like '%"+keyword+"%' or bookinfo like '%"+keyword+"%' or booktype like '%"+keyword+"%' or commentnum like '%"+keyword+"%'");
        return booklists;
    }
}
