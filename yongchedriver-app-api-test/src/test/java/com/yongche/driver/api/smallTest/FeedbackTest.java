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

public class FeedbackTest {

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
		url = new RequestUrlConfig("http://testing.d.yongche.org","/v4/Driver/FeedBack");
		
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		System.out.println("Token is :" + oauthToken);
		
		header = new RequestHeaderCofig(oauthToken);
		getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
//		getListPara.put("content", "");
	}
	
	@After
	public void tearDown(){
	}

	@Test
	public void testContentNullCodeSuccess(){
		//获取请求	
		getListPara.put("content", "");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		System.out.println("response is :" + response);
		assertEquals(499, code);
	}
	
	@Test
	public void testContentNormalCodeSuccess(){
		
		getListPara.put("content", "this is a test,这就是一个Test");
		//获取请求	
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		
		System.out.println("response is :" + response);
		assertEquals(200, code);
	}
	
	@Test
	public void testContentSpecialCodeSuccess(){
		//获取请求
		getListPara.put("content", "/*&6$%^#@!~我我我我，/.-=-https://yongche.com<>br</></id38><br>id=38键点击kid\\(^o^)/~,.//6￥6$《》】<script></scirpt>【【】{}、、|?driver_id＝23");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		
		System.out.println("response is :" + response);
		assertEquals(200, code);
	}
	
	@Test
	public void testContentTooLongCodeSuccess(){
		//获取请求
		getListPara.put("content", "/*&6$%^#@!~我我我我，/.-=-this is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Test"
                + "this is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Test"
                + "this is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Test"
                + "this is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Testthis is a test,这就是一个Test");

		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		System.out.println("response is :" + response);
		assertEquals(200, code);
	}
	
	@Test
	public void testNOContentCodeSuccess(){
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		code = Integer.parseInt(JsonPath.read(response, "$.code").toString());
		System.out.println("response is :" + response);
		assertEquals(499, code);
	}
}
