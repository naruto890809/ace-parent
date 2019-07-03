package com.ace.framework.util.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class ExcelUtil {

	public static JSONArray getJsonArr(ExcelUtilConfig config) throws IOException{
		JSONArray arr = new JSONArray();
		File file = new File(config.getFilePath());
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		try(HSSFWorkbook excel = new HSSFWorkbook(new FileInputStream(file))) {
			HSSFSheet sheet = excel.getSheetAt(0);
			int start = config.getStartRow();
			int end = config.getEndRow()-start >config.getMaxResult()?start+config.getMaxResult()-1:config.getEndRow();
			int lastRowNum = sheet.getLastRowNum();
			for(int i=start;i<=end&&i<=lastRowNum;i++){
				HSSFRow row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				Iterator<Entry<String, Integer>> it = config.getParamCellMap().entrySet().iterator();
				JSONObject obj = new JSONObject();
				boolean isEmpty = true;
				while(it.hasNext()){
					Entry<String, Integer> entry = it.next();
					HSSFCell cell = row.getCell(entry.getValue());
					if(cell==null){
						//isEmpty = true;
						continue;
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = cell.getStringCellValue();
					if(!StringUtil.isNull(value)){
						isEmpty = false;
					}
					obj.put(entry.getKey(),value);
				}
				if(!isEmpty){
					arr.add(obj);
				}
			}
		}
		return arr;
	}
	
	public static<T> List<T> listBean(ExcelUtilConfig config,Class<T> clazz) throws Exception{
		List<T> list = new ArrayList<>();
		File file = new File(config.getFilePath());
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		try(HSSFWorkbook excel = new HSSFWorkbook(new FileInputStream(file))) {
			HSSFSheet sheet = excel.getSheetAt(0);
			int start = config.getStartRow();
			int end = config.getEndRow()-start >config.getMaxResult()?start+config.getMaxResult()-1:config.getEndRow();
			int lastRowNum = sheet.getLastRowNum();
			for(int i=start;i<=end&&i<=lastRowNum;i++){
				HSSFRow row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				Iterator<Entry<String, Integer>> it = config.getParamCellMap().entrySet().iterator();
				boolean isEmpty = true;
				T t = clazz.newInstance();
				while(it.hasNext()){
					Entry<String, Integer> entry = it.next();
					HSSFCell cell = row.getCell(entry.getValue());
					if(cell==null){
						//isEmpty = true;
						continue;
					}
					Field field = clazz.getDeclaredField(config.getFiledName(entry.getKey()));
					field.setAccessible(true);
					Class<?> typeClass = field.getType();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = cell.getStringCellValue();
					CustomTypeConverter converter = config.getConverter(entry.getKey());
					if(converter!=null){
						Object val = converter.convert(value);
						if(val==null){
							break;
						}
						field.set(t, val);
					}else if(typeClass.equals(String.class)){
						field.set(t, value);
					}else if(typeClass.equals(Long.class)){
						field.set(t, Long.parseLong(value));
					}else if(typeClass.equals(Integer.class)){
						field.set(t, Integer.parseInt(value));
					}else if(typeClass.equals(Boolean.class)){
						field.set(t, Boolean.parseBoolean(value));
					}else if(typeClass.equals(Double.class)){
						field.set(t, Double.parseDouble(value));
					}
					if(!StringUtil.isNull(value)){
						isEmpty = false;
					}
				}
				if(!isEmpty){
					list.add(t);
				}
			}
		}
		return list;
	}
	
	public static ExcelUtilConfig createConfig(HttpServletRequest request,String filePath,List<String> paramName) throws Exception{
		try{
			ExcelUtilConfig config = new ExcelUtilConfig();
			
			Integer startRow = RequestUtil.getIntegerParameter("start",request) - 1;
			Integer endRow = RequestUtil.getIntegerParameter("end",request) - 1;
			Map<String,Integer> paramCellMap = new LinkedHashMap<>();
			for(String key:paramName){
				Integer cellNum = RequestUtil.getIntegerParameter(key,request);
				if(cellNum==null){
					continue;
				}
				paramCellMap.put(key, cellNum-1);
			}
			config.setStartRow(startRow);
			config.setEndRow(endRow);
			config.setParamCellMap(paramCellMap);
			config.setFilePath(filePath);
			return config;
		}catch(Exception e){
			throw e;
		}
	}
	
}
