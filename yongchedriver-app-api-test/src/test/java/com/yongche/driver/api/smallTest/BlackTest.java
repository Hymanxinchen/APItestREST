package com.yongche.driver.api.smallTest;



	import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.minidev.json.JSONArray;

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

	public class BlackTest {
		public static RequestHeaderCofig header;
		public static String response;
		public static HttpRequest request;
		public static RequestUrlConfig url;
		public static DriverInfo driver;
		public static int code;
		public static Map<String,String> getListPara;
		
		@BeforeClass
		public static void setUp(){
			driver = new DriverInfo().getDriverWithImei();
			
			String oauthToken = GetConfigInfo.GetAccessTokenTest();
			System.out.println("Token is :" + oauthToken);
			
			header = new RequestHeaderCofig(oauthToken);
			url = new RequestUrlConfig("http://testing2.d.yongche.org","/Driver/BlackList");

			
			
//			
//			Calendar calendar = Calendar.getInstance();//日历对象
//			Long timeLong =calendar.getTimeInMillis()/1000;
//			//请求参数
//			
			
			HashMap blackList = new HashMap<String, String>();
			blackList.put("version","59");
			blackList.put("imei", driver.imei);
			//HttpRequest getRequest = new RequestMulitAssemble(url.getUrl(),blackList,header).getHttpRequest_postMethod();
		   HttpRequest getRequest = new RequestMulitAssemble(url.getUrl(),blackList,header).getHttpRequest_getMethod();
			response = getRequest.body();
			JSONArray black_id = JsonPath.read(response, "$.msg.[*].black_list_id");
			
			System.out.println(black_id.get(0));
			getListPara = new HashMap<String, String>();
			getListPara.put("black_list_id",black_id.get(0).toString());
			getListPara.put("version","59");
			getListPara.put("imei", driver.imei);
			
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			System.out.println("response is :" + response);
			
		}
		
		@After
		public void tearDown(){
		}


		@Test
		public void testResponseCodeSuccess() throws InterruptedException{

			System.out.println("response is :" + response);
		}
		

}
