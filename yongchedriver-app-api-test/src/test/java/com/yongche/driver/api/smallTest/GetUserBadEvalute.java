package com.yongche.driver.api.smallTest;

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
import com.yongche.driver.api.tools.RequestForUsersAndOrders;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class GetUserBadEvalute {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static Map<String, String> getListPara;
	public static RequestForUsersAndOrders orderRequest;
	
	@BeforeClass
	public static void setUp(){
		
		driver = new DriverInfo().getDriverWithImei();
//		orderRequest = new RequestForUsersAndOrders("5");
		url = new RequestUrlConfig("http://testing.d.yongche.org","/Order/GetList");
		
		String oauthToken = GetConfigInfo.getOauth_token(driver);
		
		header = new RequestHeaderCofig(oauthToken);

		//请求参数
		getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("from", "1409716315");
		getListPara.put("to", "1410753115");
		getListPara.put("mode", "full");
		getListPara.put("status","unbalanced");
		getListPara.put("user_id", "5127");
	}
	
	@After
	public void tearDown(){
	}

	@Test
	public void testRentForHourNOGetOffAddress() throws InterruptedException{	
		response = new RequestMulitAssemble(url.getUrl(), getListPara, header).getResponse_getMethod();
		response = JsonPath.read(response, "$.msg").toString();
		System.out.println(JsonPath.read(response, "$.result"));
		JSONArray tagList = JsonPath.read(response, "$.result[*].tag_text");
		System.out.println(tagList);
		tagList = JsonPath.read(response, "$.result[*].count");
		System.out.println(tagList);
	}
}

