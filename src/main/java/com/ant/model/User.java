package com.ant.model;

import org.apache.http.client.CookieStore;

/**
 * @author: Ant
 * @Date: 2018/09/07 09:10
 * @Description:
 */
public class User {
    private String account;
    private String password;
    private String realname;
    private CookieStore cookieStore;
    private String gender;  //性别
    private int grade;   //年级 2014  2015等
    private String major;   //专业
    private String classname;   //班级名
    private String birth;   //生日


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", cookieStore=" + cookieStore +
                ", gender='" + gender + '\'' +
                ", grade=" + grade +
                ", major='" + major + '\'' +
                ", classname='" + classname + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
