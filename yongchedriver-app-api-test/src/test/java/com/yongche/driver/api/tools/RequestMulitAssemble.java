package com.yongche.driver.api.tools;

import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;

public class RequestMulitAssemble {

	public Map<String, String> requestParameter;
	public  String addressUrl;
	public RequestHeaderCofig requestConfig;
	
	public RequestMulitAssemble(String addressUrl,Map<String, String> requestParameter,RequestHeaderCofig headerConfig){
		this.requestParameter = requestParameter;
		this.addressUrl = addressUrl;
		this.requestConfig = headerConfig;
		System.out.println(requestParameter);
		System.out.println(headerConfig);
	}
	
	public String getResponse_getMethod(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
		HttpRequest getRequest = HttpRequest.get(this.addressUrl,requestParameter,true).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
											 acceptEncoding("gzip,deflate,sdch").
											 acceptGzipEncoding().
											 uncompress(true);
		System.out.println(getRequest.toString());
		return getRequest.body();											 
	}
	
	public HttpRequest  getHttpRequest_getMethod(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
		HttpRequest getRequest = HttpRequest.get(this.addressUrl,requestParameter,true).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
											 acceptEncoding("gzip,deflate,sdch").
											 acceptGzipEncoding().
											 uncompress(true);
		System.out.println(getRequest.toString());
		return getRequest;											 
	}
	
	public String getResponse_getMethod_withoutEncoding(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
		HttpRequest getRequest = HttpRequest.get(this.addressUrl,requestParameter,true).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
//											 acceptEncoding("gzip,deflate,sdch").
											 uncompress(true)
											 ;
		System.out.println(getRequest.toString());
		return getRequest.body();											 
	}
	
	public String  getResponse_postMethod(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
		HttpRequest getRequest = HttpRequest.post(this.addressUrl,true).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
											 acceptEncoding("gzip,deflate,sdch").
											 uncompress(true).
											 acceptGzipEncoding().
											 form(requestParameter);
		System.out.println("**********"+getRequest.toString());
		return getRequest.body();											 
	}
	
	public HttpRequest  getHttpRequest_postMethod(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
		HttpRequest getRequest = HttpRequest.post(this.addressUrl,true).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
											 acceptEncoding("gzip,deflate,sdch").
											 uncompress(true).
											 acceptGzipEncoding().
											 form(requestParameter);
		System.out.println("**********"+getRequest.toString());
		return getRequest;											 
	}
	
	public HttpRequest  getHttpRequest_deleteMethod(){
		String headerString = this.requestConfig.getHeaderString();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization",headerString);
	
		HttpRequest getRequest = HttpRequest.delete(this.addressUrl,true,requestParameter).
											 headers(headerMap).
											 userAgent(this.requestConfig.getUser_Agent()).
											 acceptEncoding("gzip,deflate,sdch").
											 uncompress(true).
											 acceptGzipEncoding();
		System.out.println("**********"+getRequest.toString());
		return getRequest;											 
	}
//	public String mapParameterTransformer(){
//		String parameterString ="";
//		if(!requestParameter.isEmpty()){
//			Iterator<String> iter = requestParameter.keySet().iterator();
//	
//			while (iter.hasNext()) {
//	
//			    String key = iter.next();
//	
//			    String value = requestParameter.get(key);
//			    
//			    parameterString+= "\""+key + "\""+","+ "\""+value+ "\""+",";	
//			}
//		}
//		
//		System.out.println(parameterString.substring(0, parameterString.length()-1));
//		return parameterString.substring(0, parameterString.length()-1);
//		
//	}
}
