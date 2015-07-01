//package com.yongche.driver.api.smallTest;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//
//import org.apache.commons.io.FileUtils;
//
//import freemarker.template.Configuration;
//import freemarker.template.DefaultObjectWrapper;
//import freemarker.template.Template;
//
//public class CodeGenerator {
//
//	/**
//	 * 代码工厂实例
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		String dataBase = "hibernate";     // 数据库名
//		String username = "root";          // 数据库用户名
//		String password = "root";          // 数据库密码
//		String tableName = "my_user_role"; // 表名
//		String pack = "com.wj.model";      // 包名
//		try {
//			// 获取数据
//			Collection<Map<String, String>> properties = readData(dataBase, username, password, tableName);
//
//			Configuration cfg = new Configuration();
//			cfg.setDirectoryForTemplateLoading(new File("template"));
//			cfg.setObjectWrapper(new DefaultObjectWrapper());
//
//			 //获取模板文件 
//			Template template = cfg.getTemplate("model.ftl");
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("class", getClassName(tableName));
//			map.put("pack", pack);
//			map.put("properties", properties);
//			
//			// 生成输出到控制台 
//			Writer out = new OutputStreamWriter(System.out);
//			template.process(map, out);
//			out.flush();
//
//			 //生成输出到文件 
//			String root = genPackStr("src",pack);
//			File fileDir = new File(root);
//			// 创建文件夹，不存在则创建
//			FileUtils.forceMkdir(fileDir);
//			// 指定生成输出的文件
//			File output = new File(fileDir + "/"+getClassName(tableName)+".java");
//			Writer writer = new FileWriter(output);
//			template.process(map, writer);
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	/**
//	 * 读取表数据
//	 * @param dataBase 数据库名
//	 * @param tableName 表名
//	 * @return
//	 */
//	public static Collection<Map<String, String>> readData(String dataBase, String username, String password, String tableName){
//		Collection<Map<String, String>> properties = new HashSet<Map<String, String>>();
//		Connection conn = null;
//		ResultSet rs = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dataBase+"?useUnicode=true&characterEncoding=UTF-8",username, password);
//			DatabaseMetaData dbmd = conn.getMetaData();
//			rs = dbmd.getColumns(null, null, tableName, null);
//			while (rs.next()) {
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("fieldName", genFieldName(rs.getString("COLUMN_NAME")));
//				map.put("fieldType", genFieldType(rs.getString("TYPE_NAME")));
//				properties.add(map);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if(conn != null){
//					conn.close();
//				}
//				if(rs != null){
//					rs.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return properties;
//	}
//
//	/**
//	 * 根据包名获取对应的路径名
//	 * @param root 根路径
//	 * @param pack 包名
//	 * @return
//	 */
//	public static String genPackStr(String root,String pack){
//		String result = root;
//		String [] dirs = pack.split("\\.");
//		for(String dir : dirs){
//			result += "/"+dir;
//		}
//		return result;
//	}
//	
//	/**
//	 * 根据表面获取类名
//	 * @param tableName 表名
//	 * @return
//	 */
//	public static String getClassName(String tableName){
//		String result = "";
//		String lowerFeild = tableName.toLowerCase();
//		String[] fields = lowerFeild.split("_");
//		if (fields.length > 1) {
//			for(int i=0;i<fields.length;i++){
//				result += fields[i].substring(0,1).toUpperCase() + fields[i].substring(1, fields[i].length());
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 根据表字段名获取java中的字段名
//	 * @param field 字段名
//	 * @return
//	 */
//	public static String genFieldName(String field) {
//		String result = "";
//		String lowerFeild = field.toLowerCase();
//		String[] fields = lowerFeild.split("_");
//		result += fields[0];
//		if (fields.length > 1) {
//			for(int i=1;i<fields.length;i++){
//				result += fields[i].substring(0,1).toUpperCase() + fields[i].substring(1, fields[i].length());
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 根据表字段的类型生成对应的java的属性类型
//	 * @param type 字段类型
//	 * @return
//	 */
//	public static String genFieldType(String type){
//		String result = "String";
//		if(type.toLowerCase().equals("varchar")){
//			result = "String";
//		}else if(type.toLowerCase().equals("int")){
//			result = "int";
//		}
//		return result;
//	}
//
//}
//
