package com.yongche.driver.api.tools;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.Base64;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.data.UserInfo;

/**
 * 乘客端request
 * @author grace
 *
 */
public class RequestForUsersAndOrders {

	public static HashMap<String, String> data ;
	public static RequestUrlConfig url ;
	public static String user_Agent;
	public static String authorization;
	public RequestMulitAssemble request;
	public HashMap<String, String> defaultStartAddress,defaultEndAddress;
	public static UserInfo user;
	public  String driverType;
	private Integer[] minItems = new Integer[] { 00, 10,20,30, 40,50 };
	
	public RequestForUsersAndOrders(){}
	public RequestForUsersAndOrders(String driverType){
		this.driverType = driverType;
	}
	public static void setUser_agent(){
		user_Agent = "aWeidao/6.3.2(HUAWEI G520-0000; Android 4.1.2)";
	}
	
	public static void setUser_agent(String user_agent){
		user_Agent = user_agent;
	}
	
	/**
	 * 获取乘客端authorization
	 */
	static {
		url = new RequestUrlConfig("http://yongche-app-api.yongche.org", "/oauth2/token.php");
		setUser_agent();
		//如需更改用户也可以在这里修改，创建一个新的用户实例
		user = new UserInfo("16800001199", "kk测试","111111");
//		user = new UserInfo().orderUser();
		data =  new HashMap<String, String>();
		data.put("grant_type", "password");
		data.put("username", user.phoneNumber);
		data.put("password", user.password);
		data.put("device_token", "111111");
		data.put("uuid", "111111");
		data.put("macaddress", "111111");
		
		HttpRequest request = HttpRequest
				.post(url.getUrl())
				.userAgent(user_Agent).authorization("Basic "+Base64.encode("test:test")).form(data);
		String response = request.body();
		System.out.println("=======获取乘客端accessToken======"+response);
		String accessTokenString = JsonPath.read(response,"$.access_token");
		
		authorization = "Bearer " + accessTokenString;		
	}
	
	/**
	 * 下随叫随到订单，并返回订单ID
	 * @return
	 */
	public String getAsApOrderAndReturnOrderID(){
		//乘客端地址配置
		//openapi.yongche.org
		url = new RequestUrlConfig("http://openapi.yongche.org", "/v3/order");
		//设置下单上车地址
		HashMap<String, String> orderAddress = setStartAddress();
		data =  new HashMap<String, String>();
		data.put("start_address", orderAddress.get("start_address"));
		data.put("out_coord_type", "baidu");
		data.put("is_support_system_decision", "0");
		data.put("start_lng", orderAddress.get("start_lng"));
		data.put("start_lat", orderAddress.get("start_lat"));
		data.put("from_pos", orderAddress.get("from_pos"));	
		data.put("city", "bj");
		data.put("passenger_phone", user.phoneNumber);
		data.put("start_time", String.valueOf(System.currentTimeMillis()/1000));		
		data.put("is_asap", "1");
		data.put("passenger_name", user.userName);
		data.put("car_type_ids", driverType);
		data.put("in_coord_type", "baidu");
		data.put("product_type_id", "1");
		
		HttpRequest request = HttpRequest
				.post(url.getUrl())
				.userAgent(user_Agent).authorization(authorization)
				.acceptEncoding("gzip,deflate,sdch")
				.uncompress(true).
				form(data);
		System.out.println(request);
		String response = request.body();
		System.out.println("下单结果"+response);
		String orderID = JsonPath.read(response,"$.result.order_id");
		return orderID;
	}
	
	/**
	 * 时租:订单，返回OrderID
	 * @return orderID
	 */
	public String getRentForHourOrderAndRetrunOrderID(){
		System.out.print("开始下单");
		//设置订单参数
		data = setData_PersonalDescision_BJ(setStartAddress(), 1, 25);
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 时租：自定义下车地址
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getRentForHourOrderAndRetrunOrderID(int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(setStartAddress(), 1, 1,endAddress);		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 时租：自定义上下车地址
	 * @param startAddress :上车地址
	 * @param startTime：开始时间
	 * @param endAddress：下车地址
	 * @return
	 */
	public String getRentForHourOrderAndRetrunOrderID(HashMap<String, String> startAddress,int startTime,HashMap<String, String> endAddress){
		//设置订单参数
		data = setData_PersonalDescision_withGetOffAddress_BJ(startAddress, 1, 1,endAddress);
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 接机：返回OrderID，默认时间为：1小时之后可以选择的最早时间，默认地址无下车地址
	 * @return orderID
	 */
	public String getPickUpAndRetrunOrderID(){
		//设置订单参数
		data = setData_PersonalDescision_BJ(setAirportAddress(), 7, 1);
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	/**
	 * 接机：自定义下车地址和时间
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getPickUpAndRetrunOrderID(int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(setAirportAddress(), 7, startTime,endAddress);
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 接机：自定义上下车地址和时间。可以变更为接站服务
	 * @param startAddress
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getPickUpAndRetrunOrderID(HashMap<String, String> startAddress,int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(startAddress, 7, startTime,endAddress);
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}

	/**
	 * 下送机订单，返回OrderID,默认下车地址为：首都机场1号航站楼
	 * @return orderID
	 */
	public String getSendOffAndRetrunOrderID(){
		//设置订单参数		
		data = setData_PersonalDescision_withGetOffAddress_BJ(setStartAddress(), 8, 12, setAirportAddress());
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 送机：自定义开始时间，上下车地址。通过修改下车地址可以改为接站
	 * @param startAddress
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getSendOffAndRetrunOrderID(HashMap<String, String> startAddress,int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(startAddress, 8, 1, endAddress);
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 下日租订单，返回OrderID
	 * @return orderID
	 */
	public String getRentForDayAndRetrunOrderID(){
		//订单参数
//		Date date = new Date();
//		date = this.analyzeMinute(date);
//		Calendar calendar = Calendar.getInstance();//日历对象
//		calendar.setTime(date);
//		calendar.add(Calendar.HOUR_OF_DAY, +10);
//		Long timeLong =calendar.getTimeInMillis()/1000;
//		this.setCompanyAddress();
//		data =  new HashMap<String, String>();
//		data.put("start_address", orderAddress.get("start_address"));
//		data.put("start_lng", orderAddress.get("start_lng"));
//		data.put("start_lat", orderAddress.get("start_lat"));
//		data.put("from_pos", orderAddress.get("from_pos"));
//		data.put("out_coord_type", "baidu");
//		data.put("is_support_system_decision", "0");
//		data.put("city", "bj");
//		data.put("passenger_phone", user.phoneNumber);		
////		data.put("start_time", String.valueOf(System.currentTimeMillis()));
//		data.put("is_asap", "0");
//		data.put("passenger_name", user.userName);
//		data.put("car_type_ids", driverType);
//		data.put("in_coord_type", "baidu");
//		data.put("product_type_id", "12");
//		data.put("time_length", "28800");
//		data.put("start_time", String.valueOf(timeLong));
		
		data = setData_PersonalDescision_BJ(setStartAddress(), 12, 4);
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 日租：自定义开始时间和下车地址
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getRentForDayAndRetrunOrderID(int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(setStartAddress(), 12, startTime,endAddress);	
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 日租：自定义开始时间和上下车地址
	 * @param startAddress
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getRentForDayAndRetrunOrderID(HashMap<String, String> startAddress,int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(startAddress, 12, startTime,endAddress);	
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 下半日租订单，返回OrderID
	 * @return orderID
	 */
	public String getRentForHalfDayAndRetrunOrderID(){
//		//订单参数
//		Date date = new Date();
//		date = this.analyzeMinute(date);
//		Calendar calendar = Calendar.getInstance();//日历对象
//		calendar.setTime(date);
//		calendar.add(Calendar.HOUR_OF_DAY, +4);
//		Long timeLong =calendar.getTimeInMillis()/1000;
//		this.setCompanyAddress();
//		data =  new HashMap<String, String>();
//		data.put("start_address", orderAddress.get("start_address"));
//		data.put("start_lng", orderAddress.get("start_lng"));
//		data.put("start_lat", orderAddress.get("start_lat"));
//		data.put("from_pos", orderAddress.get("from_pos"));
//		data.put("out_coord_type", "baidu");
//		data.put("is_support_system_decision", "0");
//		data.put("city", "bj");
//		data.put("passenger_phone", user.phoneNumber);	;
//		data.put("is_asap", "0");
//		data.put("passenger_name", user.userName);
//		data.put("car_type_ids", driverType);
//		data.put("in_coord_type", "baidu");
//		data.put("product_type_id", "11");
//		data.put("time_length", "28800");
//		data.put("start_time", String.valueOf(timeLong));
		data = setData_PersonalDescision_BJ(setStartAddress(), 11,4);	
		
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	/**
	 * 半日租：自定义上车时间和下车地址
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getRentForHalfDayAndRetrunOrderID(int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(setStartAddress(), 11, startTime,endAddress);	
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 半日租：自定义上下车地址和上车时间
	 * @param startAddress
	 * @param startTime
	 * @param endAddress
	 * @return
	 */
	public String getRentForHalfDayAndRetrunOrderID(HashMap<String, String> startAddress, int startTime,HashMap<String, String> endAddress){
		data = setData_PersonalDescision_withGetOffAddress_BJ(startAddress, 11, startTime,endAddress);	
		//下单
		String orderID = this.order(data);
		return orderID;
	}
	
	/**
	 * 选择司机
	 * @param driverID
	 * @param orderID
	 * @return 返回ret_code,200则选择司机成功
	 */
	public int chooseTheOnlyDriver(String driverID,String orderID){
		int code = 0;
		RequestUrlConfig url= new RequestUrlConfig("http://yongche-app-api.yongche.org", "/V3/order/getselectdriver");
		data =  new HashMap<String, String>();
		data.put("order_id", orderID);
		data.put("count", "5");
		data.put("driver_ids", driverID+",");
				
		HttpRequest request = HttpRequest
				.get(url.getUrl(),data,true)
				.userAgent(user_Agent).authorization(authorization)
				.acceptEncoding("gzip,deflate,sdch")
				.uncompress(true);
		String response = request.body();
		if(Integer.parseInt(JsonPath.read(response,"$.result.ret_code").toString())==200){
			data.remove("count");
			data.remove("driver_ids");
			data.put("driver_id", driverID);
			data.put("corporate_id", "");
			data.put("coupon_member_id", "");
			url.path= "/V3/order/decisiondriver";
			request = HttpRequest
					.post(url.getUrl())
					.userAgent(user_Agent).authorization(authorization)
					.acceptEncoding("gzip,deflate,sdch")
					.uncompress(true).form(data);
			response = request.body();
			code =Integer.parseInt(JsonPath.read(response, "$.ret_code").toString()) ;
		}
		return code;
	}
	
	/**
	 * 参数：para：请求参数
	 * 结果：返回order_id
	 */
	public  String order(Map<String, String> para){
		url = new RequestUrlConfig("http://openapi.yongche.org", "/v3/order");
		HttpRequest request = HttpRequest
				.post(url.getUrl())
				.userAgent(user_Agent).authorization(authorization)
				.acceptEncoding("gzip,deflate,sdch")
				.uncompress(true).
				form(data);
		System.out.println(request);
		String response = request.body();
//		System.out.println("下单结果"+request.f);
		System.out.println("下单结果"+response);
		String orderID = JsonPath.read(response,"$.result.order_id");
		return orderID;
	}
	
	/**
	 * 转换给定的date
	 * @param date
	 * @return
	 */
	public Date analyzeMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.MINUTE) % minItems[1] != 0)
			calendar.add(Calendar.MINUTE,
					minItems[1] - calendar.get(Calendar.MINUTE) % minItems[1]);
		return calendar.getTime();
	}
	
	/**
	 * 分配下单地址,可配置司机端附近地址以便接单
	 * @return
	 */
	public HashMap<String, String> setStartAddress(){
		HashMap<String, String> orderAddress = new HashMap<String, String>();
		//公司地址
		orderAddress.put("start_lat", "39.984050");
		orderAddress.put("start_lng", "116.307691");
		orderAddress.put("from_pos", "中国技术交易大厦");
		orderAddress.put("start_address", "实验中学---中国技术交易大厦");
		
	
		//五四排球场
//		orderAddress.put("start_lat", "39.985933");
//		orderAddress.put("start_lng", "116.307964");
//		orderAddress.put("from_pos", "五四运动场");
//		orderAddress.put("start_address", "五四运动场");
		
		//顺义区地址
//		orderAddress.put("from_pos", "顺义区");
//		orderAddress.put("start_address", "不要接单不要接单");
//		orderAddress.put("start_lat", "39.894010");
//		orderAddress.put("start_lng", "116.460809");
		
		defaultStartAddress = orderAddress;
		return orderAddress;
	}
	
	public HashMap<String, String> setGetOffAddress(){
		HashMap<String, String> getOffAddress = new HashMap<String, String>();
		getOffAddress.put("end_lat", "39.985933");
		getOffAddress.put("end_lng", "116.307964");
		getOffAddress.put("to_pos", "五四运动场");
//		getOffAddress.put("end_address", "五四运动场");
		
//		39.894010636632,116.4608090092
		
//		getOffAddress.put("to_pos", "顺义区");
//		getOffAddress.put("end_lat", "39.894010");
//		getOffAddress.put("end_lng", "116.460809");
////		
//		getOffAddress.put("end_lat", "40.06684");
//		getOffAddress.put("end_lng", "116.589403");
//		getOffAddress.put("to_pos", "机场西路5号");
		
		return getOffAddress;
	}
	
	/**
	 * 分配机场下单地址
	 * @return
	 */
	public HashMap<String, String> setAirportAddress(){
		HashMap<String, String> address = new HashMap<String, String>();
		address.put("start_lat", "40.070321");
		address.put("start_lng", "116.58848");
		address.put("from_pos", "首都机场1号航站楼");
		address.put("start_address", "不要接单不要接单");
		
		address.put("end_lat", "40.070321");
		address.put("end_lng", "116.58848");
		address.put("to_pos", "首都机场1号航站楼");
		address.put("end_address", "不要接单不要接单");
		
		defaultStartAddress = address;
		return address;
	}
	
	/**
	 * 设置接单司机的类型
	 */
	public void setDriverType(DriverInfo driver){
		this.driverType = driver.driverType;
	}
	
	public HashMap<String, String> setData_SystemDescision_BJ(HashMap<String, String> startAdress,int productTypeID,int startTime){
		Date date = new Date();
		date = this.analyzeMinute(date);
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, +startTime);
		Long timeLong =calendar.getTimeInMillis()/1000;
		HashMap<String, String> data =  new HashMap<String, String>();
		data.put("start_address", startAdress.get("start_address"));
		data.put("start_lng", startAdress.get("start_lng"));
		data.put("start_lat", startAdress.get("start_lat"));
		data.put("from_pos", startAdress.get("from_pos"));
		data.put("out_coord_type", "baidu");
		data.put("is_support_system_decision", "1");
		data.put("city", "bj");
		data.put("passenger_phone", user.phoneNumber);
		data.put("is_asap", "0");
		data.put("passenger_name", user.userName);
		data.put("car_type_ids", driverType);
		data.put("in_coord_type", "baidu");
		data.put("product_type_id", String.valueOf(productTypeID));
		data.put("time_length", String.valueOf(setTimeLength(productTypeID)));
		data.put("start_time", String.valueOf(timeLong));
		
		return data;
	}
	
	public HashMap<String, String> setData_SystemDescision_withGetOffAddress_BJ(HashMap<String, String> startAdress,int productTypeID,int startTime,HashMap<String, String> getOffAddress){
		
		HashMap<String, String> data = this.setData_SystemDescision_BJ(startAdress, productTypeID, startTime);
		//设置下车地址
		data.put("end_lng", getOffAddress.get("end_lng"));
		data.put("end_lat", getOffAddress.get("end_lat"));
		data.put("to_pos", getOffAddress.get("to_pos"));
		
		return data;
	}
	
	
	/**
	 * 如果修改下单城市：需要改：city,address(setAddress)
	 * @param startAdress
	 * @param productTypeID
	 * @param startTime
	 * @return
	 */
	public HashMap<String, String> setData_PersonalDescision_BJ(HashMap<String, String> startAdress,int productTypeID,int startTime){
		Date date = new Date();
		date = this.analyzeMinute(date);
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, +startTime);
		Long timeLong =calendar.getTimeInMillis()/1000;
		HashMap<String, String> data =  new HashMap<String, String>();
		data.put("start_address", startAdress.get("start_address"));
		data.put("start_lng", startAdress.get("start_lng"));
		data.put("start_lat", startAdress.get("start_lat"));
		data.put("from_pos", startAdress.get("from_pos"));
		data.put("out_coord_type", "baidu");
		data.put("is_support_system_decision", "0");
		data.put("city", "bj");
		data.put("passenger_phone", user.phoneNumber);
		data.put("is_asap", "0");
		data.put("passenger_name", user.userName);
		data.put("car_type_ids", driverType);
		data.put("in_coord_type", "baidu");
		data.put("product_type_id", String.valueOf(productTypeID));
		data.put("time_length", String.valueOf(setTimeLength(productTypeID)));
		data.put("start_time", String.valueOf(timeLong));
		
		return data;
	}
	
	public HashMap<String, String> setData_PersonalDescision_withGetOffAddress_BJ(HashMap<String, String> startAdress,int productTypeID,int timeLength,HashMap<String, String> getOffAddress){
		
		HashMap<String, String> data = this.setData_PersonalDescision_BJ(startAdress, productTypeID, timeLength);
		//设置下车地址
		data.put("end_lng", getOffAddress.get("end_lng"));
		data.put("end_lat", getOffAddress.get("end_lat"));
		data.put("to_pos", getOffAddress.get("to_pos"));
		
		return data;
	}
	
	public int setTimeLength(int productTypeID){
		int timeLength_second = 3600;
		//设置时租时长
		if(productTypeID ==1){
			timeLength_second = 3600;
		}
		//接送机
		if(productTypeID ==7){
			timeLength_second = 3600;
		}
		if(productTypeID ==8){
			timeLength_second = 3600;
		}
		//半日租日租
		if(productTypeID ==11){
			timeLength_second = 3600*4;
		}
		if(productTypeID ==12){
			timeLength_second = 3600*8;
		}
		return timeLength_second;
	}
	/**
	 * 分配Reserving oder下单地址,可配置司机端附近地址以便接单
	 * @return
	 */
	
	public HashMap<String, String> setStartAddressReserving(){
		HashMap<String, String> orderAddress = new HashMap<String, String>();
		//公司地址
		orderAddress.put("start_lat", "20.056478");
		orderAddress.put("start_lng", "110.34317");
		orderAddress.put("from_pos", "海南大学南门");
		orderAddress.put("start_address", "实验中学---海南大学南门");
		
	
		//五四排球场
//		orderAddress.put("start_lat", "39.985933");
//		orderAddress.put("start_lng", "116.307964");
//		orderAddress.put("from_pos", "五四运动场");
//		orderAddress.put("start_address", "五四运动场");
		
		//顺义区地址
//		orderAddress.put("from_pos", "顺义区");
//		orderAddress.put("start_address", "不要接单不要接单");
//		orderAddress.put("start_lat", "39.894010");
//		orderAddress.put("start_lng", "116.460809");
		
		defaultStartAddress = orderAddress;
		return orderAddress;
	}
	
	/**
	 * 下预约用车订单，并返回订单ID
	 * @return
	 */
	public String getReservingOrderAndReturnOrderID(){
		//乘客端地址配置
		//openapi.yongche.org
		System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		url = new RequestUrlConfig("http://openapi.yongche.org", "/v3/order");
		//设置下单上车地址
		HashMap<String, String> orderAddress = setStartAddressReserving();
		data =  new HashMap<String, String>();
		data.put("start_address", orderAddress.get("start_address"));
		data.put("out_coord_type", "baidu");
		data.put("is_support_system_decision", "0");
		data.put("start_lng", orderAddress.get("start_lng"));
		data.put("start_lat", orderAddress.get("start_lat"));
		data.put("from_pos", orderAddress.get("from_pos"));	
		data.put("city", "haikou");
		data.put("passenger_phone", user.phoneNumber);
		data.put("start_time", String.valueOf(System.currentTimeMillis()/1000+(24*60*60*4)));		
		data.put("is_asap", "0");
		data.put("passenger_name", user.userName);
		data.put("car_type_ids", driverType);
		data.put("in_coord_type", "baidu");
		data.put("product_type_id", "1");
		
		HttpRequest request = HttpRequest
				.post(url.getUrl())
				.userAgent(user_Agent).authorization(authorization)
				.acceptEncoding("gzip,deflate,sdch")
				.uncompress(true).
				form(data);
	  
		System.out.println(request);
		String response = request.body();
		System.out.println("下单结果"+response);
		String orderID = JsonPath.read(response,"$.result.order_id");
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBB"+orderID);
		return orderID;
	}
}
