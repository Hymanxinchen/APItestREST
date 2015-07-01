package com.yongche.driver.api.core;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.mysql.jdbc.log.Log;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class LoginVerifyCooperaStatusTest {
	
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig urlConfig;
	public static DriverInfo driver;
	public static int code;
	
	@BeforeClass
	public static void setUp(){
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
	
	@After
	public void tearDown(){
	}


	//@Test
	public void testResponseCodeSuccess(){
		 assertEquals(200, code);
	}
	//@Test
	public void testCodeSuccess(){
		with(response).assertThat("$.code", equalTo(200));
	}
	
	//@Test
	public void testNameNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.name"));
	}
	
	//@Test
	public void testStateNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.state"));
	}
	
	@Test
	public void testPasswordNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.password"));
		System.out.println(JsonPath.read(response,"$.msg.password").toString());
		
	}
	
	//@Test
	public void testDriver_idNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.driver_id"));
	}
	
	//@Test
	public void testCar_idNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.car_id"));
	}
	
	//@Test
	public void testArea_CodeNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.cn.area_code"));
		
	}
	
	//@Test
	public void testCountryNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.cn.country"));
		
	}
	
	//@Test
	public void testTimezoneNotNull(){
		assertNotNull(JsonPath.read(response,"$.msg.cn.timezone"));		
	}
}
