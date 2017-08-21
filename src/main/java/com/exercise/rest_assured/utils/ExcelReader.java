package com.exercise.rest_assured.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private HashMap<String, String> map = null;
	
	public ExcelReader() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param path 文件路径
	 * @param sheetName sheet表名
	 * @param caseName 用例名
	 * */
	public ExcelReader(String path,String sheetName,String caseName) {
		// TODO Auto-generated constructor stub
		map = mapFromSheet(path, sheetName, caseName);
	}
	
	public HashMap<String, String> getRowMap(){
		return map;
	}
	
	/**
	 * 创建工作簿对象
	 * @param filePath Excel文件路径
	 * @return
	 * @throws IOException
	 */
	public static final Workbook setWorkbook(String filePath) throws IOException {
		Workbook wb = null;
		if(StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("参数错误!!!") ;
		}
		if(filePath.trim().toLowerCase().endsWith("xls")) {
			wb = new HSSFWorkbook(new FileInputStream(filePath));
			return wb ;
		} else if(filePath.trim().toLowerCase().endsWith("xlsx")) {
			wb = new XSSFWorkbook(new FileInputStream(filePath));
			return wb ;
		} else {
			throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!") ;
		}
	}

	/**
	 * 获取指定行的数据
	 * @param fileName 文件名(全路径)
	 * @param sheetName sheet表名
	 * @param caseName 用例名
	 * @return HashMap<key,value> key:首行cell的值，value:指定行cell的值
	 * */
	public HashMap<String, String> mapFromSheet(String fileName, String sheetName, String caseName){
		
		Workbook workbook = null;
		Sheet sheet = null;
		int rowNum = 0;
		
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			workbook = setWorkbook(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sheet = workbook.getSheet(sheetName);
		
		for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum()+1; i++) {
			
			Row row = sheet.getRow(i);
			if (row.getLastCellNum()==0) {
				continue;
			}

			if (row.getCell(0).toString().equals(caseName)) {
				rowNum = i;
				break;
			}
		}
		
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			if (sheet.getRow(0).getCell(i).toString()==null) {
				throw new IllegalArgumentException("参数错误："+fileName+"表："+sheetName+"第1行第"+i+"列的值为空!!!") ;
			}
			
			if (sheet.getRow(rowNum).getCell(i)==null) {
				map.put(sheet.getRow(0).getCell(i).toString(), null);
			} else {
				map.put(sheet.getRow(0).getCell(i).toString(), sheet.getRow(rowNum).getCell(i).toString());
			}
			
		}
		
		return map;
	}
}
