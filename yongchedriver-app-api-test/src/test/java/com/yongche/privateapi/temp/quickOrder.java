package com.yongche.privateapi.temp;

import static org.junit.Assert.*;
import net.minidev.json.JSONArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;

public class quickOrder {
	
	@Test
	public void testBjRateIsRight(){
		HttpRequest blrequest = HttpRequest
	            .post("http://testing.private-api.yongche.org:8888/order",true,
	                    "method","quickOrder",
	                    "user_id","1610",
//	                    "version","7",
	                    "passenger_name","水娃",
	                    "passenger_number","4",
	                    "city","bj",
	                    "car_type_id","1",
	                    "product_type_id","7",
	                    "fixed_product_id","35",
	                    "source","1000001",
	                    "coupon_member_id","",
	                    "expect_start_time","1430123143",
	                    "in_coord_type","baidu",
	                    "has_custom_decision","1",
	                    "expect_end_latitude","40.08029",
	                    "expect_end_longitude","116.58797",
	                    "start_position","中国技术交易大厦",
	                    "end_position","首都国际机场",
	                    "is_face_pay","",
	                    "expect_start_latitude","39.983946880045");
	
	    String response = blrequest.body();
	    System.out.println(response);
	}

}
