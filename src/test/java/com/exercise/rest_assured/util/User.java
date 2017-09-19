package com.exercise.rest_assured.util;

public class User {
	private String p_name = "灰太狼";
	private String p_password = "123456";
	private String p_v_code = "testing";
	private String p_uuid = "86f4ae22-d9aa-4644-9f69-520da8ee361d";
	
	private String e_name = "大水杯科技";
	private String e_password = "111111";
	private String e_v_code = "testing";
	private String e_uuid = "603397e1-94be-4896-877a-fc869efc9184";
	
	public String getPUsername() {
		return p_name;
	}
	public String getPPassword() {
		return p_password;
	}
	public String getPV_code() {
		return p_v_code;
	}
	public String getPUuid() {
		return p_uuid;
	}
	
	public String getEUsername() {
		return e_name;
	}
	public String getEPassword() {
		return e_password;
	}
	public String getEV_code() {
		return e_v_code;
	}
	public String getEUuid() {
		return e_uuid;
	}
}
