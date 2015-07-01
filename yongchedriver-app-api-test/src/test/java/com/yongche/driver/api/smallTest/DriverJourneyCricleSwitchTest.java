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

public class DriverJourneyCricleSwitchTest {
		public static RequestHeaderCofig header;
		public static String response,badResponse,openResponse;
		public static HttpRequest request;
		public static RequestUrlConfig setUrl,getUrl;
		public static DriverInfo driver;
		public static Map<String, String> getListPara;
		
		@BeforeClass
		public static void setUp(){
			
			driver = new DriverInfo().getDriverWithImei();
			String oauthToken = GetConfigInfo.GetAccessTokenTest();
			
			header = new RequestHeaderCofig(oauthToken);
			//请求参数
			getListPara = new HashMap<String, String>();
			getListPara.put("imei",driver.imei);
			getListPara.put("version",driver.driverAppVersion);
			setUrl = new RequestUrlConfig("http://testing.d.yongche.org","/v4/Driver/SetDiscountLine");
			getUrl = new RequestUrlConfig("http://testing.d.yongche.org","/v4/Driver/GetDiscountLine");
		}
		@After
		public void tearDown(){
		}

		@Test
		public void testGetRightCodeSuccess(){				
			
			//请求参数
			getListPara.put("flag", "0");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
		
			int code = Integer.parseInt(JsonPath.read(JsonPath.read(response, "$.msg").toString(),"$.ret_code").toString());
			assertEquals(200, code);
		}
		//设置为0成功
		@Test
		public void testSwitchCloseSuccess(){
			
			getListPara.put("flag", "0");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
			getListPara.remove("flag");
			response = new RequestMulitAssemble(getUrl.getUrl(),getListPara,header).getResponse_getMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(JsonPath.read(response, "$.msg").toString(),"$.result").toString());
			assertEquals(0, code);
		}
		//设置为1成功
		@Test
		public void testOpenRequestSuccess(){
			getListPara.put("flag", "1");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
			getListPara.remove("flag");
			response = new RequestMulitAssemble(getUrl.getUrl(),getListPara,header).getResponse_getMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(JsonPath.read(response, "$.msg").toString(),"$.result").toString());
			assertEquals(1, code);
		}
		
		@Test
		public void testBadRequest_returnRightSuccess(){
			getListPara.put("flag", "bad");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
//			getListPara.remove("flag");
//			response = new RequestMulitAssemble(getUrl.getUrl(),getListPara,header).getResponse_getMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			assertEquals(499, code);
		}
		
		@Test
		public void testBadRequest_NullRightSuccess(){
			getListPara.remove("flag");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			assertEquals(499, code);
		}
		
		@Test
		public void testBadRequest_WrongNumberSuccess(){
			getListPara.put("flag","7");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			assertEquals(499, code);
		}
		
		@Test
		public void testBadRequest_NullImeiSuccess(){
			getListPara.remove("imei");
			response = new RequestMulitAssemble(setUrl.getUrl(),getListPara,header).getResponse_postMethod();
			System.out.println("response is :" + response);
			int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
			assertEquals(code, 0);
			getListPara.put("imei",driver.imei);
		}
		

}
