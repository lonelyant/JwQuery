package com.ant.utils;

import com.ant.model.Lesson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ant
 * @Date: 2018/09/29 11:31
 * @Description:
 */
public class PDFUtil {
    //该路径下应该存在模板table.pdf、字体文件font.TTF。并且生成的PDF也会存在这个文件夹下。
    private static final String basePath = "C:/Users/Administrator/Desktop/$RC8URVW/";

    private String xy = "武汉东湖学院（默认）";
    private String banji = "机设1班（默认）";
    private String nianji = "2014";
    private String xingming = "Ant";
    private String xuehao = "2014000000000";
    private List<Lesson> bixiu = new ArrayList<>();
    private List<Lesson> xuanxiu = new ArrayList<>();
    private float A1 = 0.0f;
    private float A2 = 0.0f;
    private float B = 0.0f;
    private float F = 0.0f;

    public PDFUtil(String xy, String banji, String nianji, String xingming, String xuehao, List<Lesson> bixiu, List<Lesson> xuanxiu, float a1, float a2, float b, float f) {
        this.xy = xy;
        this.banji = banji;
        this.nianji = nianji;
        this.xingming = xingming;
        this.xuehao = xuehao;
        this.bixiu = bixiu;
        this.xuanxiu = xuanxiu;
        A1 = a1;
        A2 = a2;
        B = b;
        F = f;
    }

    public PDFUtil() {
    }

    public static void main(String[] args) {
        try {
            new PDFUtil().getpdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String myreplace(String s) {
        s = s.replaceAll("[(（]", "(").replaceAll("[)）]", "）");
        if (s.contains("III")) {
            s = s.replace("III", "Ⅲ");
        } else if (s.contains("II")) {
            s = s.replace("II", "Ⅱ");
        } else if (s.contains("IV")) {
            s = s.replace("IV", "Ⅳ");
        }
        return s;
    }


    public void getpdf() throws Exception{

        //创建一个pdf读入流
        PdfReader reader = new PdfReader(basePath + "table.pdf");
        //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(basePath + "2014040191031.pdf"));

        //设置字体
        BaseFont bf = BaseFont.createFont(basePath + "font.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bfv = BaseFont.createFont(basePath + "font.TTF", BaseFont.IDENTITY_V, BaseFont.EMBEDDED);




        //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
        PdfContentByte over = stamper.getOverContent(1);

        over.beginText();
        //设置字体和大小
        over.setFontAndSize(bf, 6);
        //设置字体颜色
        over.setColorFill(BaseColor.BLACK);


        /******************** 基本信息模块 *********************/

        /*bixiu.add(new Lesson("专业（生产）实习（III）", "2", "97"));
        bixiu.add(new Lesson("计算机辅助设计与制造（CAD/CAM）", "2", "97"));
        bixiu.add(new Lesson("大学物理实验（机电本下）", "2", "100"));
        bixiu.add(new Lesson("大学物理（机电本下）", "2.5", "97"));
        bixiu.add(new Lesson("专业（生产）实习（I）", "2", "97.5"));
        bixiu.add(new Lesson("电工电子技术（I）", "2", "97"));
        bixiu.add(new Lesson("电工电子技术实验I", "2", "97"));
        bixiu.add(new Lesson("理论力学", "2", "100"));
        bixiu.add(new Lesson("中国近现代史纲要", "2", "97"));
        bixiu.add(new Lesson("大学体育(3)", "2", "97"));
        bixiu.add(new Lesson("科技文献检索与写作", "2.5", "97"));
        bixiu.add(new Lesson("计算机三维设计与仿真", "2", "97.5"));
        bixiu.add(new Lesson("Photoshop", "2", "97"));
        bixiu.add(new Lesson("大学生职业发展与就业指导I", "2", "97"));
        bixiu.add(new Lesson("电工电子技术（II）", "2", "97.5"));
        bixiu.add(new Lesson("材料力学", "2", "97"));
        bixiu.add(new Lesson("互换性与技术测量", "2", "97"));
        bixiu.add(new Lesson("机械原理课程设计", "2", "97"));
        bixiu.add(new Lesson("英语（Ⅳ）", "2", "97.5"));
        bixiu.add(new Lesson("电工电子技术实验II", "2", "97"));
        bixiu.add(new Lesson("大学体育(4)", "2", "97"));
        bixiu.add(new Lesson("机械工程基础实验（I）", "2", "97"));
        bixiu.add(new Lesson("毛泽东思想和中国特色社会主义理论体系概论", "2", "97"));
        bixiu.add(new Lesson("机械原理", "1.5", "97"));
        bixiu.add(new Lesson("CAD综合实训", "2", "97"));
        bixiu.add(new Lesson("理论力学", "2", "100"));
        bixiu.add(new Lesson("中国近现代史纲要", "2", "97"));
        bixiu.add(new Lesson("大学体育(3)", "2", "97"));
        bixiu.add(new Lesson("科技文献检索与写作", "2.5", "97"));
        bixiu.add(new Lesson("毛泽东思想和中国特色社会主义理论体系概论", "2", "97"));
        bixiu.add(new Lesson("机械原理", "2.5", "97"));


        xuanxiu.add(new Lesson("机械设计制造及其自动化本科第一", "3", "100"));
        xuanxiu.add(new Lesson("机械设计制造及其自动化本科第一", "3", "100"));
        xuanxiu.add(new Lesson("趣味心理学", "3", "100"));
        xuanxiu.add(new Lesson("趣味心理学", "3", "100"));
        xuanxiu.add(new Lesson("专利技术", "3", "100"));
        xuanxiu.add(new Lesson("专利技术", "3", "100"));*/

        Float temp1 = 0.0f;
        Float temp2 = 0.0f;
        for (Lesson lesson : bixiu) {
            temp1 = temp1 + Float.parseFloat(lesson.getChengji())*Float.parseFloat(lesson.getXuefen());
            temp2 += Float.parseFloat(lesson.getXuefen());
        }
        A1 = temp1/temp2;
        temp1 = temp2 = 0.0f;

        for (Lesson lesson : xuanxiu) {
            temp1 = temp1 + Float.parseFloat(lesson.getChengji())*Float.parseFloat(lesson.getXuefen());
        }
        A2 = temp1 * 0.002f;


        float A = A1+A2;
        float G =0.7f*A+0.15f*B+0.15f*F;
        //学院
        if (xy.length() <= 9) {
            over.setTextMatrix(75, 771);
            over.showText(xy);
        } else {
            over.setTextMatrix(75, 779);
            over.showText(xy.substring(0, 9));
            over.setTextMatrix(75, 771);
            over.showText(xy.substring(9, xy.length()));
        }
        //专业班级
        if (banji.length() <= 10) {
            over.setTextMatrix(185, 771);
            over.showText(banji);
        } else {
            over.setTextMatrix(185, 779);
            over.showText(banji.substring(0, 10));
            over.setTextMatrix(185, 771);
            over.showText(banji.substring(10, banji.length()));
        }
        //年级
        over.setFontAndSize(bf, 10);
        over.setTextMatrix(275, 771);
        over.showText(nianji);

        //姓名
        over.setTextMatrix(340, 771);
        over.showText(xingming);

        //学号
        over.setTextMatrix(430, 771);
        over.showText(xuehao);

        /******************** 课程成绩模块 *********************/
        //必修
        if (bixiu.size() < 13) {

            int i = 0;
            for (Lesson lesson : bixiu) {
                over.setFontAndSize(bfv, 7);
                if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 5.5f);
                over.setTextMatrix(160 + (i * 18.4f), 730);
                over.showText(myreplace(lesson.getName()));

                over.setFontAndSize(bf, 8);
                over.setTextMatrix(157 + (i * 18.4f), 590);
                if (lesson.getXuefen().length() > 1) over.setTextMatrix(154 + (i * 18.4f), 590);
                over.showText(lesson.getXuefen());

                over.setTextMatrix(157 + (i * 18.4f), 570);
                if (lesson.getChengji().length() > 2) {
                    over.setFontAndSize(bf, 7);
                    over.setTextMatrix(154 + (i * 18.4f), 570);

                }
                over.showText(lesson.getChengji());

                i++;
            }
        } else if (bixiu.size() <= 24) {
            int i = 0;
            for (Lesson lesson : bixiu) {
                if (i < 12) {
                    over.setFontAndSize(bfv, 6);
                    if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 5.5f);
                    over.setTextMatrix(156 + (i * 18.4f), 735);
                    over.showText(myreplace(lesson.getName()));

                    over.setFontAndSize(bf, 7);
                    over.setTextMatrix(154 + (i * 18.4f), 595);
                    if (lesson.getXuefen().length() > 1) over.setTextMatrix(151 + (i * 18.4f), 595);
                    over.showText(lesson.getXuefen());

                    over.setTextMatrix(154 + (i * 18.4f), 575);
                    if (lesson.getChengji().length() > 2) {
                        over.setFontAndSize(bf, 6);
                        over.setTextMatrix(151 + (i * 18.4f), 575);

                    }
                    over.showText(lesson.getChengji());
                } else {
                    int j = i - 12;
                    over.setFontAndSize(bfv, 6);
                    if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 5.5f);
                    over.setTextMatrix(164 + (j * 18.4f), 730);
                    over.showText(myreplace(lesson.getName()));

                    over.setFontAndSize(bf, 7);
                    over.setTextMatrix(160 + (j * 18.4f), 588);
                    if (lesson.getXuefen().length() > 1) over.setTextMatrix(157 + (j * 18.4f), 588);
                    over.showText(lesson.getXuefen());

                    over.setTextMatrix(160 + (j * 18.4f), 568);
                    if (lesson.getChengji().length() > 2) {
                        over.setFontAndSize(bf, 6);
                        over.setTextMatrix(157 + (j * 18.4f), 568);

                    }
                    over.showText(lesson.getChengji());
                }
                i++;
            }
        } else if (bixiu.size() <= 36) {

            int size = bixiu.size() % 12;
            if (bixiu.size() == 36) size = 12;
            int i = 0;
            int j = 0;
            for (Lesson lesson : bixiu) {
                if (i < size * 3) {
                    //填充三个
                    over.setFontAndSize(bfv, 5);
                    if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 4.5f);
                    over.setTextMatrix(154 + (i * 6.1f), 735);
                    over.showText(myreplace(lesson.getName()));
                    over.setFontAndSize(bf, 6);
                    switch (i % 3) {
                        case 0:
                            over.setTextMatrix(152 + (i * 6.1f), 600);
                            over.showText(lesson.getXuefen());

                            over.setTextMatrix(152 + (i * 6.1f), 577);
                            over.showText(lesson.getChengji());
                            break;
                        case 1:
                            over.setTextMatrix(150 + (i * 6.1f), 592);
                            over.showText(lesson.getXuefen());

                            over.setTextMatrix(148 + (i * 6.1f), 569);
                            over.showText(lesson.getChengji());
                            break;
                        case 2:
                            over.setTextMatrix(148 + (i * 6.1f), 585);
                            over.showText(lesson.getXuefen());

                            over.setTextMatrix(145 + (i * 6.1f), 563);
                            over.showText(lesson.getChengji());
                            break;
                    }

                } else {
                    //填充两个
                    over.setFontAndSize(bfv, 6);
                    if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 5.5f);
                    over.setTextMatrix(156 + (size * 18.3f) + j++ * 9.1f, 735);
                    over.showText(myreplace(lesson.getName()));
                    over.setFontAndSize(bf, 7);
                    switch (i % 2) {
                        case 1:
                            over.setTextMatrix(96 + (i * 8.9f), 595);
                            over.showText(lesson.getXuefen());

                            over.setTextMatrix(95 + (i * 8.9f), 572);
                            over.showText(lesson.getChengji());
                            break;
                        case 0:
                            over.setTextMatrix(90 + (i * 8.9f), 587);
                            over.showText(lesson.getXuefen());

                            over.setTextMatrix(90 + (i * 8.9f), 564);
                            over.showText(lesson.getChengji());
                            break;
                    }
                }
                i++;
            }


        }


        //选修

        if (xuanxiu.size() > 6) {
            over.setFontAndSize(bf, 8);
            over.setTextMatrix(375, 700);
            over.showText("没想到还有选修门数大于6的");
            over.setTextMatrix(375, 690);
            over.showText("这种情况我没有适配");
            over.setTextMatrix(375, 680);
            over.showText("请联系我 qq 891575283");
        } else {
            over.setFontAndSize(bfv, 7);
            int i = 0;
            for (Lesson lesson : xuanxiu) {
                over.setFontAndSize(bfv, 7);
                if (lesson.getName().length() > 17) over.setFontAndSize(bfv, 5.5f);
                over.setTextMatrix(379 + (i * 18.4f), 730);
                over.showText(myreplace(lesson.getName()));

                over.setFontAndSize(bf, 8);
                over.setTextMatrix(376 + (i * 18.4f), 590);
                if (lesson.getXuefen().length() > 1) over.setTextMatrix(373 + (i * 18.4f), 590);
                over.showText(lesson.getXuefen());

                over.setTextMatrix(376 + (i * 18.4f), 570);
                if (lesson.getChengji().length() > 2) {
                    over.setFontAndSize(bf, 7);
                    over.setTextMatrix(373 + (i * 18.4f), 570);

                }
                over.showText(lesson.getChengji());
                i++;
            }
        }


        /******************** 三个 A *********************/
        // A1
        over.setFontAndSize(bf, 10);
        over.setTextMatrix(278, 548);
        over.showText(String.format("%.2f",A1));

        // A2
        over.setFontAndSize(bf, 9);
        over.setTextMatrix(460, 548);
        over.showText(String.format("%.2f",A2));

        // A
        over.setFontAndSize(bf, 14);
        over.setTextMatrix(490, 665);
        over.showText(String.format("%.2f",A));

        /******************** B *********************/
        over.setFontAndSize(bf, 14);
        over.setTextMatrix(489, 420);
        over.showText(B+"");


        /******************** F *********************/
        over.setFontAndSize(bf, 14);
        over.setTextMatrix(489, 250);
        over.showText(F+"");

        /******************** G *********************/
        over.setFontAndSize(bf, 18);
        over.setTextMatrix(209, 110);
        over.showText(String.format("%.2f",G));


        over.endText();
        stamper.close();
    }


    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getNianji() {
        return nianji;
    }

    public void setNianji(String nianji) {
        this.nianji = nianji;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public List<Lesson> getBixiu() {
        return bixiu;
    }

    public void setBixiu(List<Lesson> bixiu) {
        this.bixiu = bixiu;
    }

    public List<Lesson> getXuanxiu() {
        return xuanxiu;
    }

    public void setXuanxiu(List<Lesson> xuanxiu) {
        this.xuanxiu = xuanxiu;
    }

    public float getA1() {
        return A1;
    }

    public void setA1(float a1) {
        A1 = a1;
    }

    public float getA2() {
        return A2;
    }

    public void setA2(float a2) {
        A2 = a2;
    }

    public float getB() {
        return B;
    }

    public void setB(float b) {
        B = b;
    }

    public float getF() {
        return F;
    }

    public void setF(float f) {
        F = f;
    }
}
