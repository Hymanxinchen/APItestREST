package com.yongche.privateapi.temp;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;

public class DispatchSuccessRatesTest {
	
	public static String url;

	@Before
	public void setUp(){
		url ="http://testing.private-api.yongche.org:8888/DispatchSuccessRates";
	}
	
	@Test
	public void testBjRateIsRight(){
		HttpRequest blrequest = HttpRequest
	            .get("http://jiangzhixuan.private-api.yongche.org:8888/BlackList",true,
	                    "method","oprateAction",
	                    "user_id","4098",
	                    "driver_id","758",
	                    "type","1",
	                    "action","1",
	                    "service_order_id","");
	
	    String blacklist = blrequest.body();
	    System.out.println(blacklist);
		int result = this.getResult("bj");
		assertTrue( result>= 95 && result<=100);
	}
//	
//	@Test
//	public void testShanghaiRateIsRight(){
//		int result = this.getResult("sh");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testGuangzhouRateIsRight(){
//		int result = this.getResult("gz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testShenZhenRateIsRight(){
//		int result = this.getResult("sz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testHangZhouRateIsRight(){
//		int result = this.getResult("hz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testChengDuRateIsRight(){
//		int result = this.getResult("cd");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testWuHanRateIsRight(){
//		int result = this.getResult("wh");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testHeFeiRateIsRight(){
//		int result = this.getResult("hf");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testNanJingRateIsRight(){
//		int result = this.getResult("nj");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testKunMingRateIsRight(){
//		int result = this.getResult("km");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testChangshaRateIsRight(){
//		int result = this.getResult("chs");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testShenYangRateIsRight(){
//		int result = this.getResult("sy");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testTianjinRateIsRight(){
//		int result = this.getResult("tj");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testXiaMenRateIsRight(){
//		int result = this.getResult("xm");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testFuzhouRateIsRight(){
//		int result = this.getResult("fz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testHaErbinIsRight(){
//		int result = this.getResult("hrb");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testDaLianRateIsRight(){
//		int result = this.getResult("dl");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testChongQiongRateIsRight(){
//		int result = this.getResult("cq");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testXianRateIsRight(){
//		int result = this.getResult("xa");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testFuZhouRateIsRight(){
//		int result = this.getResult("fz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	
//	@Test
//	public void testQingDaoRateIsRight(){
//		assertTrue(getNoResult("qd").equals(""));
//	}
//	
//	@Test
//	public void testHaiKouRateIsRight(){
//		assertTrue(getNoResult("haikou").equals(""));
//	}
//	
//	@Test
//	public void testSanYaRateIsRight(){
//		assertTrue(getNoResult("sanya").equals(""));
//	}
//	
//	@Test
//	public void testTaiyuanRateIsRight(){
//		assertTrue(getNoResult("ty").equals(""));
//	}
//	
//	@Test
//	public void testGuiLinRateIsRight(){
//		assertTrue(getNoResult("gl").equals(""));
//	}
//	
//	@Test
//	public void testGuiyangRateIsRight(){
//		assertTrue(getNoResult("gy").equals(""));
//	}
//	
//	@Test
//	public void testJiNanRateIsRight(){
//		assertTrue(getNoResult("jn").equals(""));
//	}
//	
//	@Test
//	public void testZhengZhouRateIsRight(){
//		int result = this.getResult("zz");
//		assertTrue( result>= 95 && result<=100);
//	}
//	
//	@Test
//	public void testDongGuanRateIsRight(){
//		assertTrue(getNoResult("dg").equals(""));
//	}
//	
//	@Test
//	public void testDaTongRateIsRight(){
//		assertTrue(getNoResult("dt").equals(""));
//	}
//	
//	@Test
//	public void testJinChengRateIsRight(){
//		assertTrue(getNoResult("jc").equals(""));
//	}
//	
//	@Test
//	public void testXiangGangRateIsRight(){
//		assertTrue(getNoResult("hk").equals(""));
//	}
//	
//	@Test
//	public void testPhoenixRateIsRight(){
//		assertTrue(getNoResult("phoenix").equals(""));
//	}
//	
//	@Test
//	public void testLondonRateIsRight(){
//		assertTrue(getNoResult("london").equals(""));
//	}
//	
//	@Test
//	public void testSanfranciscoRateIsRight(){
//		assertTrue(getNoResult("sanfrancisco").equals(""));
//	}
//	
//	@Test
//	public void testTaiBeiRateIsRight(){
//		assertTrue(getNoResult("tb").equals(""));
//	}
//	
//	@Test
//	public void testYuLinRateIsRight(){
//		assertTrue(getNoResult("yl").equals(""));
//	}
//	
//	@Test
//	public void testNewyorkRateIsRight(){
//		assertTrue(getNoResult("newyork").equals(""));
//	}
//	
//	@Test
//	public void testBaojiRateIsRight(){
//		assertTrue(getNoResult("baoji").equals(""));
//	}
//	
//	@Test
//	public void testNoCityID(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates",
//                     "city_id","");
//
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code == 499);
//	}
//	
//	@Test
//	public void testNullCityID(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates",
//                     "city_id",null);
//
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code == 499);
//	}
//	
//	@Test
//	public void testNOCityID(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates");
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code == 499);
//	}
//	
//	@Test
//	public void testWrongCityID(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates",
//                     "city_id","7777");
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code != 200);
//	}
//	
//	@Test
//	public void testWrongCityID_1(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates",
//                     "city_id","哈哈哈");
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code != 200);
//	}
//	
//	@Test
//	public void testWrongCityID_2(){
//		HttpRequest successRate = HttpRequest
//                .get(url,true,
//                     "method","getSuccessRates",
//                     "city_id","bjbj");
//        String successRateString  = successRate.body();
//        int code = JsonPath.read(successRateString, "$.ret_code");
//        assertTrue( code != 200);
//	}
	
	public int getResult(String city){
		HttpRequest successRate = HttpRequest
                .get(url,true,
                     "method","getSuccessRates",
                     "city_id",city);

        String successRateString  = successRate.body();

        System.out.println(city+successRateString);
        String result = JsonPath.read(successRateString, "$.result").toString();
        result = result.substring(0,result.length()-1);
        
        return Integer.parseInt(result);
	}
	
	public String getNoResult(String city){
		HttpRequest successRate = HttpRequest
                .get(url,true,
                     "method","getSuccessRates",
                     "city_id",city);

        String successRateString  = successRate.body();

        System.out.println(city+successRateString);
        
        String code = JsonPath.read(successRateString,"$.result");
        return code;

	}
}
