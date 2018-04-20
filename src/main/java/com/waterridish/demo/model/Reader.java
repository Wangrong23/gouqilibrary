package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Model;
import java.sql.Timestamp;

public class Reader extends Model<Reader> {
    private int reader_id;
    private String reader_password;
    private String reader_name;
    private String reader_sex;
    private String reader_birthyear;
    private String bookcard_num;
    private String contact;
    private String document_num;
    private float over_money;
    private String deposit;
    private String valid;
    private Timestamp regist_date;


    public static final Reader dao = new Reader().dao();





    //bookcard_num     | varchar(20) | YES  |     | NULL    |                |
    //| contact          | varchar(20) | NO   |     | NULL    |                |
    //| document_num     | varchar(20) | NO   |     | NULL    |                |
    //| regist_date      | datetime    | NO   |     | NULL    |                |
    //| over_money       | float(6,2)  | NO   |     | NULL    |                |
    //| deposit          | char(1)     | NO   |     | NULL    |                |
    //| valid            | char(1)
}
