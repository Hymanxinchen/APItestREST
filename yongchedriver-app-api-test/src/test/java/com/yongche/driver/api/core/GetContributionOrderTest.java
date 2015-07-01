package com.yongche.driver.api.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;

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

public class GetContributionOrderTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	
	@BeforeClass
	public static void setUp(){
		

		url = new RequestUrlConfig("/Driver/GetContributionOrder");
		driver = new DriverInfo().getDriverWithImei();
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();	
		header = new RequestHeaderCofig(oauthToken);
		//get 参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("type", "today");
		
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
//	@Test
//	public void testCodeSuccess(){
//		with(response).assertThat("$.code", equalTo(200));
//	}
//	
//	@Test
//	public void testCountBiggerThanZero(){
//		with(response).assertThat("$.msg.count",greaterThanOrEqualTo(0));
//	}
//	
//	@Test
//	public void testCountBiggerThanTotal(){
//		with(response).assertThat("$.msg.total",greaterThanOrEqualTo(0));
//	}
//	
//	@Test
//	public void testOrderListNotNull(){
//		JSONArray orderList = JsonPath.read(response, "$.msg.list");
//		assertThat(orderList, everyItem(notNullValue()));
//	}
//	
//	@Test
//	public void testOrderListcontributionBiggerThanZero(){
////		JSONArray orderList = JsonPath.read(response, "$.msg.list[*].contribution");
//		with(response).assertThat("$.msg.list[*].contribution", everyItem(greaterThanOrEqualTo(0)));
//	}
//	
//	@Test
//	public void testOrderListDecision_resultBiggerThanZero(){
//		with(response).assertThat("$.msg.list[*].decision_result", everyItem(greaterThanOrEqualTo(0)));
//	}
//	
//	@Test
//	public void testOrderListSendtime_resultBiggerThanZero(){
//		with(response).assertThat("$.msg.list[*].sendtime", everyItem(greaterThanOrEqualTo(0)));
//	}
//	
//	@Test
//	public void testOrderListEnd_timeBiggerThanZero(){
//		with(response).assertThat("$.msg.list[*].end_time", everyItem(greaterThanOrEqualTo(0)));
//	}
//	
	
}
