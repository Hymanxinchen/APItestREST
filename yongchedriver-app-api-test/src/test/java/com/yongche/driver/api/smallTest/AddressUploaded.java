package com.yongche.driver.api.smallTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class AddressUploaded {
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
//		driver = new DriverInfo("16800636773","杨柳青青");
//		driver.imei =  "861138027278122";
//		driver.vehicle_number = "3456";
//		driver.driverID = "924";
//		url = new RequestUrlConfig("http://testing.d.yongche.org","/Global/NotifyPosition");
		
		
//		
//		Calendar calendar = Calendar.getInstance();//日历对象
//		Long timeLong =calendar.getTimeInMillis()/1000;
//		//请求参数
		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
//		getListPara.put("version","51");
//		getListPara.put("latitude", "16.1111");
//		getListPara.put("longitude", "161.11111");
//		getListPara.put("time",String.valueOf(timeLong));
//		getListPara.put("provider", "BAIDU");
//		getListPara.put("in_coord_type", "baidu");
//		getListPara.put("is_gps_open", "0");
		
//		HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
//		response = request.body();
//		System.out.println(timeLong);
//		System.out.println("response is :" + response);
	}
	
	@After
	public void tearDown(){
	}


//	@Test
	public void testResponseCodeSuccess() throws InterruptedException{
		
		getListPara.put("latitude", "39.825553274839");
		getListPara.put("longitude", "116.40876631049");
		
		getListPara.put("provider", "YC_WIFI");
		getListPara.put("in_coord_type", "baidu");
		while(true){
			String oauthToken = GetConfigInfo.GetAccessTokenTest();
			System.out.println("Token is :" + oauthToken);
			
			header = new RequestHeaderCofig(oauthToken);
			Calendar calendar = Calendar.getInstance();//日历对象
			Long timeLong =calendar.getTimeInMillis()/1000;
			System.out.println(timeLong);
			getListPara.put("time",String.valueOf(timeLong));
			HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
			response = request.body();
			TimeUnit.SECONDS.sleep(3*60);
			System.out.println("response is :" + response);
		}
		
	}
	
	@Test 
	public void testResponseSuccess(){
		url = new RequestUrlConfig("http://10.0.11.214:8080/mockjsdata","/5/v3/user/collectdriver");
		
		//获取Token
		String oauthToken = GetConfigInfo.getOauth_token(driver);		
		
		//用token配置header
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("driver_id","670");
		getListPara.put("noCache","true");
		
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
		
		response = request.body();
		
		System.out.println(response);
		
		getListPara = new HashMap<String, String>();
		getListPara.put("driver_id","670");
		getListPara.put("noCache","true");
		
		request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_postMethod();
		
		response = request.body();
		
		System.out.println(response);
		
		getListPara.put("driver_id","69");
		getListPara.put("noCache","true");
		request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
		
		response = request.body();
		
		System.out.println(response);
		
		getListPara.put("driver_id","670");
		getListPara.put("noCache","true");
		request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
		
		response = request.body();
		
		System.out.println(response);
	}
}
