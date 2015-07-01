package com.yongche.driver.api.smallTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.OperateOrder;
import com.yongche.driver.api.tools.RequestForUsersAndOrders;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class GetEstimatedFeeTest {
	
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
//		
//		driver = new DriverInfo("16800636774","汪辛僧");		
//		driver.imei = "864895021601263";
//		driver.driverID = "5086";
//		driver.driverAppVersion = "41";
//		driver.vehicle_number = "4567";
		
//		driver = new DriverInfo("16800000357","程思测试_上海");		
//		driver.imei = "test-shanghai-driver";
//		driver.driverID = "859";
//		driver.driverAppVersion = "41";
//		driver.vehicle_number = "9999";
//		driver.driverType = "4";
//		
		
		orderRequest = new RequestForUsersAndOrders("1,2,4,5");
		url = new RequestUrlConfig("http://testing.d.yongche.org","/Order/GetEstimatePrice");
		
		String oauthToken = GetConfigInfo.getOauth_token(driver);
		
		header = new RequestHeaderCofig(oauthToken);

		//请求参数
		getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
	}
	
	@After
	public void tearDown(){
	}

	//测试时租有下车地址时，价格为低消
//	@Test
	public void testRentForHourNOGetOffAddress() throws InterruptedException{	
		 
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
		String order_id = orderRequest.getRentForHourOrderAndRetrunOrderID(1,getOffAddress);
		getListPara.put("order_id", order_id);
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		System.out.println("时租无下车地址:" + price);
		assertTrue(price>=15);
	}
	
	//测试时租无下车地址时，价格为传回的值
//	@Test
	public void testRentForHourWithGetOffAddress() throws InterruptedException{	

		
		String order_id = orderRequest.getRentForHourOrderAndRetrunOrderID();
		getListPara.put("order_id", order_id);
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		assertTrue(price>=15);
	}
	
	//测试套餐价格无下车地址，价格为套餐价格
//	@Test
	public void testSet_DayRent_NoGetOffAddress() throws InterruptedException{	
		 
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
		String order_id = orderRequest.getRentForDayAndRetrunOrderID(8,getOffAddress);
		getListPara.put("order_id", order_id);
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		assertTrue(price>=510);
	}
	
	
	//测试套餐价格有下车地址，价格为预估价格
	@Test
	public void testSet_SendOffWithGetOffAddress() throws InterruptedException{	
		
		String order_id = orderRequest.getSendOffAndRetrunOrderID();
		getListPara.put("order_id", order_id);
		
		TimeUnit.SECONDS.sleep(5);
		//获取司机端预估费用
//		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
//		int price = JsonPath.read(responseString, "$.msg.estimate_price");
//		System.out.println("送机:上车地址：机场西路，有下车地址:机场" + price);
//		assertTrue(price>=68);
	}
	
	//测试套送机餐价格无下车地址，价格为预估价格
//	@Test
	public void testPickUp_NOGetOffAddress() throws InterruptedException{	
		
		String order_id = orderRequest.getPickUpAndRetrunOrderID();
		getListPara.put("order_id", order_id);
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		System.out.println("接机：无下车地址顺义:" + price);
		assertTrue(price>=68);
	}
		
	//测试半日租价格有下车地址，价格为预估价格
	@Test
	public void testHalfDayWithGetOffAddress() throws InterruptedException{	
		
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
		String order_id = orderRequest.getRentForHalfDayAndRetrunOrderID(12,getOffAddress);
//		getListPara.put("order_id", order_id);
		
		//获取司机端预估费用
//		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
//		int price = JsonPath.read(responseString, "$.msg.estimate_price");
//		assertTrue(price>=108);
	}
	
//	@Test
	public void testWithNullOrder() throws InterruptedException{	

		getListPara.put("order_id", "");
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		assertTrue(price==0);
	}
	
//	@Test
	public void testWithNull() throws InterruptedException{	

		getListPara.put("order_id", null);
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		assertTrue(price==0);
	}
	
//	@Test
	public void testWithWrongOrder() throws InterruptedException{	

		getListPara.put("order_id", "2005458764");
		
		//获取司机端预估费用
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
		int price = JsonPath.read(responseString, "$.msg.estimate_price");
		assertTrue(price==20);
	}
	
//	@Test
	public void testChuanhuoOrder(){
		getListPara.put("order_id", "2005460857");
		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
//		int price = JsonPath.read(responseString, "$.msg.estimate_price");
	}
	
	//测试---其它车型
//	@Test
//	public void test

}
