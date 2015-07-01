package com.yongche.driver.api.tools;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;

public class OperateOrder {
	public static RequestUrlConfig url = new RequestUrlConfig("http://manhong.d.yongche.org","/Order/OperateOrder");
	public DriverInfo driver;
	public RequestHeaderCofig header;
	public static  HashMap<String,String> orderMap;
	public OperateOrder(RequestHeaderCofig header,DriverInfo driver){
		this.driver = driver;
		this.header = header;
		setOrderMap();
	}
	

	public void setOrderMap(){
		Long time = System.currentTimeMillis()/1000;
		orderMap = new HashMap<String, String>();
		orderMap.put("in_coord_type","baidu");
		orderMap.put("version", this.driver.driverAppVersion);
		orderMap.put("imei", this.driver.imei);
		orderMap.put("time", time.toString());
		orderMap.put("provider", "baidu");
	}
	
//	public OperateOrder(RequestHeaderCofig header,DriverInfo driver,RequestForUsersAndOrders orderRequest){
//		this.driver = driver;
//		this.header = header;
//		setOrderMap();
//	}
	
	
//	public void operateOrderStepByStep(String order_id,RequestForUsersAndOrders orderRequest) throws InterruptedException{
//		ArrayList<String> methodArrayList = new ArrayList<String>();
//		methodArrayList.add(0,"accept");
//		methodArrayList.add(1,"arrive");
//		methodArrayList.add(2,"start");
//		
//		
//		TimeUnit.SECONDS.sleep(10);
//		HttpRequest request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//		String response = request.body();
//		code = JsonPath.read(response,"$.code");
//		if(code ==200){
//			TimeUnit.SECONDS.sleep(3);
//			orderRequest.chooseTheOnlyDriver(driver.driverID,order_id);
//		}
//		
//		//就位
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(1));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			code = JsonPath.read(response,"$.code");
//		}
//		
//		Long startTime = System.currentTimeMillis()/1000;
//		//开始
//		if(code ==200){
//			orderMap.put("method", methodArrayList.get(2));
//			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
//			response = request.body();
//			System.out.println("开始的结果为："+ response);
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
//			System.out.println("结束的结果为："+ response);
////			request = new RequestMulitAssemble(url.getUrl(),orderMap,header).getHttpRequest_postMethod();
////			response= request.body();
//			code = JsonPath.read(response,"$.code");
//		}
	
//	/**
//	 * @param orderId
//	 * @param method:接单accept;就位：arrive；开始服务：start;结束：end;账单确认：bill_confirm
//	 * @param orderMap：order参数
//	 */
//	public int operateOrder(String orderId,String method,HashMap<String, String> startAddress){
//		//设置订单参数
//		orderMap.put("order_id",orderId);
//		orderMap.put("method",method);
//		orderMap.put("latitude",startAddress.get("start_lat"));
//		orderMap.put("longitude",startAddress.get("start_lng"));
//		
//		
//		HttpRequest request = new RequestMulitAssemble(url.getUrl(),this.orderMap,this.header).getHttpRequest_postMethod();
//		String response = request.body();
//		
//		System.out.println("************");
//		System.out.println("订单操作结果为："+ method +": " + response);
//		System.out.println("************");
//		
//		int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
//		
//		return code;
//	}
	
	
	/**
	 * 操作订单
	 * @param orderId
	 * @param method
	 * @return
	 */
	public int operateOrder(String orderId,String method){
		//设置订单参数
		orderMap.put("order_id",orderId);
		orderMap.put("method",method);
		
		
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),orderMap,this.header).getHttpRequest_postMethod();
		String response = request.body();
		
		System.out.println("************");
		System.out.println("订单操作结果为："+ method +": " + response);
		System.out.println("************");
		
		int code = Integer.parseInt(JsonPath.read(response,"$.code").toString());
		
		return code;
	}
	
	/**
	 * 
	 * @param orderID
	 * @param startAddress
	 */
	public int acceptOrder(String orderID,HashMap<String, String> startAddress){
		orderMap.put("latitude",startAddress.get("start_lat"));
		orderMap.put("longitude",startAddress.get("start_lng"));
		return this.operateOrder(orderID, "accept");
	}
	
	/**
	 * 到达
	 * @param orderID
	 * @param startAddress
	 */
	public int arrive(String orderID,HashMap<String, String> startAddress){
		orderMap.put("latitude",startAddress.get("start_lat"));
		orderMap.put("longitude",startAddress.get("start_lng"));
		return this.operateOrder(orderID, "arrive");
	}
	
	/**
	 * 开始服务
	 * @param orderID
	 * @param startAddress
	 */
	public int  start(String orderID,HashMap<String, String> startAddress){
		orderMap.put("latitude",startAddress.get("start_lat"));
		orderMap.put("longitude",startAddress.get("start_lng"));
		return this.operateOrder(orderID, "start");
	}
	
	/**
	 * 结束
	 * @param orderID
	 * @param startAddress
	 */
	public int end(String orderID,HashMap<String, String> startAddress){
		orderMap.put("latitude",startAddress.get("start_lat"));
		orderMap.put("longitude",startAddress.get("start_lng"));
		orderMap.put("supercritical", "1");
		orderMap.put("is_offline_mode", "0");
		return this.operateOrder(orderID, "end");
	}
	
	/**
	 * 支付
	 * @param orderID
	 * @param startAddress
	 */
	public int billConfirm(String orderID,long startTime,long endTime){
		orderMap.remove("provider");
		orderMap.remove("time");
		orderMap.put("mileage", "1");
		orderMap.put("deadhead_distance","0");
		orderMap.put("highway_amount","0.0");
		orderMap.put("parking_amount","0.0");
		orderMap.put("addons_amount","0.0");
		orderMap.put("addons_amount_src","0");
		orderMap.put("is_airport_service","0");
		orderMap.put("start_time",String.valueOf(startTime));
		orderMap.put("end_time",String.valueOf(endTime));
		return this.operateOrder(orderID, "bill_confirm");
	}
	
	
	public int operateOrderStepByStep(String orderID,RequestForUsersAndOrders orderRequest,HashMap<String, String> startAddres) throws InterruptedException{
		
		int code = 0;
		//接受订单，接单
		if(this.acceptOrder(orderID, startAddres) ==200){
			TimeUnit.SECONDS.sleep(3);
			
			code = orderRequest.chooseTheOnlyDriver(driver.driverID,orderID);
		}		
		
		//从开始--账单确认
		if(code ==200){
			code = operateOrderAfterArrive(orderID, startAddres);
		}
		
		return code;
	}
	
public int operateOrderAfterArrive(String orderID,HashMap<String, String> startAddres) throws InterruptedException{
		
		int code = 200;
		
		Long time = Long.parseLong(orderMap.get("time"));
		if(code == 200){
			//就位
			code = this.arrive(orderID, startAddres);
		}
		
		if(code == 200){
			//开始
			code = this.start(orderID, startAddres);
		}
		
		if(code == 200){
			//结束
			code = this.end(orderID, startAddres);
		}
		
		
		if(code == 200){
			Calendar calendar = Calendar.getInstance();//日历对象
			calendar.add(Calendar.MINUTE, +10);
			Long timeLong = calendar.getTimeInMillis()/1000;
			
			//账单确认
			code = this.billConfirm(orderID,time ,timeLong);	
		}
		
		return code;
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
