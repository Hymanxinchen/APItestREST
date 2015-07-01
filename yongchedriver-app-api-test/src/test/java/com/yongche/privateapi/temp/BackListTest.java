package com.yongche.privateapi.temp;

import com.github.kevinsawicki.http.HttpRequest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;

public class BackListTest {
	
	public static String url;

	@Before
	public void setUp(){
		url ="http://gaowenrui.private-api.yongche.org:8888/?select";
	}

//	@Test
//	public void test_remove_UserIDIsNull(){
//		this.getResponseOfRemove(null, "670", "2005454250");
//		System.out.println("*********test_remove_UserIDIsNull");
//	}
//	
//	@Test
//	public void test_remove_UserIDNotValid_String(){
//		this.getResponseOfRemove("哈哈哈", "670", "2005454250	");
//		System.out.println("*********test_remove_UserIDNotValid_String");
//	}
//	
//	@Test
//	public void test_remove_UserIDNotValid_NotExist(){
//		this.getResponseOfRemove("99999999999", "670", "2005454250");
//		System.out.println("*********test_remove_UserIDNotValid_NotExist");
//	}
//	
//	@Test
//	public void test_remove_UserIDNotValid_Zero(){
//		this.getResponseOfRemove("0", "670", "2005454250");
//		System.out.println("*********test_remove_UserIDNotValid_Zero");
//	}
//	
//	@Test
//	public void test_remove_UserIDNotValid_Special(){
//		this.getResponseOfRemove(",.<>", "670", "2005454250");
//		System.out.println("*********test_remove_UserIDNotValid_Special");
//	}
	
//	@Test
//	public void test_Add_UserIDNotValid_Special(){
//		this.getResponseOfAdd("5127", "670", "2005454250");
//		System.out.println("*********test_Add_UserIDNotValid_Special");
//	}
//	
//	@Test
//	public void test_Add_UserIDNotValid_String(){
//		this.getResponseOfAdd("哈哈哈", "670", "2005454250");
//		System.out.println("*********test_Add_UserIDNotValid_String");
//	}
//	
//	@Test
//	public void test_Add_UserIDNotValid_NotExist(){
//		this.getResponseOfAdd("8888888", "670", "2005454250");
//		System.out.println("*********test_Add_UserIDNotValid_NotExist");
//	}
//	
//	@Test
//	public void test_Add_UserIDNotValid_Zero(){
//		this.getResponseOfAdd("0", "670", "2005454250");
//		System.out.println("*********test_Add_UserIDNotValid_Zero");
//	}
//	
//	@Test
//	public void test_Add_UserIDNotValid_Null(){
//		this.getResponseOfAdd(null, "670", "2005454250");
//		System.out.println("*********test_Add_UserIDNotValid_Zero");
//	}
	
	
	
//	@Test
//	public void test_remove_DriverIDIsNull(){
//		this.getResponseOfRemove("5127", null,"2005454250");
//		System.out.println("*********test_remove_DriverIDIsNull");
//	}
	
//	@Test
//	public void test_remove_DirverIDNotValid_String(){
//		this.getResponseOfRemove("5127", "hmij", "2005454250");
//		System.out.println("*********test_remove_DirverIDNotValid_String");
//	}
//	
//	@Test
//	public void test_remove_DirverIDNotValid_NotExist(){
//		this.getResponseOfRemove("5127", "88888888888", "2005454250");
//		System.out.println("*********test_remove_DirverIDNotValid_NotExist");
//	}
//	
//	@Test
//	public void test_remove_DirverIDNotValid_Zero(){
//		this.getResponseOfRemove("5127", "0", "2005454250");
//		System.out.println("*********test_remove_DirverIDNotValid_Zero");
//	}
//	
//	@Test
//	public void test_remove_DirverIDNotValid_Special(){
//		this.getResponseOfRemove("5127", "<>><<;lk", "2005454250");
//		System.out.println("*********test_remove_DirverIDNotValid_Special");
//	}
	
//	@Test
//	public void test_Add_DirverIDNotValid_Special(){
//		this.getResponseOfAdd("5127", "o.;dklfo<>", "2005454250");
//		System.out.println("*********test_Add_DirverIDNotValid_Special");
//	}
//	
//	@Test
//	public void test_Add_DirverIDNotValid_String(){
//		this.getResponseOfAdd("5127", "hamidj", "2005454250");
//		System.out.println("*********test_Add_DirverIDNotValid_String");
//	}
//	
//	@Test
//	public void test_Add_DirverIDNotValid_NotExist(){
//		this.getResponseOfAdd("5127", "8888888888888", "2005454250");
//		System.out.println("*********test_Add_DirverIDNotValid_NotExist");
//	}
//	
//	@Test
//	public void test_Add_DirverIDNotValid_Zero(){
//		this.getResponseOfAdd("5127", null, "2005454250");
//		System.out.println("*********test_Add_DirverIDNotValid_Zero");
//	}
//	
//	
//	
//	@Test
//	public void test_remove_OrderIDIsNull(){
//		this.getResponseOfRemove("5127", "670",null);
//		System.out.println("*********test_remove_OrderIDIsNull");
//	}
//	
//	@Test
//	public void test_remove_OrderIDNotValid_String(){
//		this.getResponseOfRemove("5127", "670", "2005454250op");
//		System.out.println("*********test_remove_OrderIDNotValid_String");
//	}
//	
//	@Test
//	public void test_remove_OrderIDNotValid_NotExist(){
//		this.getResponseOfRemove("5127", "670", "9999999999");
//		System.out.println("*********test_remove_OrderIDNotValid_NotExist");
//	}
//	
//	@Test
//	public void test_remove_OrderIDNotValid_Zero(){
//		this.getResponseOfRemove("5127", "670", "0");
//		System.out.println("*********test_remove_OrderIDNotValid_Zero");
//	}
//	
//	@Test
//	public void test_remove_OrderIDNotValid_Special(){
//		this.getResponseOfRemove("5127", "670", ",./'[poiu90<>");
//		System.out.println("*********test_remove_OrderIDNotValid_Special");
//	}
//	
//	@Test
	public void test_Add_OrderIDNotValid_Special(){
		this.getResponseOfAdd("5127", "671", ",./'[poiu90<>");
		System.out.println("*********test_Add_OrderIDNotValid_Special");
	}
	
//	@Test
	public void test_Add_OrderIDNotValid_String(){
		this.getResponseOfAdd("5127", "670", "malhfafaf");
		System.out.println("*********test_Add_OrderIDNotValid_String");
	}
	
	@Test
	public void test_Add_OrderIDNotValid_NotExist(){
		 this.getResponseOfAdd("1103", "50006244", "88888888888");
     	//this.getResponseOfRemove("1103", "50006244", "88888888888");
		System.out.println("*********test_Add_OrderIDNotValid_NotExist");
	}
	
//	@Test
	public void test_Add_OrderIDNotValid_Zero(){
		this.getResponseOfAdd("5127", "673",null);
		System.out.println("*********test_Add_OrderIDNotValid_Zero");
	}
//	
//	@Test
	public void test_TypeNotValid_String(){
		this.getResponseAction("pppp", "1");
		System.out.println("*********test_TypeNotValid_String");
	}
	
//	@Test
	public void test_ActionNotValid_String(){
		this.getResponseAction("1", "3");
		System.out.println("*********test_ActionNotValid_String");
	}
	

	
	public void getResponseOfAdd(String user_id,String driver_id,String orderID ){
		HttpRequest blrequest = HttpRequest
	            .get("http://testing2.private-api.yongche.org:8888/User",true,
	            		"method","oprateAction",
	                    "user_id",user_id,
	                    "driver_id",driver_id,
	                    "type","0",
	                    "action","0",
	                    "service_order_id",orderID);
	
	    String blacklist = blrequest.body();
	    System.out.println("++++++++" + blacklist);
//	    return blacklist;
	}
	
	public void getResponseOfRemove(String user_id,String driver_id,String orderID ){
		HttpRequest blrequest = HttpRequest
	            .get("http://testing2.private-api.yongche.org:8888/User",true,
	                    "method","oprateAction",
	                    "user_id",user_id,
	                    "driver_id",driver_id,
	                    "type","1",
	                    "action","0",
	                    "service_order_id",orderID);
		
	
	    String blacklist = blrequest.body();
	    System.out.println("++++++++" + blacklist);
//	    return blacklist;
	}
	
	public String getResponseAction(String type,String action ){
		HttpRequest blrequest = HttpRequest
	            .get("http://testing2.private-api.yongche.org:8888/User",true,
	                    "method","oprateAction",
	                    "user_id","5127",
	                    "driver_id","670",
	                    "type",type,
	                    "action",action,
	                    "service_order_id","2005454250");
	
	    String blacklist = blrequest.body();
	    System.out.println("++++++++" + blacklist);
	    return blacklist;
	}
	
//	4098 758
}
