package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThan;

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
//获取每单的数据
public class GetDriverDayOrderTest {
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
		url = new RequestUrlConfig("/V1/Driver/GetDriverDayOrder");
		
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("timeflag",driver.orderSpecificDay);
		
		
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
	public void testDetalNotNull(){
		with(response).assertNotNull("$.msg.detail");
	}
	
	@Test
	public void testDetailTimeBiggerThanZero(){
		with(response).assertThat("$.msg.detail[*].time",everyItem(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void testDetailService_order_idBiggerThanZero(){
		with(response).assertThat("$.msg.detail[*].service_order_id",everyItem(greaterThan(0)));
	}
	
	@Test
	public void testDetailStart_positionBiggerThanZero(){
		with(response).assertThat("$.msg.detail[*].start_position",everyItem(notNullValue()));
	}
	
	@Test
	public void testDetailAmountBiggerThanZero(){
		with(response).assertThat("$.msg.detail[*].amount",everyItem(notNullValue()));
	}
	
	@Test
	public void testDetailActual_time_lengthBiggerThanZero(){
		with(response).assertThat("$.msg.detail[*].actual_time_length",everyItem(greaterThanOrEqualTo(0)));
	}
	
	
	@Test
	public void testCountBiggerThanZero(){
		with(response).assertThat("$.msg.count",greaterThan(0));
	}
	
	@Test
	public void testAmountBiggerThanZero(){
		with(response).assertThat("$.msg.amount",greaterThanOrEqualTo(0.0));
	}
	
	

}
