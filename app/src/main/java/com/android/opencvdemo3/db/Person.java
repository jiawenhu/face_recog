package com.android.opencvdemo3.db;

import org.litepal.crud.DataSupport;

/**
 * Created by wenjia on 2018/4/15.
 * 用于存储被登记的人脸信息
 */

public class Person extends DataSupport {

    private int id;

    private String personName;//人名

    private int personCode;//人脸代号

    private String gender;//性别

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonCode() {
        return personCode;
    }

    public void setPersonCode(int personCode) {
        this.personCode = personCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
