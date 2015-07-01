package com.yongche.driver.api.tools;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderCofig {

	private String oauth_consumer_key;
	private String oauth_signature_method;
	private String oauth_timestamp;
	private String x_auth_mode;
	public String oauth_token;
	private String oauth_nonce;
	private String oauth_signature;
	private String oauth_version;
	public String user_Agent;
	public String oauth_secret;
	public Map<String, String> headerMap;
	
	private static String oauth_consumer_keyString;
	private static String oauth_consumer_key_Offline = "2afdd89f5c6dbdc34542ab04933a091004eba18e2";
	private static String oauth_consumer_key_online = "4821726c1947cdf3eebacade98173939";
	
	
	public static boolean IS_ONLINE_TEST = RequestUrlConfig.IS_ONLINE_TEST;
	static{
		oauth_consumer_keyString = IS_ONLINE_TEST ? oauth_consumer_key_online : oauth_consumer_key_Offline;
	}
	public RequestHeaderCofig(){
		this.setOauth_consumer_key();
		this.setOauth_signature_method();
		this.setOauth_timestamp(String.valueOf(System.currentTimeMillis()/1000));
		this.setOauth_nonce();
		this.setOauth_signature();
		this.setOauth_version();
		this.setUser_Agent();
	}
	
	public RequestHeaderCofig(String oauth_token){
		this.setOauth_consumer_key();
		this.setOauth_signature_method();
		this.setOauth_timestamp(String.valueOf(System.currentTimeMillis()/1000));
		this.setX_auth_mode();
		this.setOauth_nonce();
		this.setOauth_signature();
		this.setOauth_version();
		this.setUser_Agent();
		this.setOauth_token(oauth_token);
	}
	
	public RequestHeaderCofig(String oauth_token,String oauth_scret){
		this.setOauth_consumer_key();
		this.setOauth_signature_method();
		this.setOauth_timestamp(String.valueOf(System.currentTimeMillis()/1000));
		this.setX_auth_mode();
		this.setOauth_nonce();
		this.setOauth_signature();
		this.setOauth_version();
		this.setUser_Agent();
		this.setOauth_token(oauth_token);
		this.oauth_secret = oauth_scret;
	}
	public Map<String,String> getHeaderMapWithOutToken(){
		headerMap = new HashMap<String, String>();
		headerMap.put("oauth_consumer_key", this.oauth_consumer_key);
		headerMap.put("oauth_signature_method", this.oauth_signature_method);
		headerMap.put("oauth_timestamp", this.oauth_timestamp);
		headerMap.put("oauth_nonce", this.oauth_nonce);
		headerMap.put("oauth_signature", this.oauth_signature);
		headerMap.put("oauth_version", this.oauth_version);
		return headerMap;
	}
	
	public Map<String, String> getHeaderMapWithToken(){
		headerMap = new HashMap<String, String>();
		headerMap.put("oauth_consumer_key", this.oauth_consumer_key);
		headerMap.put("oauth_signature_method", this.oauth_signature_method);
		headerMap.put("oauth_timestamp", this.oauth_timestamp);
		headerMap.put("oauth_nonce", this.oauth_nonce);
		headerMap.put("x_auth_mode", this.x_auth_mode);
		headerMap.put("oauth_token", this.oauth_token);
		headerMap.put("oauth_signature", this.oauth_signature);
		headerMap.put("oauth_version", this.oauth_version);
		headerMap.put("oauth_token_secret", this.oauth_secret);
		return headerMap;
	}
	
	public String getHeaderString(){
		String authString  =  "";
		if(this.oauth_token ==null)
		{
			authString = this.getHeaderMapWithOutToken().toString();
		}
		else{
			authString = this.getHeaderMapWithToken().toString();
			
		}
		
		int l = authString.length();
		authString = authString.substring(1,l-1);
		System.out.println("this is authString" + authString);
		return "OAuth " + authString;
	}
	
//	public String getHeaderStringWithToken(){
//		return "OAuth" + this.getHeaderMapWithToken().toString();
//	}
	public String getOauth_timestamp() {
		return oauth_timestamp;
	}

	public void setOauth_timestamp(String oauth_timestamp) {
		this.oauth_timestamp = oauth_timestamp;
	}


	public String getOauth_consumer_key() {
		return oauth_consumer_key;
	}

	void setOauth_consumer_key(){
		this.oauth_consumer_key = oauth_consumer_keyString;
	}
	
	public String getOauth_signature_method() {
		return oauth_signature_method;
	}

	void setOauth_signature_method(){
		this.oauth_signature_method = "PLAINTEXT";
	}

	public String getOauth_nonce() {
		return oauth_nonce;
	}

    void setOauth_nonce(){
		this.oauth_nonce = String.valueOf(Long.parseLong(this.oauth_timestamp)+1000);
	}
	
	public String getOauth_signature() {
		return oauth_signature;
	}

	void setOauth_signature(){
		this.oauth_signature = "5sARLGoVkNAPhh5wq1Hl95crWIk";
	}
	
	public String getOauth_version() {
		return oauth_version;
	}

	public void setOauth_version(){
		this.oauth_version = "1.0";
	}
	
	public String getUser_Agent() {
		return user_Agent;
	}
	
	public void setUser_Agent(){
		this.user_Agent = "HUAWEI G520-0000";
	}

	public void setX_auth_mode(){
		this.x_auth_mode = "client_auth";
	}
	
	public String getX_auth_mod(){
		return x_auth_mode;
	}
	
	
	public String getOauth_token() {
		return oauth_token;
	}

	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}
	
	public void refreshTimeStamp(){
		
	}
}
