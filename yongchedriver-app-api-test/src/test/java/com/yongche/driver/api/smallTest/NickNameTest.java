package com.yongche.driver.api.smallTest;

import static org.junit.Assert.assertEquals;

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

public class NickNameTest {
		public static RequestHeaderCofig header;
		public static String response;
		public static HttpRequest request;
		public static RequestUrlConfig url;
		public static DriverInfo driver;
		public static Map<String, String> getListPara;
		public static int code;
		public static String oauthToken;
		
		@BeforeClass
		public static void setUp(){
			
			//获取Token
			oauthToken = GetConfigInfo.GetAccessTokenTest();
			
			driver = new DriverInfo().getDriverWithImei();		
			header = new RequestHeaderCofig(oauthToken);
			url = new RequestUrlConfig("http://testing.d.yongche.org","/Driver/SetLoginName");
															
			//请求参数
			getListPara = new HashMap<String, String>();
			getListPara.put("imei",driver.imei);
			getListPara.put("version",driver.driverAppVersion);
			
			
		}
		
		@After
		public void tearDown(){
//			getListPara.put("imei",driver.imei);
//			getListPara.put("login_name", "clean");
//			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
//			response = request.body();
		}


		@Test
		public void testAddNickCorrectly(){
			getListPara.put("imei",driver.imei);
			getListPara.put("login_name", "天王盖地虎");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(200, code);
		}
		
		@Test
		public void testSpecialNickNameCorrectLy(){
			getListPara.put("login_name", "办证书");
			getListPara.put("imei",driver.imei);
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(499, code);
		}
		
		@Test
		public void testWrongHeaderCorrectLy(){
			getListPara.put("login_name", "123");
			getListPara.put("imei",driver.imei);
			header.setOauth_token("heiheih");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			header.setOauth_token(oauthToken);
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			assertEquals(0, code);
		}
		
		@Test
		public void testSpecailNickName(){
			getListPara.put("imei",driver.imei);
			getListPara.put("login_name", "/><select*from driver>");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(200, code);
		}
		
		@Test
		public void testNullNickName(){
			getListPara.put("login_name", "");
			getListPara.put("imei",driver.imei);
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(498, code);
		}
		
		@Test
		public void testSameNickName(){
			getListPara.put("login_name", "hh");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(499, code);
		}
		
		@Test
		public void testNOIMEI(){
			getListPara.put("login_name", "dfd");
			getListPara.remove("imei");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			
			getListPara.put("imei",driver.imei);
			assertEquals(0, code);
		}
		
		@Test
		public void testWongIMEI(){
			getListPara.put("login_name", "dfd");
			getListPara.put("imei","test-shanghai-driver");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			
			assertEquals(0, code);
		}
		
		@Test
		public void testOldVersion(){
			getListPara.put("imei",driver.imei);
			getListPara.put("login_name", "哈哈哈哈");
			getListPara.put("version","41");
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			
			System.out.println("response is :" + response);
			code = Integer.parseInt(JsonPath.read(response,"$.msg.ret_code").toString());
			assertEquals(200, code);
		}
		
		
//		
//		@Test
//		public void testEditNickName(){
//			
//		}
		
}
