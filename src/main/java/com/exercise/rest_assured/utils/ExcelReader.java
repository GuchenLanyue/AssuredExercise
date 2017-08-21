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
import org.junit.Test;

public class ExcelReader {
	
	static String filePath = null;
	static String sheetName = null;
	static String caseName = null;
	
	static Workbook wb = null;
	static Sheet sheet = null;
	
	static HashMap<String, String> map = new HashMap<String, String>();
	
	public ExcelReader() {
		// TODO Auto-generated constructor stub
	}
	
	public ExcelReader(String path,String sheetName,String caseName) {
		// TODO Auto-generated constructor stub
		setPath(path);
		setSheetName(sheetName);
		setCaseName(caseName);
	}
	
	/**
	 * 设置Excel文件路径
	 * @param excelFilePath Excel文件路径
	 * */
	public void setPath(String excelFilePath){
		this.filePath = excelFilePath;
	}
	
	/**
	 * 设置Sheet名称
	 * @param sheetName Excel sheet表名称
	 * */
	public void setSheetName(String sheetName){
		this.sheetName = sheetName;
	}
	
	/**
	 * 设置用例名称
	 * @param caseName 用例名称
	 * */
	public void setCaseName(String caseName){
		this.caseName = caseName;
	}
	
	/**
	 * 创建工作簿对象
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static final Workbook setWorkbook(String filePath) throws IOException {
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
	
	public Workbook getWorkBook(){
		return wb;
	}
	
	public static Sheet getSheet(String sheetName){
		return wb.getSheet(sheetName);
	}
	
	public HashMap<String, String> getRowMap(){
		return map;
	}
	
	/**
	 * 获取指定行的数据
	 * @param Workbook wb 
	 * @param Sheet sheet
	 * @return HashMap<key,value> key:首行cell的值，value:指定行cell的值
	 * */
	public HashMap<String, String> mapFromSheet(String fileName, String sheetName, String caseName){
		
//		Workbook wookbook = null;
//		Sheet sheet = null;
		int rowNum = 0;
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			setWorkbook(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sheet = getSheet(sheetName);
		
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

			map.put(sheet.getRow(0).getCell(i).toString(), sheet.getRow(rowNum).getCell(i).toString());
		}
		
		return map;
	}
	
	/**
	 * 获取指定行的数据
	 * @param Workbook wb 
	 * @param Sheet sheet
	 * @return HashMap<key,value> key:首行cell的值，value:指定行cell的值
	 * */
	public HashMap<String, String> mapFromSheet(){
		
//		Workbook wookbook = null;
//		Sheet sheet = null;
		int rowNum = 0;
		
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
				throw new IllegalArgumentException("参数错误："+filePath+"表："+sheetName+"第1行第"+i+"列的值为空!!!") ;
			}

			map.put(sheet.getRow(0).getCell(i).toString(), sheet.getRow(rowNum).getCell(i).toString());
		}
		
		return map;
	}
	
	@Test
	public void getMapTest(){
		String path = System.getProperty("user.dir");
		String filePath = path + "\\resources\\case\\Basic.xlsx";
		String sheetName = "Base";
		String caseName = "Basic";
		HashMap<String, String> map = null;
		map = mapFromSheet(filePath, sheetName, caseName);
		
		System.out.println(map.get("Method"));
	}
}
