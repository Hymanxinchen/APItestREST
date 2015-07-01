package com.yongche.driver.api.core;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class GetAccessTokenTest {
	public static RequestHeaderCofig header;
	public static HttpRequest request;
	public static String response;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	
	@BeforeClass
	public static void setUp(){
		header = new RequestHeaderCofig();
		url = new RequestUrlConfig("/Driver/CreateDriverPassword");
		driver = new DriverInfo().getDriverWithImei();
		
		//获取验证码
		Map<String,String> paMap = new HashMap<String, String>();
		paMap.put("vehicle_number",driver.vehicle_number);
		paMap.put("cellphone",driver.cellPhone);
		paMap.put("area_code","+86");
		paMap.put("imei",driver.imei);
		System.out.println(paMap);		
		response = new RequestMulitAssemble(url.getUrl(),paMap,header).getResponse_getMethod();
		String password = JsonPath.read(response, "$.msg.password").toString();
				
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("imei", driver.imei);
		paraMap.put("x_auth_username", driver.cellPhone);
		paraMap.put("x_auth_password", password);
		
		url.path = "/oauth/accessToken";
		request = new RequestMulitAssemble(url.getUrl(),paraMap,header).getHttpRequest_postMethod();
		code = request.code();
		response = request.body();
		System.out.println("response is :" + response);
	}
	
	@Test
	public void testResponseCodeSuccess(){
		 assertEquals(200, code);
	}
	
	@Test
	public void testResponseWithSuccessCode(){
		with(response).assertThat("$.code", equalTo(200));
	}
	
	@Test
	public void testOauth_tokenNotNull(){
		with(response).assertNotNull("$.msg.oauth_token");
	}
	
	@Test
	public void testOauth_token_secretNotNull(){
		with(response).assertNotNull("$.msg.oauth_token_secret");
	}
	
	@Test
	public void testUser_idNotNull(){
		with(response).assertNotNull("$.msg.user_id");
	}
	
	@Test
	public void testXmpp_tokenNotNull(){
		with(response).assertNotNull("$.msg.xmpp_token");
	}
}
