package com.ace.framework.util.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 *
 * @author WuZhiWei@HF
 * @since 2015-4-28
 */
public class FileZipUtil {

    private static Log log = LogFactory.getLog(FileZipUtil.class);

    /**
     * 压缩文件（压缩成zip格式的文件）
     *
     * @param srcfile 需要压缩的文件列表
     * @param zipFile 压缩后的zip文件
     * @return true 或 false。true表示执行成功，false表示失败。
     */
    public static boolean compressToZipFiles(File[] srcfile, File zipFile) {
        boolean success = true;
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (Exception e) {
            success = false;
            log.error("Exception occurred while compress zip files !", e);
        }
        return success;
    }
}
