package com.waterridish.demo.model;
import com.jfinal.plugin.activerecord.Model;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Mark extends Model<Mark>{
    private int mark_num;
    private int book_id;
    private int reader_id;
    private Timestamp mark_date;
    public static final Mark dao = new Mark().dao();
    public boolean marked;
    public boolean success;
    public void toMark(int readerId,int bookId){
        System.out.println(readerId+""+bookId);
        List<Mark> markList = this.find("select * from mark where book_id= "+bookId+" and reader_id = "+readerId);
        if(!markList.isEmpty()){
            this.marked = true;
        } else {
            Date date = new Date();//获得系统时间.
            Timestamp mark_date = new Timestamp(date.getTime());
            boolean t = this.set("mark_date",mark_date).set("book_id",bookId).set("reader_id",readerId).save();
            if (t){
                this.success = true;
            }else {
                this.success = false;
            }
        }
    }
    public void unmark(int readerId,int bookId){
        List<Mark> markList = this.find("select * from mark where book_id= "+bookId+" and reader_id = "+readerId);
        if(!markList.isEmpty()){
            this.marked = true;
            List<Mark> markNumList = this.find("select mark_num from mark where book_id= ? and reader_id = ?",bookId,readerId);
            int markNum = markNumList.get(0).getInt("mark_num");
            boolean t = this.deleteById(markNum);
            if (t){
                this.success = true;
            }else {
                this.success = false;
            }
        } else {
            this.marked = false;
        }
    }


}
