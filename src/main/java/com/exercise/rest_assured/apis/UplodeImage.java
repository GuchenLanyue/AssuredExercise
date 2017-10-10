package com.exercise.rest_assured.apis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;

public class UplodeImage {
	
	@Step("上传图片")
	public String upload(String baseURL,File file){
		if(!file.exists()){
			Assert.fail("File not found:"+file.getAbsolutePath());
		}
		
		Response response = given()
			.multiPart("image",file.getName(),"text/plain")
			.multiPart("image",file,"image/jpeg")
		.when()
			.post(baseURL+"/images/uplodeimage")
		.then()
		.extract()
			.response();
		String body = response.getBody().asString();
		while(body.charAt(0)!='{'){
			body = body.substring(1, body.length());
		}
		JsonPath json = JsonPath.with(body).setRoot("value");
		
		imgLog(file);
		return json.getString("imgurl");
	}
	
	@Attachment(value="Image Log",type="image/png")
	public byte[] imgLog(File file){
        byte[] buffer = null;  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        
        return buffer; 
	}
}
