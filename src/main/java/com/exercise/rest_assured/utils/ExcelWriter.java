package com.exercise.rest_assured.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	public static void main(String[] args) {
		createExcel();
	}
	
	public static void createExcel(){
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(wb!=null){
			Sheet baseSheet = wb.createSheet("Base");
			Sheet paramSheet = wb.createSheet("Params");
			
			Row row = baseSheet.createRow(0);
			String[] baseTitle = {"API","Desc","Method","BasePath","Path"};
			int i = 0;
			for(String title:baseTitle){
				Cell cell = row.createCell(i, CellType.BLANK);
				cell.setCellValue(title);
				baseSheet.autoSizeColumn(i);
				i++;
			}
			
			String[] paramTitle = {"toke","id"};
			Row pRow = paramSheet.createRow(0);
			int j = 0;
			for(String title:paramTitle){
				Cell cell = pRow.createCell(j);
				cell.setCellValue(title);
				paramSheet.autoSizeColumn(j);
				j++;
			}
			
			try {
				FileOutputStream outputStream = new FileOutputStream("C:\\Users\\sam\\Desktop\\a.xlsx");
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
