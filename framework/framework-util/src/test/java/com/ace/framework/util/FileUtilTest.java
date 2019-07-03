package com.ace.framework.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FileUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testConcatFile() throws IOException {
		String dir = "test/som/util/";
		String outputFileName = dir + "C.txt";
		File output = new File(outputFileName);
		File input1 = new File(dir + "A.txt");
		File input2 = new File(dir + "B.txt");

		FileUtil.concatFile(output, input1, input2);
		assertEquals("hello world", StreamUtil
				.inputStreamToString(new FileInputStream(outputFileName)));
	}
	
	@Test
	@Ignore
	public void testConcatFile2() throws IOException {
		String dir = "E:\\Tool\\Desk\\Driver\\";
		String outputFileName = dir + "touch.zip";
		File output = new File(outputFileName);
		File input1 = new File(dir + "Windows7_Portege_R700_SynapticsTouchPadDriver_V15.0.12.part1.zip");
		File input2 = new File(dir + "Windows7_Portege_R700_SynapticsTouchPadDriver_V15.0.12.part2.zip");

		FileUtil.concatFile(output, input1, input2);
	}
	@Ignore
	@Test
	public void testCPU0() {
		while (true) {
			
		}
	}


}
