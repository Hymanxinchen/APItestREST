package com.yongche.privateapi.temp;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;

public class SilencingDriverTest {

	public static String url;

	@Before
	public void setUp(){
		url ="http://testing.private-api.yongche.org:8888/DriverSilence";
	}
	
	@After
	public void tearDown(){
	}
	
	
//	@Test
//	public void testSlience(){
//		
//		int code = JsonPath.read(this.slience("670", 24,"", "", ""), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "", "");
//		
//	}
//	
	@Test
	public void testSlienceRepeatly(){
		
//		int code = JsonPath.read(this.slience("670", 10,"", "", ""), "$.ret_code");
//		code = JsonPath.read(this.slience("670", 3,"", "", ""), "$.ret_code");
//	    code = JsonPath.read(this.slience("670", 10,"", "", ""), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "", "");
	    judgingDriverSilenceStatus();
		
	}
//	
//	@Test
//	public void testSlienceDifferentType_0(){
//		
//		int code = JsonPath.read(this.slience("670", 24,"", "", "0"), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "", "");	
//	}
//	
//	@Test
//	public void testSlienceDifferentType_1(){
//		
//		int code = JsonPath.read(this.slience("670", 24,"", "", "1"), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "", "");	
//	}
//	
//	@Test
//	public void testSlience_opar(){
//		
//		int code = JsonPath.read(this.slience("670", 24,"", "电动机", "1"), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "", "");	
//	}
	
//	@Test
	public void testSlience_memo(){
		
		int code = JsonPath.read(this.slience("50005058	", 5,"<>dfjdksjfad我哈哈哈", "http://ddfj我哈哈哈", "skdjskj"), "$.ret_code");
//	    assertTrue(code == 200);
//	    this.unSlience("670", "1", "1");	
	    
	    judgingDriverSilenceStatus();
	}
	
//	@Test
//	public void testSlience_NODriverID(){
//		
//		int code = JsonPath.read(this.slience("", 2,"", "H80jdifjaojfdfhnafdjfhaiufyajnfk我哈哈哈", "1"), "$.ret_code");
//	    assertTrue(code == 499);
//	    this.unSlience("670", "", "");	
//	}
	
	@Test
	public void testSlience_NOTime(){
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -5);
		long endTime = calendar.getTimeInMillis()/1000;
		
		HttpRequest blrequest = HttpRequest
	            .get(url,true,
	                    "method","silencingDriver",
	                    "driver_id","670",
	                    "silence_end_time", endTime,
	                    "memo","jdhfaiuiudhfudsja",
	                    "oper",""
//	                    "type",1
	                    );
	
	    String slienceResult = blrequest.body();
		int code = JsonPath.read(slienceResult, "$.ret_code");
//	    assertTrue(code == 498);
//	    this.unSlience("670", "", "");	
	    judgingDriverSilenceStatus();
	}
	
	public String slience(String driver_id,int silence_end_time,String memo,String opar,String type){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, +silence_end_time);
		long endTime = calendar.getTimeInMillis()/1000;
		
		System.out.println(endTime);
		
		HttpRequest blrequest = HttpRequest
	            .get(url,true,
	                    "method","silencingDriver",
	                    "driver_id",driver_id,
	                    "silence_end_time",String.valueOf(endTime),
	                    "memo",memo,
	                    "oper",opar,
	                    "type",type);
	
	    String slienceResult = blrequest.body();
	    return slienceResult;
	}
	
	public String unSlience(String driver_id,String opar,String type){
		HttpRequest blrequest = HttpRequest
	            .get(url,true,
	                    "method","unSilencingDriver",
	                    "driver_id",driver_id,
	                    "oper",opar,
	                    "type",type);
	
	    String unSlienceResult = blrequest.body();
	    return unSlienceResult;
	}

	public void judgingDriverSilenceStatus(){
		HttpRequest blrequest = HttpRequest
	            .get(url,true,
	                    "method","judgingDriverSilenceStatus",
	                    "driver_id","670");
		String slienceList = blrequest.body();
		System.out.println(slienceList);
	}
}
