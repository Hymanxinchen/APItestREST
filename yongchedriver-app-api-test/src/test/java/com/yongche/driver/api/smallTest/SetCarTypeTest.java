package com.yongche.driver.api.smallTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.DBHelper;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class SetCarTypeTest {
	
    public static final String db = "yc_crm_common";
    public static DBHelper dbHelper;
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	public static Map<String, String> getListPara;
	@BeforeClass
	public static void setUp(){
		
		dbHelper = new DBHelper();
		driver = new DriverInfo("16800000357","程思测试_上海");		
		driver.imei = "test-shanghai-driver";
		driver.driverID = "50005046";
		driver.driverAppVersion = "51";
		driver.vehicle_number = "9999";
		url = new RequestUrlConfig("http://huangyang.d.yongche.org","/v4/Driver/SetCarType");
		
		String oauthToken = GetConfigInfo.getOauth_token(driver);
		
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
				
	}
	@After
	public void tearDown(){
	}

	@Test
	public void testdegradeSuccess(){
		int newCarType = 2;
		//修改车型为商务车型
		String sqlString = "update car,driver set car.base_car_type_id = 5 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);
		//给车型降级
		getListPara.put("car_type_id",String.valueOf(newCarType));
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();			
		//获取司机目前车型
		RequestUrlConfig memberUrl = new RequestUrlConfig("http://huangyang.d.yongche.org","/Driver/Member");
		getListPara.remove("car_type_id");
		response = new RequestMulitAssemble(memberUrl.getUrl(),getListPara,header).getResponse_getMethod();	
		String carType = JsonPath.read(response, "$.msg.member_info.car_type");
		assertEquals("舒适车型",carType);
	}
	
	@Test
	public void testChangeTwiceSuccess(){
		//修改车型为奢华车型
		String sqlString = "update car,driver set car.base_car_type_id = 4 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);
		//给车型降级
		getListPara.put("car_type_id","2");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		//再次降级
		getListPara.put("car_type_id","3");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		//获取司机目前车型
		RequestUrlConfig memberUrl = new RequestUrlConfig("http://huangyang.d.yongche.org","/Driver/Member");
		getListPara.remove("car_type_id");
		response = new RequestMulitAssemble(memberUrl.getUrl(),getListPara,header).getResponse_getMethod();	
		String carType = JsonPath.read(response, "$.msg.member_info.car_type");
		assertEquals("豪华车型",carType);
	}
	
	@Test
	public void testChangeBackSuccess(){
		//修改车型为奢华车型
		String sqlString = "update car,driver set car.base_car_type_id = 4 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);
		//给车型降级
		getListPara.put("car_type_id","2");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		//再次降级
		getListPara.put("car_type_id","3");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		//再次降级
		getListPara.put("car_type_id","4");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		//获取司机目前车型
		RequestUrlConfig memberUrl = new RequestUrlConfig("http://huangyang.d.yongche.org","/Driver/Member");
		getListPara.remove("car_type_id");
		response = new RequestMulitAssemble(memberUrl.getUrl(),getListPara,header).getResponse_getMethod();	
		String carType = JsonPath.read(response, "$.msg.member_info.car_type");
		assertEquals("奢华车型",carType);
	}
	
	@Test
	public void testBaseCarTypeNotChangedSuccess(){
		//修改车型为奢华务车型
		String sqlString = "update car,driver set car.base_car_type_id = 4 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);
		//给车型降级
		getListPara.put("car_type_id","3");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		
		sqlString = "select base_car_type_id from car,driver where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		int actualCarTypeID = dbHelper.connectDBAndExcuteWithJsonObject(sqlString,db).getInt("base_car_type_id");
		assertEquals(4,actualCarTypeID);
	}

	@Test
	public void testKeepSameOptionsSuccess(){
		//修改车型为商务车型
		String sqlString = "update car,driver set car.base_car_type_id = 4 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);
		//给车型降级
		getListPara.put("car_type_id","2");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();	
		
		RequestUrlConfig getHeaderCofig = new RequestUrlConfig("http://huangyang.d.yongche.org","/v4/Driver/GetLowCarType");
		response = new RequestMulitAssemble(getHeaderCofig.getUrl(),getListPara,header).getResponse_getMethod();
		
		ArrayList<Integer> carList =JsonPath.read(response, "$.msg.car_type_list[*].car_type_id");
		assertEquals(4,carList.size());
	}
	
	@Test
	public void testCarTypeIDEmpty(){
		//给车型降级
		getListPara.put("car_type_id","");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(400,Integer.parseInt(JsonPath.read(response, "$.msg.ret_code").toString()));
	}
	
	@Test
	public void testNOCarTypeID(){
		//给车型降级
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(400,Integer.parseInt(JsonPath.read(response, "$.msg.ret_code").toString()));
	}
	

	@Test
	public void testWrongCarType_char(){
		//给车型降级
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(400,Integer.parseInt(JsonPath.read(response, "$.msg.ret_code").toString()));
	}
	@Test
	public void testWrongCarTypeID(){
		getListPara.put("car_type_id","16");
		//给车型降级
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(499,Integer.parseInt(JsonPath.read(response, "$.msg.ret_code").toString()));
	}
	
	@Test
	public void testNOImei(){
		HashMap<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("car_type_id","1");
		
		//给车型降级
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(0,JsonPath.read(response, "$.code"));
	}
	
	//测试存在订单时，
	@Test
	public void testChangeFailed_OrderExisted(){
		
		//设置车型
		String sqlString = "update car,driver set car.base_car_type_id = 4 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
		dbHelper.connectDBAndExcute_NoResult(sqlString,db);

		DriverInfo driver = new DriverInfo().getDriverWithImei();		
		
        String oauthToken = GetConfigInfo.getOauth_token(driver);
		
		RequestHeaderCofig  header = new RequestHeaderCofig(oauthToken);
		
		HashMap<String, String> getListPara = new HashMap<String, String>();
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("imei",driver.imei);
		getListPara.put("car_type_id","1");
				//给车型降级
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_postMethod();
		
		assertEquals(String.valueOf(499),JsonPath.read(response, "$.msg.ret_code"));
	}

}
