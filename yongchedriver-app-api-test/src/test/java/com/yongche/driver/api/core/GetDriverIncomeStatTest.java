package com.yongche.driver.api.core;
//每月收入总量
import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

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
public class GetDriverIncomeStatTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	
	@BeforeClass
	public static void setUp(){
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		driver = new DriverInfo().getDriverWithImei();		
		header = new RequestHeaderCofig(oauthToken);
		url = new RequestUrlConfig("/Driver/GetDriverIncomeStat");
								
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("type","month");
		getListPara.put("time_flag",driver.driverIncomeMonth);
		getListPara.put("out_coord_type", "baidu");
		
		
		System.out.println(header.getHeaderString());
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
	
	@Test
	public void testComplete_orderBiggerThanZero(){
		with(response).assertThat("$.msg.complete_order",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testService_kilometersThanZero(){
		with(response).assertThat("$.msg.service_kilometers",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testService_timesThanZero(){
		with(response).assertThat("$.msg.service_times",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testIncomeThanZero(){
		with(response).assertThat("$.msg.income",greaterThanOrEqualTo(0));
	}
	
	
}
