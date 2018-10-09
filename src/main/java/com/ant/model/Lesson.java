package com.ant.model;

/**
 * @author: Ant
 * @Date: 2018/09/29 12:46
 * @Description: 课程成绩学分实体类
 */
public class Lesson {
    private String name;//课程名
    private String xuefen;//学分
    private String chengji;//成绩

    public Lesson(){

    }

    public Lesson(String name, String xuefen, String chengji) {
        this.name = name;
        this.xuefen = xuefen;
        this.chengji = chengji;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXuefen() {
        return xuefen;
    }

    public void setXuefen(String xuefen) {
        this.xuefen = xuefen;
    }

    public String getChengji() {
        return chengji;
    }

    public void setChengji(String chengji) {
        this.chengji = chengji;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", xuefen='" + xuefen + '\'' +
                ", chengji='" + chengji + '\'' +
                '}';
    }
}
