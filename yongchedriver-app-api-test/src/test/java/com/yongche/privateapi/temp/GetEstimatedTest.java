package com.yongche.privateapi.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.OperateOrder;
import com.yongche.driver.api.tools.RequestForUsersAndOrders;
import com.yongche.driver.api.tools.RequestHeaderCofig;

public class GetEstimatedTest {
	public static String url;
	public static Map<String, String> getListPara;
	public static RequestForUsersAndOrders orderRequest;
	public DriverInfo driver;
	public RequestHeaderCofig header;
	public static OperateOrder operateOrder;
	@Before
	public void setUp(){
		url ="http://manhong.private-api.yongche.org:8888/Order";
		orderRequest = new RequestForUsersAndOrders("5");
//		driver = new DriverInfo();
        
		
		driver = new DriverInfo().getDriverWithImei();
		String oauthToken = GetConfigInfo.getOauth_token(driver);
		header = new RequestHeaderCofig(oauthToken);
		operateOrder = new OperateOrder(header, driver);
//		http://jiangzhixuan.private-api.yongche.org:8888/Order?method=getSelectCar&order_id=2005457689
	}

	//测试套餐价格有下车地址，价格为预估价格
	@Test
	public void testRentForHour_WithoutGetOffAddress() throws InterruptedException{	
		
		String order_id = orderRequest.getAsApOrderAndReturnOrderID();
		
		operateOrder.operateOrderStepByStep(order_id, orderRequest,orderRequest.defaultStartAddress);
//		
//		HttpRequest blrequest = HttpRequest
//	            .get(url,true,
//	                    "method","getSelectCar",
//	                    "order_id","2005458907");
//	
//	    String blacklist = blrequest.body();
//	    System.out.println("++++++++" + blacklist);
	}
	
	@Test
	public void testRentForHour_WithGetOffAddress() throws InterruptedException{	
		
		String order_id = orderRequest.getRentForHourOrderAndRetrunOrderID();
		
		TimeUnit.SECONDS.sleep(5);
		operateOrder.operateOrderStepByStep(order_id, orderRequest,orderRequest.defaultStartAddress);
		
//		HttpRequest blrequest = HttpRequest
//	            .get(url,true,
//	                    "method","getSelectCar",
//	                    "order_id","2005459256");
//	
//	    String blacklist = blrequest.body();
//	    System.out.println("++++++++" + blacklist);
	}
	
	
	@Test
	public void testSet_sendOff_WithGetOffAddress() throws InterruptedException{	
		
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
		String order_id = orderRequest.getSendOffAndRetrunOrderID();
		TimeUnit.SECONDS.sleep(10);
		operateOrder.operateOrderStepByStep(order_id, orderRequest,orderRequest.defaultStartAddress);
		
//		HttpRequest blrequest = HttpRequest
//	            .get(url,true,
//	                    "method","getSelectCar",
//	                    "order_id",order_id);
//	
//	    String blacklist = blrequest.body();
//	    System.out.println("++++++++" + blacklist);
	}
	
	@Test
	public void testSet_PickUp_WithGetOutOffAddress() throws InterruptedException{	
		
		String order_id = orderRequest.getPickUpAndRetrunOrderID();
		
		operateOrder.operateOrderStepByStep(order_id, orderRequest,orderRequest.defaultStartAddress);
//		
//		HttpRequest blrequest = HttpRequest
//	            .get(url,true,
//	                    "method","getSelectCar",
//	                    "order_id","2005460840");
//	
//	    String blacklist = blrequest.body();
//	    System.out.println("++++++++" + blacklist);
	}
	
	
	@Test
	public void testSet_DayRent_NoGetOffAddress() throws InterruptedException{	
		 
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
		String order_id = orderRequest.getRentForDayAndRetrunOrderID(24,getOffAddress);
		TimeUnit.SECONDS.sleep(5);
		operateOrder.operateOrderStepByStep(order_id, orderRequest,orderRequest.defaultStartAddress);
		
		//获取司机端预估费用
//		String responseString = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
//		int price = JsonPath.read(responseString, "$.msg.estimate_price");
//		assertTrue(price>=510);
	}
	
}
