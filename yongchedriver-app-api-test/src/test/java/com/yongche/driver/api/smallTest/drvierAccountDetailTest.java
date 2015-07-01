package com.yongche.driver.api.smallTest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class drvierAccountDetailTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	public static Map<String, String> getListPara;
	
	@BeforeClass
	public static void setUp(){
		
		driver = new DriverInfo().getDriverWithImei();
		url = new RequestUrlConfig("http://testing.d.yongche.org","/V2/Driver/GetDriverAccountDetail");
		
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		System.out.println("Token is :" + oauthToken);
		
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		System.out.println("response is :" + response);
	}
		@After
		public void tearDown(){
		}

		@Test
		public void testGetRightCodeSuccess(){				
			code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
			assertEquals(200, code);
		}
		
		@Test
		public void testRet_codeSuccess(){
			code = Integer.parseInt(JsonPath.read(response, "$.msg.ret_code").toString());
			assertEquals(200, code);
		}
		
		@Test
		public void testAmountNotNull(){
			with(response).assertNotNull("$.msg.amount");
		}
		
		@Test
		public void testResultNotNull(){
			with(response).assertNotNull("$.msg.result");
		}
		
		@Test
		public void testAccount_history_idExist(){
			with(response).assertThat("$.msg.result[*].account_history_id", everyItem(notNullValue()));
		}
		
		@Test
		public void testAmountExist(){
			with(response).assertThat("$.msg.result[*].amount", everyItem(notNullValue()));
		}
		
		@Test
		public void testCreate_timeExist(){
			with(response).assertThat("$.msg.result[*].create_time", everyItem(notNullValue()));
		}
		
		@Test
		public void testExtraExist(){
			with(response).assertThat("$.msg.result[*].extra", everyItem(notNullValue()));
		}
}
