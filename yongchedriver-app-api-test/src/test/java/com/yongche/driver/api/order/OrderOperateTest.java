package com.yongche.driver.api.order;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.yongche.driver.api.tools.RequestForUsersAndOrders;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class OrderOperateTest {
	public static RequestHeaderCofig header;
	public static HttpRequest request;
	public static String response;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static RequestForUsersAndOrders orderRequest;
	public static int code;
	
	@BeforeClass
	public static void setUp() throws InterruptedException{

		
		
		//下单
		//String order_id = orderRequest.
				//getRentForHalfDayAndRetrunOrderID();
				//getRentForDayAndRetrunOrderID();
				//getSendOffAndRetrunOrderID();
				//getPickUpAndRetrunOrderID();
				//getRentForHourOrderAndRetrunOrderID();
				//orderRequest.getAsApOrderAndReturnOrderID();
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		driver = new DriverInfo().getDriverWithImei();		
		header = new RequestHeaderCofig(oauthToken);
		url = new RequestUrlConfig("/Order/OperateOrder");
		orderRequest = new RequestForUsersAndOrders("3");

			
	}

	@After
	public void tearDown(){
		
	}
	//随叫随到订单
	//@Test
	public void testOrder_ASAP() throws InterruptedException{
		String order_id = orderRequest.getAsApOrderAndReturnOrderID();
		TimeUnit.SECONDS.sleep(5);
		
		
//		HttpRequest blrequest = HttpRequest
//                .get("http://jiangzhixuan.private-api.yongche.org:8888/BlackList",true,
//                        "method","oprateAction",
//                        "user_id","5127",
//                        "driver_id","670",
//                        "type","1",
//                        "action","0",
//                        "service_order_id","0");
//
//        String blacklist = blrequest.body();
//        System.out.println("++++++++" + blacklist);
		//operateOrderStepByStep(order_id);
//		assertEquals(200, code);
	}
	//时租订单
   // @Test
	public void testOrder_RentForHour() throws InterruptedException{
		String order_id = orderRequest.getRentForHourOrderAndRetrunOrderID();
		TimeUnit.SECONDS.sleep(4);
		System.out.println(order_id);
		operateOrderStepByStep(order_id);
		//assertEquals(code, 200);
	}
	//接机订单
	//@Test
	public void testOrder_PickUp() throws InterruptedException{
		String order_id = orderRequest.getPickUpAndRetrunOrderID();
//		operateOrderStepByStep(order_id);
//		assertEquals(code, 200);
	}
	
//	送机订单
//	@Test
	public void testOrder_SendOff() throws InterruptedException{
		String order_id = orderRequest.getSendOffAndRetrunOrderID();
		
		operateOrderStepByStep(order_id);
	}
	//日租订单
	//@Test
	public void testOrder_RentForDay() throws InterruptedException{
		String order_id = orderRequest.getRentForDayAndRetrunOrderID();
		TimeUnit.SECONDS.sleep(2);
		operateOrderStepByStep(order_id);
		//assertEquals(code, 200);
	}
	//半日租订单
//	@Test
	public void testOrder_RentForHalfDay() throws InterruptedException{
		for(int i = 0 ; i<5; i++){
			String order_id = orderRequest.getRentForHalfDayAndRetrunOrderID();
		}
//		operateOrderStepByStep(order_id);
	}
	
	//预定订单
	@Test
	public void testOrder_ReservingOrder() throws InterruptedException{
	
			String order_id = orderRequest.getReservingOrderAndReturnOrderID();
		//	operateOrderStepByStep(order_id);
	
		operateOrderStepByStep(order_id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void operateOrderStepByStep(String order_id) throws InterruptedException{
		ArrayList<String> methodArrayList = new ArrayList<String>();
		methodArrayList.add(0,"accept");
		methodArrayList.add(1,"arrive");
		methodArrayList.add(2,"start");
		
		Long time = System.currentTimeMillis()/1000;
		Map<String,String> orderMap = new HashMap<String, String>();
		orderMap.put("order_id",order_id);
		orderMap.put("method",methodArrayList.get(0));
		orderMap.put("in_coord_type","baidu");
		orderMap.put("latitude",orderRequest.setStartAddress().get("start_lat"));
		orderMap.put("longitude",orderRequest.setStartAddress().get("start_lng"));
		orderMap.put("version", driver.driverAppVersion);
		orderMap.put("imei", driver.imei);
		orderMap.put("time", time.toString());
		orderMap.put("provider", "baidu");
		System.out.println("the time is " + time.toString());
		TimeUnit.SECONDS.sleep(3);
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
		response = request.body();
		code = JsonPath.read(response,"$.code");
		
		//if(code ==200){
		     
			TimeUnit.SECONDS.sleep(10);
			orderRequest.chooseTheOnlyDriver(driver.driverID,order_id);
	//	}
		
//		//就位
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(1));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			System.out.println("就位："+ response);
//			code = JsonPath.read(response,"$.code");
//		}
//		
//		Long startTime = System.currentTimeMillis()/1000;
////		//开始
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(2));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			System.out.println("开始的结果为："+ response);
//			code = JsonPath.read(response,"$.code");
//		}
//		
////		//结束
//		if(code ==200){
//			orderMap.put("supercritical", "1");
//			orderMap.put("is_offline_mode", "0");
//			orderMap.put("method", "end");
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response= request.body();
//			System.out.println("结束的结果为："+ response);
//			code = JsonPath.read(response,"$.code");
//		}
//		
//		//付款
//		if(code ==200){
//			Calendar calendar = Calendar.getInstance();//日历对象
//			//时租订单，1小时之后的
//			calendar.add(Calendar.MINUTE, +1);
//			Long timeLong = calendar.getTimeInMillis()/1000;
//			orderMap.remove("provider");
//			orderMap.remove("latitude");
//			orderMap.remove("longitude");
//			orderMap.remove("time");
//			orderMap.put("method", "bill_confirm");
//			orderMap.put("mileage", "1");
//			orderMap.put("deadhead_distance","0");
//			orderMap.put("highway_amount","0.0");
//			orderMap.put("parking_amount","0.0");
//			orderMap.put("addons_amount","0.0");
//			orderMap.put("addons_amount_src","0");
//			orderMap.put("is_airport_service","0");
//			orderMap.put("start_time",startTime.toString());
//			orderMap.put("end_time",timeLong.toString());
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response= request.body();
//			System.out.println("结束并且付款的结果为："+ response);
////			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
////			response= request.body();
//			code = JsonPath.read(response,"$.code");
//		}
			
	}
	
	
//	public void operateOrderStepByStep(String order_id,Map<String, Double> addressMap) throws InterruptedException{
//		ArrayList<String> methodArrayList = new ArrayList<String>();
//		methodArrayList.add(0,"accept");
//		methodArrayList.add(1,"arrive");
//		methodArrayList.add(2,"start");
//		
//		Long time = System.currentTimeMillis()/1000;
//		Map<String,String> orderMap = new HashMap<String, String>();
//		orderMap.put("order_id",order_id);
//		orderMap.put("method",methodArrayList.get(0));
//		orderMap.put("in_coord_type","baidu");
//		orderMap.put("latitude",addressMap.get("lat").toString());
//		orderMap.put("longitude",addressMap.get("lng").toString());
//		orderMap.put("version", driver.driverAppVersion);
//		orderMap.put("imei", driver.imei);
//		orderMap.put("time", time.toString());
//		orderMap.put("provider", "baidu");
//		System.out.println("the time is " + time.toString());
//		TimeUnit.SECONDS.sleep(3);
//		HttpRequest request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//		response = request.body();
//		code = JsonPath.read(response,"$.code");
//		if(code ==200){
//			TimeUnit.SECONDS.sleep(3);
//			orderRequest.chooseTheOnlyDriver(driver.driverID,order_id);
//		}
//		
//		
//		//就位
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(1));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			code = JsonPath.read(response,"$.code");
//		}
//		
////		Long startTime = System.currentTimeMillis()/1000;
//		//开始
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(2));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			code = JsonPath.read(response,"$.code");
//		}
//		
//		//结束
//		if(code ==200){
//			orderMap.put("supercritical", "1");
//			orderMap.put("is_offline_mode", "0");
//			orderMap.put("method", "end");
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response= request.body();
//			code = JsonPath.read(response,"$.code");
//		}
//		
//		//付款
//		if(code ==200){
//			
//			Calendar calendar = Calendar.getInstance();//日历对象
//			//时租订单，1小时之后的
//			calendar.add(Calendar.MINUTE, +3);
//			orderMap.remove("provider");
//			orderMap.remove("latitude");
//			orderMap.remove("longitude");
//			orderMap.remove("time");
//			orderMap.put("method", "bill_confirm");
//			orderMap.put("mileage", "1");
//			orderMap.put("deadhead_distance","0");
//			orderMap.put("highway_amount","0.0");
//			orderMap.put("parking_amount","0.0");
//			orderMap.put("addons_amount","0.0");
//			orderMap.put("addons_amount_src","0");
//			orderMap.put("is_airport_service","0");
////			orderMap.put("start_time",startTime.toString());
////			orderMap.put("end_time",timeLong.toString());
//			System.out.print("结账的参数" + orderMap.toString());
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response= request.body();
//			code = JsonPath.read(response,"$.code");
//		}
//			
//	}
}
