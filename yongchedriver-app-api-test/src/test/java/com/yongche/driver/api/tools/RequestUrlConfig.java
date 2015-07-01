package com.yongche.driver.api.tools;
/**
 * 请求地址构成：hostName , path
 * @author grace
 *
 */
public class RequestUrlConfig {

	public String hostName;
	public String path;
	public static String hostOffLine = "http://testing2.d.yongche.org";
//	public static String hostOffLine = "http://driver-api.alpha.yongche.org";
//	public static String hostOffLine = "http://backend.alpha.d.yongche.org";
	public static String hostOnLine = "http://driver-api.yongche.com";
	public static String hostNameString;
	
	public static boolean IS_ONLINE_TEST = false;
	static{
		hostNameString = IS_ONLINE_TEST ? hostOnLine : hostOffLine;
	}
	public RequestUrlConfig(String path){
		this.hostName = hostNameString;
		this.path = path;
	}
	
	public RequestUrlConfig(String hostName,String path){
		this.hostName = hostName;
		this.path = path;
	}
	
	public String getUrl(){
		return this.hostName+this.path;
	}
}
