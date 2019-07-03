package com.ace.app.cms.util;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成工具
 * @author WuZhiWei
 * @since 2015-11-24 15:29:04
 */
public class SecurityCodeUtils {
    private static final Logger logger = Logger.getLogger(SecurityCodeUtils.class);
    static {
        ImageIO.setUseCache(false);
    }

    public static void generateSecCode(String code,int width,int height) {
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);


            ServletOutputStream out = response.getOutputStream();
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 设定背景色
            g.setColor(randomColor(200, 250));
            g.fillRect(0, 0, width, height);

            // 设定字体
            Font mFont = new Font(null, Font.BOLD, 23);// 设置字体
            g.setFont(mFont);

            // 画边框
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, width - 1, height - 1);
            // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
            g.setColor(randomColor(160, 200));
            // 生成随机类
            Random random = new Random();
            for (int i = 0; i < 155; i++) {
                int x2 = random.nextInt(width);
                int y2 = random.nextInt(height);
                int x3 = random.nextInt(12);
                int y3 = random.nextInt(12);
                g.drawLine(x2, y2, x2 + x3, y2 + y3);
            }

            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));

            g.drawString(code, 34, 28);

            // 图象生效
            g.dispose();
            // 输出图象到页面
//			ImageIO.write(image, "JPEG", out);
            /*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
            encoder.encode(image);*/

            //兼容jdk 1.7
            JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("JPEG").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(response.getOutputStream());
            imageWriter.setOutput(ios);
            IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
            // new Write and clean up
            imageWriter.write(imageMetaData,new IIOImage(image, null, null), null);
            ios.close();
            imageWriter.dispose();
            out.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static final String[] securityCodes = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
            , "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            , "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String randomCode(int length) {
        StringBuilder builder = new StringBuilder();
        int size = securityCodes.length;
        for (int i = 0; i < length; i++) {
            double rdm = Math.random();
            int index = (int) (rdm * size);
            builder.append(securityCodes[index]);
        }
        return builder.toString();
    }


    private static Color randomColor(int fc, int bc) { // 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
