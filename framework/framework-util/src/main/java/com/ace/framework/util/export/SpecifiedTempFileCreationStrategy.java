package com.ace.framework.util.export;

import org.apache.poi.util.TempFileCreationStrategy;

import java.io.File;
import java.io.IOException;

/**
 * 这个类用于指定当生成Excel 20007时，poi内部生成临时文件的地址
 * （poi内部默认使用系统临时目录，在tomcat上则为tomcat目录下的temp文件目录，因为权限等问题可能会创建临时文件失败而导致报错，所以需要指定临时文件的地址）
 */
public class SpecifiedTempFileCreationStrategy implements TempFileCreationStrategy {

    //临时文件目录
    private File tempDir;

    /**
     * 默认的构造方法
     */
    public SpecifiedTempFileCreationStrategy(){
        this(null);
    }

    /**
     * 指定文件目录的构造方法
     *
     * @param tempDir 临时文件目录
     */
    public SpecifiedTempFileCreationStrategy(File tempDir) {
        this.tempDir = tempDir;
    }

    @Override
    public File createTempFile(String prefix, String suffix) throws IOException {
        // Identify and create our temp dir, if needed
        if (tempDir == null){
            String tempDirPath = ExcelExportUtil.getTempDirPath();
            tempDir = new File(tempDirPath);
        }

        // Generate a unique new filename
        return File.createTempFile(prefix, suffix, tempDir);
    }
}
