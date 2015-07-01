package com.yongche.driver.api.smallTest;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

/**
 * 测试：返回城市开通串活以及串活折扣
 * 参数为imei
 * is_open_city=1 表示已开通
 * discount 为折扣
 * @author grace
 */
public class GetCityDiscountLineInfoTest {
	public static RequestHeaderCofig header;
	public static String beijingResponse,shanghaiResponse;;
	public static RequestUrlConfig url;
	public static String beijingResult,shanghaiResult;
	
	@BeforeClass
	public static void setUp(){
		url = new RequestUrlConfig(" http://localhost:8080/mockjsdata/2","/v4/Driver/GetCityDiscountLineInfo");	
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();		
		
		//用token配置header
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",new DriverInfo().getDriverWithImei().imei);
//		getListPara.put("version",driver.driverAppVersion);
//		getListPara.put("driver_id","1271");
		
		beijingResponse = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		System.out.println("beijing response is :" + beijingResponse);
		beijingResult =JsonPath.read(JsonPath.read(beijingResponse, "$.msg").toString(),"$.result").toString();
		
		
		DriverInfo driver = new DriverInfo("16800000357","程思测试_上海");		
		driver.imei = "test-shanghai-driver";
		driver.driverID = "859";
		driver.driverAppVersion = "41";
		driver.vehicle_number = "9999";
		
		//设置请求参数
		getListPara.put("imei","2");
		//获取Token
		oauthToken = GetConfigInfo.getOauth_token(driver);	
		//用token配置header
		header = new RequestHeaderCofig(oauthToken);
		
		shanghaiResponse = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		System.out.println("response is :" + shanghaiResponse);
		shanghaiResult =JsonPath.read(JsonPath.read(shanghaiResponse, "$.msg").toString(),"$.result").toString();
	}
	
	@After
	public void tearDown(){
	}


	
	@Test
	public void testIsBeijingOpenCircle(){
		
		
		int is_open_city = JsonPath.read(beijingResult, "$.is_open_city");
		assertTrue(is_open_city == 1); 
	}
	
	@Test
	public void testBeijingDiscountSuccess(){
		int discount = JsonPath.read(beijingResult, "$.discount");
		assertTrue(discount == 90); 
	}
	
	@Test
	public void testShanghaiDiscountSuccess(){

		int discount = JsonPath.read(shanghaiResult, "$.discount");
		assertTrue(discount == 90); 
	}
	
	@Test
	public void tesShanghaiIsNotOpenRight(){
		int is_open_city = JsonPath.read(shanghaiResult, "$.is_open_city");
		assertTrue(is_open_city == 1); 
	}
}
