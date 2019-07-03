package com.ace.framework.util.common;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

/**
 * todo 带测试使用
 */
@SuppressWarnings("restriction")
	public class PicCompression {

		private PicCompression(){

		}
		/**
		* 压缩图片方法
		* 
		* @param oldFile 将要压缩的图片
		* @param width 压缩宽
		* @param height 压缩高
		* @param quality 压缩清晰度 <b>建议为1.0</b>
		* @param percentage 是否等比压缩 若true宽高比率将将自动调整
		* @author slzs
		* @return 如果处理正确返回压缩后的文件名 null则参数可能有误
		*/
		public static boolean doCompress(File oldFile,File newFile, int width, int height, float quality, boolean percentage) {
           String fileName=oldFile.getName();
           String ext=fileName.substring(fileName.lastIndexOf(".")+1);
		   if (oldFile != null && width > 0 && height > 0) {
		     Image srcFile=null;
		    try {
		     File file = oldFile;
		     // 文件不存在
		     if (!file.exists()) {
		      return false;
		     }
		     /*读取图片信息*/
		     srcFile = ImageIO.read(file);
		     int new_w = width;
		     int new_h = height;
		     if (percentage) {
		      // 为等比缩放计算输出的图片宽度及高度
		      double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
		      double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
		      double rate = rate1 > rate2 ? rate1 : rate2;
		      new_w = (int) (((double) srcFile.getWidth(null)) / rate);
		      new_h = (int) (((double) srcFile.getHeight(null)) / rate);
		     }
		     /* 宽高设定*/
		     BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		     tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
		     /*压缩后的文件名 
		     String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
		     newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());*/
		     /*压缩之后临时存放位置*/
		     FileOutputStream out = new FileOutputStream(newFile);
		    /* JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		     JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
		     *//* 压缩质量 *//*
		     jep.setQuality(quality, true);
		     encoder.encode(tag, jep);*/

            JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix(ext).next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            imageWriter.setOutput(ios);
            // and metadata
            IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(tag), null);

            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);

            imageWriter.write(imageMetaData,new IIOImage(tag, null, null), null);
            ios.close();
            imageWriter.dispose();

		     out.close();
		    } catch (FileNotFoundException e) {
		     e.printStackTrace();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }finally{
		    	if(srcFile!=null){
		    		srcFile.flush();
		    	}
		    }
		    return true;
		   } else {
		    return false;
		   }
		}
	}
