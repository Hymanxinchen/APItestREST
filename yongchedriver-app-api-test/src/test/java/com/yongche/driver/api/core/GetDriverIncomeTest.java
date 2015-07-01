package com.yongche.driver.api.core;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
//import static org.hamcrest.Matchers.greaterThan;



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

//历史收入测试
public class GetDriverIncomeTest {
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
		url = new RequestUrlConfig("/V1/Driver/GetDriverIncome");
								
		
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("is_gzip","1");
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		
		//获取被测试response		
		System.out.println(header.getHeaderString());
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
		//http返回码
		code = request.code();
		response = request.body();
		System.out.println("response is :" + response);
	}
	
	@After
	public void tearDown(){
	}
	
	@Test
	public void testResponseCodeSuccess(){
		with(response).assertThat("$.code", equalTo(200));
	}
	
	@Test
	public void testIncomeNotNull(){
		with(response).assertThat("$.msg[*].incomesss",everyItem(notNullValue()));
	}
	
	@Test
	public void testTime_flagNotNull(){
		with(response).assertThat("$.msg[*].time_flag",everyItem(notNullValue()));
	}
	
	@Test
	public void testComplete_ordereBiggerThanZero(){
		with(response).assertThat("$.msg[*].complete_order", everyItem(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void testService_timesThanZero(){
		with(response).assertThat("$.msg[*].service_times",everyItem(notNullValue()));
	}
	
	@Test
	public void testService_kilometersBiggerThanZero(){
		with(response).assertThat("$.msg[*].service_kilometers",everyItem(greaterThanOrEqualTo(0)));
	}
}
