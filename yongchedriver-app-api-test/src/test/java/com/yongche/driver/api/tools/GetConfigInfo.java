package com.yongche.driver.api.tools;

import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
/**
 * 获取司机端请求配置信息
 * @author grace
 *
 */
public class GetConfigInfo {
	public static RequestHeaderCofig header;
	public static HttpRequest request;
	public static String response;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static String accessToken;
	public static String password;

	static{
		driver = new DriverInfo().getDriverWithImei();
		header = new RequestHeaderCofig();
		url = new RequestUrlConfig("/Driver/CreateDriverPassword");
		
		
		//获取验证码
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",driver.vehicle_number);
		paMap.put("cellphone",driver.cellPhone);
		paMap.put("area_code","+86");
		paMap.put("imei",driver.imei);
		System.out.println(paMap);		
		response = new RequestMulitAssemble(url.getUrl(),paMap,header).getResponse_getMethod();
	    password = JsonPath.read(response, "$.msg.password").toString();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("imei", driver.imei);
		paraMap.put("x_auth_username", driver.cellPhone);
		paraMap.put("x_auth_password", password);
		
		url.path = "/oauth/accessToken";
		response = new RequestMulitAssemble(url.getUrl(),paraMap,header).getResponse_postMethod();
		accessToken = JsonPath.read(response,"$.msg.oauth_token");
		System.out.println("=======================================");
	}
	
	/**
	 * 获取司机端oauth_token
	 * @return
	 */
	public static String GetAccessTokenTest(){
		return accessToken;
	}
	
	/**
	 * 获取司机端验证码
	 * @return
	 */
	public static String GetPassWord(){
		return password;
	}
	
	/**
	 * 如果需要用到多个driver,用该方法来获取不同的token
	 * @param driver
	 * @return
	 */
	public static String getOauth_token(DriverInfo driver){
		header = new RequestHeaderCofig();
		url = new RequestUrlConfig("/Driver/CreateDriverPassword");
		
		//获取验证码
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",driver.vehicle_number);
		paMap.put("cellphone",driver.cellPhone);
		paMap.put("area_code","+86");
		paMap.put("imei",driver.imei);
		System.out.println(paMap);		
		response = new RequestMulitAssemble(url.getUrl(),paMap,header).getResponse_getMethod();
	    password = JsonPath.read(response, "$.msg.password").toString();
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("imei", driver.imei);
		paraMap.put("x_auth_username", driver.cellPhone);
		paraMap.put("x_auth_password", password);
		
		url.path = "/oauth/accessToken";
		response = new RequestMulitAssemble(url.getUrl(),paraMap,header).getResponse_postMethod();
		accessToken = JsonPath.read(response,"$.msg.oauth_token");
		
		return accessToken;
	}
}
