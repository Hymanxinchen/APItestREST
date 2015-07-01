package com.yongche.driver.api.smallTest;

import static org.junit.Assert.*;

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

public class GetInvateContent {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static Map<String, String> getListPara;
	
	
	@BeforeClass
	public static void setUp(){
		url = new RequestUrlConfig("http://huangyang.d.yongche.org","/v4/Driver/GetInvateContent");
		driver = new DriverInfo().getDriverWithImei();

		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		getListPara = new HashMap<String, String>();
		
	}
	
	@After
	public void tearDown(){
	}


	@Test
	public void testReturnRightContent(){
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
		assertEquals("邀请新乘客，就给现金奖励",JsonPath.read(response,"$.msg.content"));
	}
	
	@Test
	public void testReturnRightDescription(){
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
		assertEquals("邀请新乘客用车后，将奖励TA用车金额的10%给您，最多20单！邀请越多，奖励越高，不设上限！",JsonPath.read(response,"$.msg.description"));
	}
	
	@Test
	public void testNODriverID(){
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
		assertEquals(200,JsonPath.read(response,"$.code"));
	}
	

}
