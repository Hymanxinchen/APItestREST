package com.yongche.driver.api.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class DBHelper {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.0.11.224/";

    public static final String USER = "yongche";
    public static final String PASS = "";
    
    /**
     * 执行sql语句并返回jsonObject
     * @param sqlString
     * @return
     */
    public JSONObject connectDBAndExcuteWithJsonObject(String sqlString,String db){
    	
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rSet = null;
        JSONObject result =  null;
        
    	try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + db, USER, PASS);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rSet= stmt.executeQuery(sqlString);
            result = this.resultSetToJsonArray(rSet);
            rSet.close();            
            stmt.close();
            conn.close();
        }catch(SQLException se){        
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	
    	return result;
    }
    
    /**
     * sql结果转换为jsonObject
     * @param rs
     * @return jsonObject 
     */
    public  JSONObject resultSetToJsonArray(ResultSet rs) {
        JSONObject element = null;
        ResultSetMetaData rsmd = null;
        String columnName, columnValue = null;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                element = new JSONObject();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnName = rsmd.getColumnName(i + 1);
                    columnValue = rs.getString(columnName);
                    element.accumulate(columnName, columnValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(element);
        return element;
    }
    
    /**
     * 执行sql语句，无返回结果的如update/del
     * @param sqlString
     * @return
     */
    public void connectDBAndExcute_NoResult(String sqlString,String db){
    	
    	Connection conn = null;
        Statement stmt = null;
        
    	try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + db, USER, PASS);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate(sqlString);
            stmt.close();
            conn.close();
        }catch(SQLException se){        
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

}
