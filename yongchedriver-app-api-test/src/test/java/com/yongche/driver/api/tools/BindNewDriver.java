package com.yongche.driver.api.tools;

import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.yongche.driver.api.data.DriverInfo;

public class BindNewDriver {
	
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig urlConfig;
	public static DriverInfo driver;
	public static int code;
    public static void loginAndBind(){
		header = new RequestHeaderCofig();
		urlConfig = new RequestUrlConfig("/Driver/VerifyCooperaStatus");
		driver = new DriverInfo().getDriverWithImei();
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",driver.vehicle_number);
		paMap.put("cellphone",driver.cellPhone);
		paMap.put("area_code","86");
		paMap.put("version",driver.driverAppVersion);
		paMap.put("imei",driver.imei);
		System.out.println(paMap);
		request = new RequestMulitAssemble(urlConfig.getUrl(),paMap,header).getHttpRequest_getMethod();
		code = request.code();
		response = request.body();
		System.out.println("response is :" + response);
    }
    
    public static void loginAndBind(DriverInfo bindDriver){
		header = new RequestHeaderCofig();
		urlConfig = new RequestUrlConfig("/Driver/VerifyCooperaStatus");
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",bindDriver.vehicle_number);
		paMap.put("cellphone",bindDriver.cellPhone);
		paMap.put("area_code","86");
		paMap.put("version",bindDriver.driverAppVersion);
		paMap.put("imei",bindDriver.imei);
		System.out.println(paMap);
		request = new RequestMulitAssemble(urlConfig.getUrl(),paMap,header).getHttpRequest_getMethod();
		response = request.body();
		System.out.println("response is :" + response);
    }
}
