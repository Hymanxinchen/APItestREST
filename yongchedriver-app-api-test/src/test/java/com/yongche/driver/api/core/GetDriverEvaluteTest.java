package com.yongche.driver.api.core;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.everyItem;

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

public class GetDriverEvaluteTest {
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	
	@BeforeClass
	public static void setUp(){
		
		
		//获取Token
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		driver = new DriverInfo().getDriverWithImei();		
		header = new RequestHeaderCofig(oauthToken);
		url = new RequestUrlConfig("/V1/Driver/GetDriverEvalute");
				
		//请求参数
		Map<String,String> getListPara = new HashMap<String, String>();
		getListPara.put("is_gzip","1");
		getListPara.put("imei",driver.imei);
		getListPara.put("version",driver.driverAppVersion);
		
		System.out.println(header.getHeaderString());
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod_withoutEncoding();
		System.out.println("response is :" + response);
	}
	
	@After
	public void tearDown(){
	}

	@Test
	public void testCodeSuccess(){
		with(response).assertThat("$.code", equalTo(200));
	}
	
	@Test
	public void testGoodCommentCountBiggerThanZero(){
		with(response).assertThat("$.msg.good_comment_count", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testBadCommentCountBiggerThanZero(){
		with(response).assertThat("$.msg.bad_comment_count", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testGoodCommentRateBiggerThanZero(){
		with(response).assertThat("$.msg.good_comment_rate", greaterThanOrEqualTo(0));
	}
	
	@Test
	public void testTagTextNotNull(){
//		JSONArray tagTextList = JsonPath.read(response,"$.msg.good_tag[*].tag_text");
		with(response).assertNotNull("$.msg.good_tag[*].tag_text");
	}
	
	@Test 
	public void testGoodTagBiggerThanZero(){
		with(response).assertThat("$.msg.good_tag[*].count", everyItem(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void testBadTagTextNotNull(){
//		JSONArray tagTextList = JsonPath.read(response,"$.msg.good_tag[*].tag_text");
		with(response).assertNotNull("$.msg.bad_tag[*].tag_text");
	}
	
	@Test
	public void testBadTagCountNotNull(){
//		JSONArray tagTextList = JsonPath.read(response,"$.msg.good_tag[*].tag_text");
		with(response).assertNotNull("$.msg.bad_tag[*].tag_text");
	}
}
