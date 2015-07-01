package com.yongche.driver.api.data;

import com.yongche.driver.api.tools.RequestUrlConfig;




public class DriverInfo {

	public String cellPhone;
	public String name;
	public String driverID;
	public String imei;
	public String vehicle_number;
	public String orderID;
	public String driverAppVersion;
	public String orderSpecificDay;
	public String driverIncomeMonth;
	public String driverType;
	public int rentHourFee;
	public int sendOffFee;
	public int pickUpFee;
	public int dayFee;
	public int halfDayFee;
	
	public static boolean isOnLine = RequestUrlConfig.IS_ONLINE_TEST;
		
	public DriverInfo(){
	}
	
	public DriverInfo(String cellPhone,String name){
		this.cellPhone = cellPhone;
		this.name = name;
	}
	
	public DriverInfo getDriverWithImei(){
		if(!isOnLine){
//			DriverInfo driver = new DriverInfo("16891929394", "三星测试");	
//			driver.vehicle_number = "1990";
//			driver.imei = "359357050178038";
//			driver.driverID = "1271";	
//			DriverInfo driver = new DriverInfo("16800008899", "刘先生");
//			driver.vehicle_number = "9988";
//			driver.imei = "865931022855837";
//			// "352621063817677";
//			// "867496010827329";
//
//			driver.driverID = "50006244";
//			driver.driverAppVersion = "55";
			DriverInfo driver = new DriverInfo("16800008899", "刘先生");
			driver.vehicle_number = "9988";
			driver.imei = "865931022855837";
			// "352621063817677";
			// "867496010827329";

			driver.driverID = "50006244";
			driver.driverAppVersion = "55";
//			DriverInfo driver = new DriverInfo("16800000222", "程思测试");	
//			driver.vehicle_number = "1111";
//			driver.imei = //"352621063817677";
//					"867496010827329";
//			driver.driverID = "670";
//			driver.driverAppVersion = "55";
//			
//			driver.driverType = "5";
//			driver.driverAppVersion = "51";
//			driver.orderID = "2005426632";//2005422756";
//			driver.orderSpecificDay = "2014-09-22";
//			driver.driverIncomeMonth = "2014-09";
			
//			DriverInfo driver = new DriverInfo("13391949871","程思测试_上海");		
//			driver.imei = "a0000042d9c021";
//			driver.driverID = "690";
//			driver.driverAppVersion = "51";
//			driver.vehicle_number = "0011";
			
//			DriverInfo driver = new DriverInfo("16820072007","程思测试_上海");		
//			driver.imei = "863175021265430";
//			driver.driverID = "863";
//			driver.driverAppVersion = "51";
//			driver.vehicle_number = "9999";
			
//			DriverInfo driver = new DriverInfo("16800000357","程思测试_上海");		
//			driver.imei = "test-shanghai-driver";
//			driver.driverID = "859";
//			driver.driverAppVersion = "51";
//			driver.vehicle_number = "9999";
			
//			DriverInfo driver = new DriverInfo("13391949871","程思测试_上海");		
//			driver.imei = "a0000042d9c021";
//			driver.driverID = "690";
//			driver.driverAppVersion = "51";
//			driver.vehicle_number = "0009";
			
//			DriverInfo driver = new DriverInfo("18500197939","程思测试_上海");		
//			driver.imei = "863175021265430";
//			driver.driverID = "656";
//			driver.driverAppVersion = "51";
//			driver.vehicle_number = "2319";
            
            //张三
//			DriverInfo driver = new DriverInfo("16800636773", "张三");
//			driver.vehicle_number = "2345";
//			driver.imei = "861138027278122"; //=== MX3 ===
//			driver.driverID = "924";
//			
//			driver.driverAppVersion = "41";
//			driver.orderID = "2005426632"; //2005422756";
//			driver.orderSpecificDay = "2014-09-22";
//			driver.driverIncomeMonth = "2014-09";
//			driver.driverType = "2";
			
			return driver;
		}
		else{
			;
			DriverInfo driver = new DriverInfo("16800000222", "程思测试");	
			driver.vehicle_number = "8101";
			driver.imei = "867496010827329";
			driver.driverAppVersion = "41";
			driver.orderID = "35746187";
			driver.orderSpecificDay = "2014-11-14";
			driver.driverIncomeMonth = "2014-11";
			return driver;
		}		
	}	
}
