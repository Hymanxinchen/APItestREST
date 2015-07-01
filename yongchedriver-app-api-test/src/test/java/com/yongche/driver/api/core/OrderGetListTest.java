package com.yongche.driver.api.core;

import static org.junit.Assert.assertEquals;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThan;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

public class OrderGetListTest {
	
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
		url = new RequestUrlConfig("http://huangyang.d.yongche.org","/Order/GetList");
												
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("from","1");
		getListPara.put("is_gzip","1");
		getListPara.put("status","unbalanced");
		getListPara.put("page_num","1");
		getListPara.put("mode","full");
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
//		getListPara.put("to","2000000000");
		getListPara.put("out_coord_type","baidu");
		
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
	public void testResultNotNull(){
		with(response).assertNotNull("$.msg");
	}
	
	@Test
	public void testCountBiggerThanZero(){
		with(response).assertThat("$.msg.count", greaterThan(0));
	}
	
	@Test
	public void testOrder_listNotNull(){
		with(response).assertNotNull("$.msg.order_list");
	}
	
	@Test
	public void testOrder_listStatusNotNull(){
		with(response).assertThat("$.msg.order_list[*].status",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listOrder_idPriceNotNull(){
		with(response).assertThat("$.msg.order_list[*].order_id",everyItem(greaterThan(0)));
	}
	
	@Test
	public void testOrder_listUser_idPriceNotNull(){
		with(response).assertThat("$.msg.order_list[*].user_id",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listTypePriceNotNull(){
		with(response).assertThat("$.msg.order_list[*].type",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listFormulaPriceNotNull(){
		with(response).assertThat("$.msg.order_list[*].formula",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listBalanceNotNull(){
		with(response).assertThat("$.msg.order_list[*].balance_status",everyItem(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void testOrder_listExpect_start_timeNotNull(){
		with(response).assertThat("$.msg.order_list[*].expect_start_time",everyItem(greaterThan(0)));
	}
	
	@Test
	public void testOrder_listSystem_distanceNotNull(){
		with(response).assertThat("$.msg.order_list[*].system_distance",everyItem(greaterThanOrEqualTo(0)));
	}
	@Test
	public void testOrder_listProduct_type_idNotNull(){
		with(response).assertThat("$.msg.order_list[*].product_type_id",everyItem(greaterThan(0)));
	}
	
	@Test
	public void testOrder_listUserPriceNotNull(){
		with(response).assertThat("$.msg.order_list[*].user",everyItem(notNullValue()));
	}

	
	@Test
	public void testOrder_listAir_bridge_amountNotNull(){
		with(response).assertThat("$.msg.order_list[*].air_bridge_amount",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listHighway_amountNotNull(){
		with(response).assertThat("$.msg.order_list[*].highway_amount",everyItem(notNullValue()));
	}
	
	@Test
	public void testOrder_listParking_amountNotNull(){
	
		with(response).assertThat("$.msg.order_list[*].parking_amount",everyItem(notNullValue()));
	}
	
	@Test
	public void testUserGenderNotNull(){
		with(response).assertThat("$.msg.order_list[*].user.gender",everyItem(notNullValue()));
	}
	
	@Test
	public void testUserNameNotNull(){
		with(response).assertThat("$.msg.order_list[*].user.name",everyItem(notNullValue()));
	}
	
	@Test
	public void testUseriIs_coporateNotNull(){
		with(response).assertThat("$.msg.order_list[*].user.is_coporate",everyItem(notNullValue()));
	}

}
