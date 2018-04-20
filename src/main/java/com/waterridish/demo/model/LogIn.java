package com.waterridish.demo.model;

import com.waterridish.demo.model.Reader;

import java.util.List;

public class LogIn {
    public boolean exist;
    public boolean pass;
    public String readerId;
    public LogIn(String bookcardNum,String password){
        List<Reader> reader = Reader.dao.find("select * from reader where bookcard_num= '"+bookcardNum+"' ");
        if (!reader.isEmpty()){
            this.exist = true;
            String reader_password = reader.get(0).get("reader_password");
            if (reader_password.equals(password)){
                this.readerId = reader.get(0).get("reader_id").toString();
                this.pass = true;
            }else {
                this.pass = false;
            }
        }else {
            this.exist = false;
        }
    }
}
