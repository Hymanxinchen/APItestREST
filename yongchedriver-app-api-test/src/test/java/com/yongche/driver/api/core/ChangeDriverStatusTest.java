package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class ChangeDriverStatusTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	public static String driverStatus;
	
	@BeforeClass
	public static void setUp(){
		
		driver = new DriverInfo().getDriverWithImei();
		url = new RequestUrlConfig("/Driver/memberStat");
		
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		System.out.println("Token is :" + oauthToken);
		
		header = new RequestHeaderCofig(oauthToken);
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
//		getListPara.put("stat", "nobusy");
		
		//获取请求	
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
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
	public void testCodeSuccess(){
		with(response).assertThat("$.code", equalTo(200));
	}
}
