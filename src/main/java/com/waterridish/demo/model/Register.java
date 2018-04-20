package com.waterridish.demo.model;

import com.jfinal.plugin.activerecord.Model;
import com.waterridish.demo.model.Reader;

import java.sql.Timestamp;
import java.util.Date;

public class Register {
    public boolean regist;
    public String bookcardNum;
    public Register(Model<Reader> reader){
        String deposit = "n";
        String valid = "n";
        Date date = new Date();//获得系统时间.
        Timestamp regist_date = new Timestamp(date.getTime());
        //数据插入操作
        //todo 生成借书卡号
        this.bookcardNum = "";
        this.regist = reader.set("bookcard_num",bookcardNum).set("regist_date",regist_date).set("over_money",0).set("deposit",deposit).set("valid",valid).save();

    }
}
