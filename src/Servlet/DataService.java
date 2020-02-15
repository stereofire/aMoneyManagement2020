package Servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DataService {
	
	static Connection conn = null;//数据库链接信息
    static PreparedStatement stmt = null;//数据库链接状态
    static ResultSet rs = null;//数据库查询结果
    
	public static Map<String,String> tokenDict = new HashMap<>();//已登录账号的tockenid后台监控
	private static String PASSWORD;

	// 验证 username和password
	public static String tryLogin(String username, String password) {
		String sql = "SELECT * FROM 用户信息表  WHERE 学号 =" + username;
        rs = selectSQL(sql); 
        show(rs);
		System.out.println("The right PASSWORD is " + PASSWORD + 
				" and the input password is " + password);
		String token;
		if(PASSWORD.contentEquals(password)) {
	        if(true) {
				UUID uuid = UUID.randomUUID();
				tokenDict.put(username, uuid.toString());
				token = uuid.toString();
				System.out.println("密码验证通过");
				return token;
			}
		}
//		else 
			return "fail";
		
	}

	//测试并打印数据库查询结果
	private static void show(ResultSet rs) {
	        System.out.println("-----------------");
	        System.out.println("执行结果如下所示:");
	        System.out.println("-----------------");
	        System.out.println("学号" + "\t\t" + "学校" + "\t\t" + "姓名"+ "\t\t" + "学院" + "\t\t" + "专业" + "\t\t" + "性别" + "\t\t" + "年级" + "\t\t" + "在读状态" +"\t\t" + "密码");
	        System.out.println("-----------------");
	        try {
	            while (rs.next()) {
	                System.out.println(rs.getString("学号") + "\t\t"
	                        + rs.getString("学校") + "\t\t"
	                        + rs.getString("姓名")+ "\t\t"
	                        + rs.getString("院系")+ "\t\t"
	                        + rs.getString("专业")+ "\t\t"
	                        + rs.getString("性别")+ "\t\t"
	                        + rs.getString("年级")+ "\t\t"
	                        + rs.getInt("在读状态")+ "\t\t"
	                        + rs.getString("密码"));
	            }
	        } catch (SQLException e) {
	            System.out.println("显示时数据库出错。");
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println("显示出错。");
	            e.printStackTrace();
	        }
	    }

	private static ResultSet selectSQL(String sql) {
		if(conn == null) {
        	try {
        		 //以下为从数据库连接池中获取一个数据库链接的整体操作
	        	Context initContext = new InitialContext();
	          	Context envContext = (Context)initContext.lookup("java:/comp/env");
	          	DataSource ds = (DataSource)envContext.lookup("jdbc/db20200213");
	            System.out.println("连接数据库……");
	    		conn = ds.getConnection();
	    	    System.out.println("连接conn: "+conn);
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		} catch (NamingException e) {
	    			e.printStackTrace();
    		} 
        }
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
	}



}
