package com.yongche.driver.api.smallTest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class GetDriverInviteTests {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	
	@BeforeClass
	public static void setUp(){
		
		driver = new DriverInfo().getDriverWithImei();
		url = new RequestUrlConfig("http://testing.d.yongche.org","/Driver/GetDriverInvite");
		
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		header = new RequestHeaderCofig(oauthToken);
	}
	@After
	public void tearDown(){
	}

	@Test
	public void testGetRightCodeSuccess(){	
		//请求参数
		Map<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		assertEquals(200, code);
	}
	
	@Test
	public void testNoImeiSuccess(){	
		Map<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("version",driver.driverAppVersion);
		
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		assertEquals(0, code);
	}
	
	@Test
	public void testNoVersionSuccess(){	
		Map<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		assertEquals(200, code);
	}
	
	@Test
	public void testOlderVersionSuccess(){	
		Map<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("version","45");
		getListPara.put("imei",driver.imei);
		
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		assertEquals(200, code);
	}

}
