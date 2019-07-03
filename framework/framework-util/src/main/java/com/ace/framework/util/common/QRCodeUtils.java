package com.ace.framework.util.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;


public class QRCodeUtils {

	private QRCodeUtils(){

	}
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	
	/**
	 * 
	 * @param content 二维码内容
	 * @param logoUrl  内嵌的logo位置
	 * @param qrcodeSize 二维码长宽
	 * @param color 二维码颜色 FFFFFF
	 * @param outImage 生成的二维码文件
	 */
	public static void encode(String content,String logoUrl,int qrcodeSize,String color,String outImage){
		try {
			BufferedImage image = createImage(content, logoUrl, true, qrcodeSize, color);
			File file = new File(outImage);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			ImageIO.write(image, FORMAT_NAME, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void encode(String content,String logoUrl,int qrcodeSize,String color,File outImage){
		try {
			BufferedImage image = createImage(content, logoUrl, true, qrcodeSize, color);
			if(!outImage.getParentFile().exists()){
				outImage.getParentFile().mkdirs();
			}
			ImageIO.write(image, FORMAT_NAME, outImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage createImage(String content, String logoUrl,
			boolean needCompress,int qrcodeSize,String color) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 + Integer.parseInt(color, 16)
						: 0xFFFFFFFF);
			}
		}
		if (logoUrl == null || "".equals(logoUrl)) {
			return image;
		}
		// 插入图片
		QRCodeUtils.insertImage(image, logoUrl, needCompress,qrcodeSize);
		return image;
	}
	
	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param logoUrl
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String logoUrl,
			boolean needCompress,int qrcodeSize) throws Exception {
		int codeWidth = qrcodeSize/5;
		
		Image src = ImageIO.read(new URL(logoUrl).openStream());
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > codeWidth) {
				width = codeWidth;
			}
			if (height > codeWidth) {
				height = codeWidth;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qrcodeSize - width) / 2;
		int y = (qrcodeSize - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
}
