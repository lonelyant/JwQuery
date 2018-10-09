package com.ant.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Ant
 * @Date: 2018/09/03 17:33
 * @Description:
 */
public class VCodeOCR {

    private static Map<BufferedImage, String> trainMap = null;

    public static int isBlue(int colorInt) {
        Color color = new Color(colorInt);
        int rgb = color.getRed() + color.getGreen() + color.getBlue();
        if (rgb == 153) {
            return 1;
        }
        return 0;
    }

    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }

    /**
     * 去除背景及干扰点，正方教务系统验证码主体为蓝色，所以将蓝色像素转黑，其余像素转白，就能得到白底黑字的验证码了。
     * @param picFile
     * @return
     * @throws Exception
     */
    public static BufferedImage removeBackgroud(String picFile)
            throws Exception {
        BufferedImage img = ImageIO.read(new File(picFile));
        img = img.getSubimage(5, 1, img.getWidth() - 5, img.getHeight() - 2);
        img = img.getSubimage(0, 0, 50, img.getHeight());
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isBlue(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return img;
    }

    /**
     * 切割验证码
     * @param img
     * @return
     * @throws Exception
     */
    public static List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth() / 4;
        int height = img.getHeight();
        subImgs.add(img.getSubimage(0, 0, width, height));
        subImgs.add(img.getSubimage(width, 0, width, height));
        subImgs.add(img.getSubimage(width * 2, 0, width, height));
        subImgs.add(img.getSubimage(width * 3, 0, width, height));
        return subImgs;
    }

    /**
     * 载入训练好的字库
     *
     * @return
     * @throws Exception
     */
    public static Map<BufferedImage, String> loadTrainData() throws Exception {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            File dir = new File("C:\\Users\\Administrator\\Desktop\\image\\train");
//            File dir = new File("/root/Ant/JW/image/train");
            File[] files = dir.listFiles();
            for (File file : files) {
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        return trainMap;
    }

    /**
     * 依次对比训练字库，得到相同像素最多的取文件名首字符
     *
     * @param img
     * @param map
     * @return
     */
    public static String getSingleCharOcr(BufferedImage img,
                                          Map<BufferedImage, String> map) {
        String result = "#";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            if (Math.abs(bi.getWidth() - width) > 2)
                continue;
            int widthmin = width < bi.getWidth() ? width : bi.getWidth();
            int heightmin = height < bi.getHeight() ? height : bi.getHeight();
            Label1:
            for (int x = 0; x < widthmin; ++x) {
                for (int y = 0; y < heightmin; ++y) {
                    if (isBlack(img.getRGB(x, y)) != isBlack(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

    /**
     * 验证码识别
     * @param file 要验证的验证码本地路径
     * @return
     * @throws Exception
     */
    public static String ocr(String file) throws Exception {
        BufferedImage img = removeBackgroud(file);
        List<BufferedImage> listImg = splitImage(img);
        Map<BufferedImage, String> map = loadTrainData();
        String result = "";
        for (BufferedImage bi : listImg) {
            result += getSingleCharOcr(bi, map);
        }
        return result;
    }
}
