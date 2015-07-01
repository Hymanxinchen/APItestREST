package com.yongche.privateapi.temp;

import net.minidev.json.JSONArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;

public class GetUserBadEvalute {

	public static String url;

	@Before
	public void setUp(){
//		url ="http://testing.private-api.yongche.org:8888/User";
		url ="http://testing.private-api.yongche.org:8888/Order";
	}
	
	@After
	public void tearDown(){
	}
	
	@Test
	public void testSlience_NOTime(){
		HttpRequest request = HttpRequest
							.get(url, true, 
//								"method","getBadCommentTags",
									"method","create_order",
								"user_id","5295");
		String response = request.body();
		System.out.println(response);
//		String response1= JsonPath.read(response, "$.result");
//		JSONArray tagList = JsonPath.read(response, "$.result[*].tag_text");
//		System.out.println(tagList);
//		tagList = JsonPath.read(response, "$.result[*].count");
//		System.out.println(tagList);
	}
}
