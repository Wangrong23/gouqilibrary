package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Back extends Model<Back> {
    private int bac_num;
    private int book_id;
    private int reader_id;
    private Timestamp back_date;
    public boolean borrowed;
    public boolean success;
    public static final Back dao = new Back().dao();

    public void toBack(int readerId,int bookId){
        List<Borrow> borrowList = Borrow.dao.find("select * from borrow where "+"book_id = ? and reader_id = ?",bookId,readerId);
        if (!borrowList.isEmpty()){
            this.borrowed = true;
            Date date = new Date();
            Timestamp back_date = new Timestamp(date.getTime());
            final int readerId1 = readerId;
            final int bookId1 = bookId;
            final Timestamp back_date1 = back_date;
            boolean d = Borrow.dao.deleteById(borrowList.get(0).getInt("borrow_num"));
            if (d){
                boolean succeed = Db.tx(new IAtom(){
                    public boolean run() throws SQLException {
                        int count = Db.update("update over_num set over_num = over_num + ? where book_id = ?", 1, bookId1);
                        Back back = new Back();
                        boolean t = back.set("back_date",back_date1).set("book_id",bookId1).set("reader_id",readerId1).save();

                        return count == 1;
                    }});
                if (succeed){
                    this.success = true;
                }else {
                    this.success = false;
                }
            }else {
                this.success = false;
            }

        }else {
            this.borrowed = false;
        }
    }
}
