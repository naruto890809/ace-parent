package com.ace.framework.util;

import java.io.*;

/**
 * 文件实用类
 * 
 * @author LIU Fangran
 * 
 */
public class FileUtil {
	/**
	 * 拼接文件
	 * 
	 * @param output
	 *            输出的文件
	 * @param inputFiles
	 *            输入的文件列表
	 * @throws java.io.IOException
	 *             发生错误时抛出
	 */
	public static void concatFile(File output, File... inputFiles)
			throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(output);
			byte[] buffer = new byte[4096];
			for (File file : inputFiles) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					int read = 0;
					while ((read = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, read);
					}
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * Delete file (file, directory, and files under directory).
	 * 
	 * @param file
	 *            file/directory to be deleted.
	 */
	public static void deleteFile(File file) {
		if (file == null) {
			return;
		}

		try {
			if (file.getAbsolutePath().equals("/")) {
				return; // in case the disaster
			}

			if (file.isDirectory()) {
				File[] subFiles = file.listFiles();
				for (File subFile : subFiles) {
					deleteFile(subFile); // ensure the delete.
				}
			}

			file.delete();

		} catch (Exception e) {
			// Catch any exception.
			e.printStackTrace();
		}
	}
}
