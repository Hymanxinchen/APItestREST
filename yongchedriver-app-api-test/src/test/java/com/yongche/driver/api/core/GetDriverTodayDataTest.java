package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

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

public class GetDriverTodayDataTest {
	
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	
	public static String driverStatus;
	
	@BeforeClass
	public static void setUp(){
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		driver = new DriverInfo().getDriverWithImei();		
		header = new RequestHeaderCofig(oauthToken);
		url = new RequestUrlConfig("/V1/Driver/GetDriverTodayData");
										
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
	public void testToday_incomeBiggerThanZero(){
		double income = Double.parseDouble(JsonPath.read(response,"$.msg.tday_income").toString());
		assertTrue(income>=0.00);
	}
	
	@Test
	public void testToday_servicekmBiggerThanZero(){		
		String monthIncome = JsonPath.read(response, "$.msg.tday_servicekm").toString();
		assertTrue(Float.parseFloat(monthIncome)>=0);
	}
	
	@Test
	public void testToday_servicetimeBiggerThanZero(){
		
		with(response).assertThat("$.msg.tday_servicetime",notNullValue() );
	}
	
	@Test
	public void testToday_collectBiggerThanZero(){
		with(response).assertThat("$.msg.tday_collect", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testContributionBiggerThanZero(){
		with(response).assertThat("$.msg.contribution", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testEvaluationBiggerThanZero(){
		with(response).assertThat("$.msg.evaluation", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testMth_incomeBiggerThanZero(){
		String monthIncome = JsonPath.read(response, "$.msg.tmth_income").toString();
		assertTrue(Float.parseFloat(monthIncome)>=0);
	}
	
	@Test
	public void testMth_servicekmBiggerThanZero(){
		with(response).assertThat("$.msg.tmth_servicekm", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testMth_servicetimeBiggerThanZero(){
		String monthIncome = JsonPath.read(response, "$.msg.tmth_servicetime").toString();
		assertTrue(Float.parseFloat(monthIncome)>=0);
	}
	
	@Test
	public void testMth_completeorderBiggerThanZero(){
		with(response).assertThat("$.msg.tmth_completeorder", greaterThanOrEqualTo(0));
	}
	
}
