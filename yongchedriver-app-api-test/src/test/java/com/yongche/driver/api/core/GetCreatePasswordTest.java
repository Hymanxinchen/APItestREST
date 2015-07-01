package com.yongche.driver.api.core;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;


public class GetCreatePasswordTest {
	
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig urlConfig;
	public static int code;
	public static DriverInfo driver;
	
	@BeforeClass
	public static void setUp(){
		header = new RequestHeaderCofig();
		driver = new DriverInfo().getDriverWithImei();
		urlConfig = new RequestUrlConfig("/Driver/CreateDriverPassword");
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",driver.vehicle_number);
		paMap.put("is_gzip","1");
		paMap.put("cellphone",driver.cellPhone);
		paMap.put("area_code","+86");
		paMap.put("version",driver.driverAppVersion);
		paMap.put("imei",driver.imei);
		System.out.println(paMap);
		request = new RequestMulitAssemble(urlConfig.getUrl(),paMap,header).getHttpRequest_getMethod();
		code = request.code();
		response = request.body();
		System.out.println("response is :" + response);
	}
	
	@After
	public void tearDown(){
	}


	@Test
	public void testResponseCodeSuccess(){
		 assertEquals(200, code);
	}
	@Test
	public void testResponseWithSuccessCode() {
		with(response).assertThat("$.code", equalTo(200));
	}
		
	@Test 
	public void testPhoneNONotNull(){
		String cellphone = JsonPath.read(response, "$.msg.cellphone");
		assertNotNull(cellphone);
	}
	
	@Test
	public void testPasswordNotNull(){
		String password = JsonPath.read(response,"$.msg.password").toString();
		assertNotNull(password);
	}
		

}
