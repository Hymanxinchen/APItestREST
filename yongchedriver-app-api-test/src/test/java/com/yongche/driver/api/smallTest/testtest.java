package com.yongche.driver.api.smallTest;

import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

public class testtest {

	@Test
	public void test1(){
		String a ="{ret_code:100,msg:ssss}";
		String s = JsonPath.read(a, "$.ret_code").toString();
		System.out.println("dfdfdf"+s);
	}
}
