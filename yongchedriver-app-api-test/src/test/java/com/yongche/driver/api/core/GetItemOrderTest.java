package com.yongche.driver.api.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThan;

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

public class GetItemOrderTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static int code;
	
	@BeforeClass
	public static void setUp(){
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		driver = new DriverInfo().getDriverWithImei();		
		header = new RequestHeaderCofig(oauthToken);
		url = new RequestUrlConfig("/Order/GetItemOrder");
												
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		getListPara.put("order_id",driver.orderID);
//		getListPara.put("order_id","2005450107");
		getListPara.put("out_coord_type", "baidu");
		
		
		System.out.println(header.getHeaderString());
		HttpRequest request = new RequestMulitAssemble(url.getUrl(),getListPara,header).getHttpRequest_getMethod();
		code = request.code();
		response = request.body();
		System.out.println("response is :" + response);
	}
	
	@After
	public void tearDown(){
	}


	@Test
	public void testResponseCodeSuccess(){
		 assertEquals(200, code);
	}
	
	@Test
	public void testCodeSuccess(){
		with(response).assertThat("$.code", equalTo(200));
	}
	
	@Test
	public void testStatusNotNull(){
		with(response).assertNotNull("$.msg.order.status");
	}
	@Test
	public void testBalance_statusNotNull(){
		with(response).assertNotNull("$.msg.order.balance_status");
	}
	
	@Test
	public void testCityNotNull(){
		with(response).assertNotNull("$.msg.order.city");
	}
	
	@Test
	public void testUser_idBiggerThanZero(){
		with(response).assertThat("$.msg.order.user_id",greaterThan(0));
	}
	
	@Test
	public void testOrder_idBiggerThanZero(){
		with(response).assertThat("$.msg.order.order_id",greaterThan(0));
	}
	
	@Test
	public void testLast_update_timeBiggerThanZero(){
		with(response).assertThat("$.msg.order.last_update_time",greaterThan(0));
	}
	
	@Test
	public void testAmountBiggerThanZero(){
		double amount = Double.parseDouble(JsonPath.read(response, "$.msg.order.amount").toString());
		assertTrue(amount>=0.00);
	}
	
//	@Test
//	public void testExtra_amountBiggerThanZero(){
//		with(response).assertThat("$.msg.order.extra_amount",greaterThan(0.00));
//	}
	
	@Test
	public void testExtra_timeBiggerThanZero(){
		with(response).assertThat("$.msg.order.extra_time",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testTime_lengthBiggerThanZero(){
		with(response).assertThat("$.msg.order.time_length",greaterThan(0));
	}
	
	@Test
	public void testExpect_start_timeBiggerThanZero(){
		with(response).assertThat("$.msg.order.expect_start_time",greaterThan(0));
	}
	
	@Test
	public void testEnd_timeBiggerThanZero(){
		with(response).assertThat("$.msg.order.end_time",greaterThan(0));
	}
	
	@Test
	public void testPassenger_nameNotNull(){
		with(response).assertNotNull("$.msg.order.passenger_name");
	}
	
	@Test
	public void testTypeNotNull(){
		with(response).assertNotNull("$.msg.order.type");
	}
	
	@Test
	public void testPass_positionNotNull(){
		with(response).assertNotNull("$.msg.order.pass_position");
	}
	
	@Test
	public void testPassenger_phoneNotNull(){
		with(response).assertNotNull("$.msg.order.passenger_phone");
	}
	
	@Test
	public void testExpect_start_latitudedNotNull(){
		with(response).assertNotNull("$.msg.order.expect_start_latitude");
	}
	
	@Test
	public void testAir_bridge_amountNotNull(){
		with(response).assertNotNull("$.msg.order.air_bridge_amount");
	}
	
	@Test
	public void testHighway_amountNotNull(){
		with(response).assertNotNull("$.msg.order.highway_amount");
	}
	
	@Test
	public void testParking_amountNotNull(){
		with(response).assertNotNull("$.msg.order.parking_amount");
	}
	
	@Test
	public void testIs_long_distanceBiggerThanZero(){
		with(response).assertThat("$.msg.order.is_long_distance",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testIs_nightBiggerThanZero(){
		with(response).assertThat("$.msg.order.is_night",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testIs_airport_serviceBiggerThanZero(){
		with(response).assertThat("$.msg.order.is_airport_service",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testIs_route_changedBiggerThanZero(){
		with(response).assertThat("$.msg.order.is_route_changed",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testSystem_distanceBiggerThanZero(){
		with(response).assertThat("$.msg.order.system_distance",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testActual_time_lengthBiggerThanZero(){
		with(response).assertThat("$.msg.order.actual_time_length",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testProduct_type_idBiggerThanZero(){
		with(response).assertThat("$.msg.order.product_type_id",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testCar_idBiggerThanZero(){
		with(response).assertThat("$.msg.order.car_id",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testdriver_idBiggerThanZero(){
		with(response).assertThat("$.msg.order.driver_id",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testOrigin_amountBiggerThanZero(){
		with(response).assertThat("$.msg.order.expect_start_longitude",greaterThanOrEqualTo(0.00));
	}
	
	@Test
	public void testTotal_amountBiggerThanZero(){
		double amount = Double.parseDouble(JsonPath.read(response, "$.msg.order.total_amount").toString());
		assertTrue(amount>=0.00);
	}
	
	@Test
	public void testShichangfeiyongBiggerThanZero(){
		with(response).assertNotNull("$.msg.order.shichangfeiyong");
	}
	
	@Test
	public void testYejianfuwufeiBiggerThanZero(){
		double amount = Double.parseDouble(JsonPath.read(response, "$.msg.order.yejianfuwufei").toString());
		assertTrue(amount>=0.00);
	}
	
	@Test
	public void testJichangfuwufeiBiggerThanZero(){
		double amount = Double.parseDouble(JsonPath.read(response, "$.msg.order.yejianfuwufei").toString());
		assertTrue(amount>=0.00);
	}
	
	@Test
	public void testTaocanfeiyongNotNull(){
		with(response).assertThat("$.msg.order.taocanfeiyong",notNullValue());
	}
	
	@Test
	//乘客端展示按照字符串展示，按照为空展示
	public void testDistribute_driver_amountBiggerThanZero(){
		with(response).assertThat("$.msg.order.distribute_driver_amount",notNullValue());
	}
	
	@Test
	//乘客端展示按照字符串展示，按照为空展示
	public void testDistribute_company_amountBiggerThanZero(){
		with(response).assertThat("$.msg.order.distribute_company_amount",notNullValue());
	}
	
	@Test
	public void testDistribute_yidao_amountBiggerThanZero(){
		with(response).assertThat("$.msg.order.distribute_yidao_amount",notNullValue());
	}
	
	@Test
	public void testDriver_agent_amountBiggerThanZero(){
		with(response).assertThat("$.msg.order.driver_agent_amount",greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testFormulaNotNull(){
		with(response).assertThat("$.msg.order.formula",notNullValue());
	}
}
