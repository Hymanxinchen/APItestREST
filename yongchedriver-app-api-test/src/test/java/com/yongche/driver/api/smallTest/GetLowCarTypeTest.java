package com.yongche.driver.api.smallTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.yongche.driver.api.data.DriverInfo;
import com.yongche.driver.api.tools.GetConfigInfo;
import com.yongche.driver.api.tools.RequestHeaderCofig;
import com.yongche.driver.api.tools.RequestMulitAssemble;
import com.yongche.driver.api.tools.RequestUrlConfig;

/**
 * 测试获取降级之后的和原本的车型
 * @author grace
 */
public class GetLowCarTypeTest {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.0.11.224/yc_crm_common";

    public static final String USER = "yongche";
    public static final String PASS = "";
    
	public static RequestHeaderCofig header;
	public static String response;
	public static HttpRequest request;
	public static RequestUrlConfig url;
	public static DriverInfo driver;
	public static Map<String, String> getListPara;
	
	@BeforeClass
	public static void setUp(){
		
		driver = new DriverInfo().getDriverWithImei();
		url = new RequestUrlConfig("http://tesing.d.yongche.org","/v4/Driver/GetLowCarType");
		
		
		String oauthToken = GetConfigInfo.GetAccessTokenTest();
		
		header = new RequestHeaderCofig(oauthToken);
		//请求参数
		getListPara = new HashMap<String, String>();
		getListPara.put("imei",driver.driverAppVersion);
//		getListPara.put("version",driver.driverAppVersion);
				
	}
	@After
	public void tearDown(){
	}

	@Test
	public void testGetRightCodeSuccess(){	
		System.out.println("******************");
		getListPara.put("imei","111111");
		url = new RequestUrlConfig("localhost","/v4/Driver/GetLowCarType");
		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();			
		System.out.println("response is :" + response);
		int ret_code = JsonPath.read(response, "$.code");
		
		assertEquals(ret_code,200);
	}
//	
//	@Test
//	public void testGetCurrenCarTypeRight(){	
//		
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();			
//		System.out.println("response is :" + response);
//		int carType = JsonPath.read(response, "$.msg.base_car_type_id");
//		String sqlString = "select base_car_type_id from car,driver where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
//		int expectCarType = connectDBAndExcuteWithJsonObject(sqlString).getInt("base_car_type_id");
//		
//		assertEquals(expectCarType,carType);
//	}
//	
//	@Test
//	public void testGetCarListTypeRight(){	
//		
//		String sqlString = "update car,driver set car.base_car_type_id = 5 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
//		this.connectDBAndExcute_NoResult(sqlString);
//		
//		getListPara.put("version",driver.driverAppVersion);
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();			
//		System.out.println("response is :" + response);
//	    ArrayList<Integer> carList =JsonPath.read(response, "$.msg.car_type_list[*].car_type_id");
//	
//		assertEquals(3,carList.size());
//	}
//	
//	@Test
//	public void testGetCarListTypeRight_luxuryCar(){	
//		
//		String sqlString = "update car,driver set car.base_car_type_id = 3 where car.car_id = driver.car_id AND driver.driver_id = " + driver.driverID;
//		this.connectDBAndExcute_NoResult(sqlString);
//		
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();			
//		System.out.println("response is :" + response);
//	    ArrayList<Integer> carList =JsonPath.read(response, "$.msg.car_type_list[*].car_type_id");
//	
//		assertEquals(3,carList.size());
//	}
//	
//	@Test
//	public void testGetCarListNoImei(){	
//		getListPara = new HashMap<String, String>();
//		getListPara.put("version",driver.driverAppVersion);
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();
//		System.out.println("response is :" + response);
//		assertEquals(0, JsonPath.read(response, "$.code"));
//	}
//	
//	@Test
//	public void testGetCarListNoVersion(){	
//		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
//		System.out.println("response is :" + response);
//		assertEquals(200, JsonPath.read(response, "$.code"));
//	}
//	
//	@Test
//	public void testGetCarListNoToken(){	
//		RequestHeaderCofig headerNoTokenCofig = header;
//		headerNoTokenCofig.oauth_token =  null;
//		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,headerNoTokenCofig).getResponse_getMethod();	
//		System.out.println("response is :" + response);
//		assertEquals(0, JsonPath.read(response, "$.code"));
//	}
//	
//	@Test
//	public void testGetCarListWrongVersion(){	
//		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
//		getListPara.put("version","30");
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
//		System.out.println("response is :" + response);
//		assertNotEquals(200, JsonPath.read(response, "$.code"));
//	}
//	
//	@Test
//	public void testGetCarListWrongVersion_char(){	
//		getListPara = new HashMap<String, String>();
//		getListPara.put("imei",driver.imei);
//		getListPara.put("version","dfdf");
//		response = new RequestMulitAssemble(url.getUrl(),getListPara,header).getResponse_getMethod();	
//		System.out.println("response is :" + response);
//		assertNotEquals(200, JsonPath.read(response, "$.code"));
//	}
//	
//	/**
//     * 执行sql语句并返回jsonObject
//     * @param sqlString
//     * @return
//     */
//    public JSONObject connectDBAndExcuteWithJsonObject(String sqlString){
//    	
//    	Connection conn = null;
//        Statement stmt = null;
//        ResultSet rSet = null;
//        JSONObject result =  null;
//        
//    	try{
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            stmt = conn.createStatement(
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
//            rSet= stmt.executeQuery(sqlString);
//            result = this.resultSetToJsonArray(rSet);
//            rSet.close();            
//            stmt.close();
//            conn.close();
//        }catch(SQLException se){        
//            se.printStackTrace();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            try{
//                if(stmt!=null)
//                    stmt.close();
//            }catch(SQLException se2){
//            }
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//    	
//    	return result;
//    }
//    
//    /**
//     * sql结果转换为jsonObject
//     * @param rs
//     * @return jsonObject 
//     */
//    public  JSONObject resultSetToJsonArray(ResultSet rs) {
//        JSONObject element = null;
//        ResultSetMetaData rsmd = null;
//        String columnName, columnValue = null;
//        try {
//            rsmd = rs.getMetaData();
//            while (rs.next()) {
//                element = new JSONObject();
//                for (int i = 0; i < rsmd.getColumnCount(); i++) {
//                    columnName = rsmd.getColumnName(i + 1);
//                    columnValue = rs.getString(columnName);
//                    element.accumulate(columnName, columnValue);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(element);
//        return element;
//    }
//    
//    /**
//     * 执行sql语句，无返回结果的如update/del
//     * @param sqlString
//     * @return
//     */
//    public void connectDBAndExcute_NoResult(String sqlString){
//    	
//    	Connection conn = null;
//        Statement stmt = null;
//        
//    	try{
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            stmt = conn.createStatement(
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
//            stmt.executeUpdate(sqlString);
//            stmt.close();
//            conn.close();
//        }catch(SQLException se){        
//            se.printStackTrace();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            try{
//                if(stmt!=null)
//                    stmt.close();
//            }catch(SQLException se2){
//            }
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//    }

}
