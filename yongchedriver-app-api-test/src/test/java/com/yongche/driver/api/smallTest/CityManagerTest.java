package com.yongche.driver.api.smallTest;

import static org.junit.Assert.*;

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
/**
 * 测试返回产品经理
 * @author grace
 * 返回字段：manager_phone_number为产品经理电话（如果没有产品经理电话返回0）
 */
public class CityManagerTest {
		public static RequestHeaderCofig header;
		public static String response;
		public static HttpRequest request;
		public static RequestUrlConfig url;
		public DriverInfo driver;
		public static int code;
		
		@BeforeClass
		public static void setUp(){
			url = new RequestUrlConfig("http://testing.d.yongche.org","/Driver/Member");
		}
		
		@After
		public void tearDown(){
		}


		@Test
		public void testResponseCodeSuccess(){
			DriverInfo driver = new DriverInfo("16891929394", "三星测试");	
			driver.vehicle_number = "1990";
			driver.imei = "359357050178038";
			driver.driverID = "1271";		
			//获取Token
			String oauthToken = GetConfigInfo.getOauth_token(driver);		
			
			//用token配置header
			header = new RequestHeaderCofig(oauthToken);
			//请求参数
			Map<String,String> getListPara = new HashMap<String, String>();
			getListPara.put("imei",driver.imei);
			
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
			response = request.body();
			System.out.println("response is :" + response);
			
			String city_managerString = JsonPath.read(response, "$.msg.member_info.manager_phone_number");
			assertTrue(city_managerString.equals("16889898989")); 
		}
		
//		@Test
		public void testNoPhoneNumbeerReturnSuccess(){
			driver = new DriverInfo("16800000222", "程思测试");	
			driver.vehicle_number = "1111";
			driver.imei = "867496010827329";
			driver.driverID = "670";
			driver.driverAppVersion = "41";
			
			//获取Token
			String oauthToken = GetConfigInfo.getOauth_token(driver);			
			
			//用token配置header
			header = new RequestHeaderCofig(oauthToken);
			//请求参数
			Map<String,String> getListPara = new HashMap<String, String>();
			getListPara.put("imei",driver.imei);
//			getListPara.put("version",driver.driverAppVersion);
			
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
			response = request.body();
			System.out.println("response is :" + response);
			
			String city_managerString = JsonPath.read(response, "$.msg.member_info.manager_phone_number").toString();
			
			assertTrue(city_managerString.equals("0"));
			
		}
}
