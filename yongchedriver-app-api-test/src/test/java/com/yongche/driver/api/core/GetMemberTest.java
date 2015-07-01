package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

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

public class GetMemberTest {
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
		url = new RequestUrlConfig("/Driver/Member");
														
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("is_gzip","1");
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		
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
	public void testDriverInfoNotNull(){
		with(response).assertNotNull("$.msg");
	}
	
	@Test
	public void testDriverNameNotNull(){
		with(response).assertNotNull("$.msg.member_info.name");
	}
	
	@Test
	public void testDriverCellPhoneNotNull(){
		with(response).assertNotNull("$.msg.member_info.cellphone");
	}
	
	@Test
	public void testDriverService_times_totalBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.service_times_total", notNullValue());
	}
	
	@Test
	public void testDriverService_order_totallBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.service_order_total", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverService_kilometers_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.service_kilometers_today", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverService_times_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.service_times_today", notNullValue());
	}
	
	@Test
	public void testDriverIncome_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.income_today", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverAccept_order_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.accept_order_today", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverSuccess_order_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.success_order_today", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverReceive_order_todayBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.receive_order_today", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testDriverIncome_city_rank_tswkBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.income_city_rank_tswk", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testVehicle_numberNotNull(){
		with(response).assertNotNull("$.msg.member_info.vehicle_number");
	}
	
	@Test
	public void testIdentity_cardNotNull(){
		with(response).assertNotNull("$.msg.member_info.identity_card");
	}
	
	@Test
	public void testDrive_licenseNotNull(){
		with(response).assertNotNull("$.msg.member_info.drive_license");
	}
	
	@Test
	public void testCar_typeNotNull(){
		with(response).assertNotNull("$.msg.member_info.car_type");
	}
	
	@Test
	public void testBrandNotNull(){
		with(response).assertNotNull("$.msg.member_info.brand");
	}
	
	@Test
	public void testCollect_cntBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.collect_cnt", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testBlack_cntBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.black_cnt", greaterThanOrEqualTo(0));
	}
	
	
	@Test
	public void testGood_comment_rateBiggerThanZero(){
		with(response).assertThat("$.msg.member_info.good_comment_rate", greaterThanOrEqualTo(0));
	}
}
