package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
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
///每月收入
public class GetDriverIncomeOrder {
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
		url = new RequestUrlConfig("/Driver/GetDriverIncomeOrder");
						
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("type","month");
		getListPara.put("timeflag",driver.driverIncomeMonth);
		
		
		
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
////	验证当月的Cases，由于当月收入可能为0，改用某月的，当月的参数：Type = tsmh,固定的参数为type = month
//	@Test
//	public void testCountBiggerThanZero(){
//		with(response).assertThat("$.msg.count",greaterThanOrEqualTo(0));
//	}
//	
//	@Test
//	public void testAmounttBiggerThanZero(){
////		with(response).assertThat("$.msg.amount",greaterThanOrEqualTo(0));
//		String amount = JsonPath.read(response,"$.msg.amount").toString();
//		Float amountFloat = Float.parseFloat(amount);
//		assertTrue(amountFloat>=0);
//		
//	}
//	@Test
//	//验证开始日期不能为空
//	public void testStartDateNotNull(){
//		with(response).assertNotNull("$.msg.start");
//	}
//	@Test
//	//验证结束日期不能为空
//	public void testEndDateNotNull(){
//		with(response).assertNotNull("$.msg.end");
//	}
//	@Test
//	//验证订单信息	
//	public void testEveryDateNotNull(){
//		with(response).assertThat("$.msg.result[*].date",everyItem(notNullValue()));
//	}
//	@Test
//	//验证订单信息	
//	public void testWeekNotNull(){
//		with(response).assertThat("$.msg.result[*].week",everyItem(notNullValue()));
//	}
//	@Test
//	//验证订单信息	
//	public void testOrderCountBiggerThanZero(){
//		with(response).assertThat("$.msg.result[*].count",everyItem(greaterThan(0)));
//	}
//	@Test
//	//验证订单信息	
//	public void testOrderTimeBiggerThanZero(){
//		with(response).assertThat("$.msg.result[*].time_long",everyItem(greaterThanOrEqualTo(0)));
//	}
//	@Test
//	//验证订单信息	
//	public void testOrderKilometersBiggerThanZerol(){
//		with(response).assertThat("$.msg.result[*].kilometers",everyItem(greaterThanOrEqualTo(0)));
//	}
//	@Test
//	//验证订单信息	
//	public void testOrderAmountBiggerThanZero(){
//		with(response).assertThat("$.msg.result[*].amount",everyItem(notNullValue()));
//	}
	
	//验证订单信息	
	public void testIncomeBiggerThanZero(){
		with(response).assertThat("$.msg[*].amount",everyItem(greaterThanOrEqualTo(0.00)));
	}
	@Test
	//验证订单信息	
	public void testCountBiggerThanZero(){
		with(response).assertThat("$.msg[*].count",everyItem(greaterThanOrEqualTo(0)));
	}
	@Test
	//验证订单信息	
	public void testTime_longBiggerThanZero(){
		with(response).assertThat("$.msg[*].time_long",everyItem(greaterThanOrEqualTo(0)));
	}
	
	@Test
	//验证订单信息	
	public void testKilometersBiggerThanZero(){
		with(response).assertThat("$.msg[*].kilometers",everyItem(greaterThanOrEqualTo(0)));
	}
	
	public void testWeekNotNull(){
		with(response).assertThat("$.msg[*].week",everyItem(notNullValue()));
	}
	
	public void testDateNotNull(){
		with(response).assertThat("$.msg[*].date",everyItem(notNullValue()));
	}
	

	
}
