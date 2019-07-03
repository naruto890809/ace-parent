package com.ace.framework.util.common;

import java.util.HashMap;
import java.util.Map;

public class ExcelUtilConfig {

	private int startRow;
	
	private int endRow;
	
	private Map<String,Integer> paramCellMap;
	
	private Map<String,String> paramFieldMap;
	
	private Map<String,CustomTypeConverter> converterMap = new HashMap<String, CustomTypeConverter>();
	
	private String filePath;
	
	private Integer maxResult;
	
	public int getStartRow() {
		if(startRow<0){
			startRow = 1;
		}
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		if(endRow<startRow){
			endRow = getStartRow();
		}
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public Map<String, Integer> getParamCellMap() {
		return paramCellMap;
	}

	public void setParamCellMap(Map<String, Integer> paramCellMap) {
		this.paramCellMap = paramCellMap;
	}

	public Map<String, String> getParamFieldMap() {
		return paramFieldMap;
	}

	public void setParamFieldMap(Map<String, String> paramFieldMap) {
		this.paramFieldMap = paramFieldMap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFiledName(String param){
		String filed = paramFieldMap.get(param);
		if(StringUtil.isNull(filed)){
			filed = param;
		}
		return filed;
	}

	public Integer getMaxResult() {
		if(maxResult==null){
			return getEndRow() - getStartRow() + 1;
		}
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
	
	public CustomTypeConverter getConverter(String param){
		return converterMap.get(param);
	}
	
	public void putConvert(String param,CustomTypeConverter converter){
		converterMap.put(param, converter);
	}
}
