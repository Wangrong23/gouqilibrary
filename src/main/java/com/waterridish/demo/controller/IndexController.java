package com.waterridish.demo.controller;

import com.jfinal.core.Controller;
import com.waterridish.demo.model.*;

import java.util.List;

public class IndexController extends Controller {
    //渲染主页
    public void index(){
        String readerIdstr = getSessionAttr("readerId");
        int pageNum = 1;
        String pageNumStr = getPara("page");
        System.out.println(pageNumStr);
        if (pageNumStr != null){
            pageNum = Integer.parseInt(pageNumStr);
        }
        ShareMethod.setPage(pageNum);
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
            render("gou.html");
//            render("public_foot.html");
        }
    }
    //渲染登录界面
    public void login(){
        renderTemplate("login.html");
    }
    //渲染注册页面
    public void register(){
        renderTemplate("register.html");
    }
    //登录操作
    public void loginCon(){
        String bookcardNum = getPara("inputBookcardNum");
        String password = getPara("inputPassword");
        LogIn logIn = new LogIn(bookcardNum,password);
        if (logIn.exist){
            if (logIn.pass){
                setSessionAttr("readerId", logIn.readerId);
                renderHtml("<script>window.location.replace(\"/\");</script>");
            }else {
                renderHtml("<script>alert('密码错误');\nhistory.go(-1);</script>");
            }
        }else {
            renderHtml("<script>alert('账户不存在');\nhistory.go(-1);</script>");
        }
    }
    //注册操作
    public void registerCon(){
        Reader reader = getModel(Reader.class, "");
        Register register = new Register(reader);
        if (register.regist){
            renderHtml("<script>alert('注册成功,请记住您的借书卡号："+register.bookcardNum+"');\nwindow.location.replace(\"login\");</script>");
        }else {
            renderHtml("<script>alert('注册失败');\nhistory.go(-1);</script>");
        }
    }
    //退出登录
    public void logout(){
        getSession().removeAttribute("readerId");
        renderHtml("<script>window.location.replace(\"login\");</script>");
    }
    //码住
    public void mark(){
        int bookId = getParaToInt("bookid");
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
        }
        Mark mark = new Mark();
        mark.toMark(readerId,bookId);
        if (mark.marked){
            renderHtml("<script>alert('你已经码过这本书了');\nhistory.go(-1);</script>");
        }else {
            if (mark.success){
                renderHtml("<script>alert('Mark成功');\nhistory.go(-1);</script>");
            }else {
                renderHtml("<script>alert('Mark失败');\nhistory.go(-1);</script>");
            }
        }
    }
    //取消收藏
    public void unmark(){
        final int bookId = getParaToInt("bookid");
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
        }
        Mark mark = new Mark();
        mark.unmark(readerId,bookId);
        if (mark.marked){
            if (mark.success){
                renderHtml("<script>alert('移除成功');\nself.location=document.referrer;</script>");
            } else {
                renderHtml("<script>alert('移除失败');\nself.location=document.referrer;</script>");
            }
        }else {
            renderHtml("<script>alert('你没有码过这本书');\nhistory.go(-1);</script>");
        }
    }
    //借书
    public void borrow(){
        int bookId = getParaToInt("bookid");
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
        }
        Borrow borrow = new Borrow();
        borrow.toBorrow(readerId,bookId);
        if (borrow.borrowed){
            renderHtml("<script>alert('你已经借过这本书了');\nself.location=document.referrer;</script>");
        }else {
            if (borrow.valid) {
                if (borrow.success){
                    renderHtml("<script>alert('借书成功');\nself.location=document.referrer;</script>");
                }else {
                    renderHtml("<script>alert('借书失败');\nhistory.go(-1);</script>");
                }
            }else {
                renderHtml("<script>alert('你的账号不可用，请联系管理员充值押金和余额');\nself.location=document.referrer;</script>");
            }
        }
    }
    // 还书
    public void back(){
        int bookId = getParaToInt("bookid");
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
        }
        Back back = new Back();
        back.toBack(readerId,bookId);
        if (back.borrowed){
            if (back.success){
                renderHtml("<script>alert('归还成功');\nself.location=document.referrer;</script>");
            }else {
                renderHtml("<script>alert('归还失败');\nself.location=document.referrer;</script>");
            }
        }else {
            renderHtml("<script>alert('你没有借过这本书');\nself.location=document.referrer;</script>");
        }
    }
    //搜索
    public void searcher(){
        String keyword = getPara("word");
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
            ShareMethod.setKeyword(keyword);
            render("searcher.html");
        }
    }
    //全部书架
    public void bookshelfs(){
        render("bookshelfs.html");
    }
    //书架
    public void shelf(){
        String type = getPara("type");
        ShareMethod.setType(type);
        render("shelf.html");
    }
    //我借的
    public void Iborrowed(){
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
            ShareMethod.setReaderId(readerId);
            render("Iborrowed.html");
        }
    }
    //我想看
    public void Imarked(){
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
            ShareMethod.setReaderId(readerId);
            render("Imarked.html");
        }

    }
    //我看过
    public void IreadIt(){
        String readerIdstr = getSessionAttr("readerId");
        int readerId = 1;
        if(readerIdstr==null){
            renderHtml("<script>window.location.replace(\"login\");</script>");
        }else {
            readerId = Integer.parseInt(readerIdstr);
            ShareMethod.setReaderId(readerId);
            render("IreadIt.html");
        }
    }
}
