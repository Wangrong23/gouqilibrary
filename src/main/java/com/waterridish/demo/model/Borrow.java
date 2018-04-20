package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Borrow extends Model<Borrow> {
    private int borrow_num;
    private int book_id;
    private int reader_id;
    private Timestamp borrow_date;
    private Timestamp end_time;
    public boolean borrowed;
    public boolean valid;
    public boolean success;
    public static final Borrow dao = new Borrow().dao();

    public void toBorrow(int readerId,int bookId){
        //todo 从mark里删除
        List<Borrow> borrowList = this.find("select * from borrow where book_id = "+bookId+" and reader_id = "+readerId);
        List<Reader> readerList = Reader.dao.find("select * from reader where valid = 'y' and reader_id = "+readerId);
        List<Mark> markList = Mark.dao.find("select * from mark where book_id = "+bookId+" and reader_id = "+readerId);
//        boolean r = false;
        if (!borrowList.isEmpty()){
            this.borrowed=true;
        }else {
            //检测帐号可用,r为true表示可用
            if (!readerList.isEmpty()){
                this.valid = true;
                //查询余额overMoney
                float overMoney = readerList.get(0).get("over_money");
                int endDays = (int) (overMoney/0.2f);
                Date date = new Date();//获得系统时间.
                final Timestamp borrow_date = new Timestamp(date.getTime());
                //todo计算到期时间
                long endTime = date.getTime()+endDays*86400000;
                Date d = new Date(endTime);
                final Timestamp end_time = new Timestamp(d.getTime());
                final int readerId1 = readerId;
                final int bookId1 = bookId;

                //图书数量减一,succeed为true表示成功
                boolean succeed = Db.tx(new IAtom(){
                    public boolean run() throws SQLException {
                    int count = Db.update("update over_num set over_num = over_num - ? where book_id = ?", 1, bookId1);
                    Borrow borrow = new Borrow();
                    boolean t = borrow.set("borrow_date",borrow_date).set("end_time",end_time).set("book_id",bookId1).set("reader_id",readerId1).save();
                    return count == 1;
                }});
                if (succeed){
                    success = true;
                    if (!markList.isEmpty()){
                        int markNum = markList.get(0).getInt("mark_num");
                        boolean dm = Mark.dao.deleteById(markNum);
                        if (dm){
                            success = true;
                        }else {
                            success = false;
                        }
                    }
                }else {
                    success = false;
                }
            }else {
                valid = false;

            }
        }
    }

}
