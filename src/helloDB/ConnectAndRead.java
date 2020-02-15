package helloDB;
import java.sql.*;
public class ConnectAndRead {
    // JDBC 驱动名，数据库 URL，数据库用户名，登录密码
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/db20200213?useUnicode=true&characterEncoding=UTF-8&useSSL=FALSE&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "111111";
    
	public static void main(String[] args) {
	    Connection conn = null;
	    Statement stmt = null;
        ResultSet rs = null;
        try {
        	// 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        	// 打开链接
            System.out.println("连接数据库……");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        System.out.println("连接conn: "+conn);
		} 
        catch ( ClassNotFoundException cnfex ) {
            System.err.println(
            "装载 JDBC/ODBC 驱动程序失败。" );
            cnfex.printStackTrace(); 
        } 
        catch ( SQLException sqlex ) {
            System.err.println( "无法连接数据库" );
            sqlex.printStackTrace(); 
        }
        
        // 执行查询
        System.out.println("实例化Statement对象...");
        try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        String sql1,sql2;
        //sql1 = "INSERT INTO 用户信息表  VALUES(1234567890,'suda','张爱静','数学学院','信计','女','16',1,'zxcvbnm')";
        sql2 = "SELECT * FROM 用户信息表";
        
        try {
        	//stmt.executeUpdate(sql1);
			rs =stmt.executeQuery(sql2);
			System.out.println(rs);
			while (rs.next()) {
				String id = rs.getString("学号");
				String collage = rs.getString("学校"); 
				String stu_name = rs.getString("姓名"); 
				String school = rs.getString("院系"); 
				String department = rs.getString("专业"); 
				String gender = rs.getString("性别"); 
				String grade = rs.getString("年级"); 
				int status = rs.getInt("在读状态"); 
				String pass = rs.getString("密码");
				// 输出数据
				System.out.print("学号: " + id+" ");
				System.out.print("学校: " + collage+" ");
				System.out.print("姓名: " + stu_name+" ");
				System.out.print("院系: " + school+" ");
				System.out.print("专业: " + department+" ");
				System.out.print("性别: " + gender+" ");
				System.out.print("年级: " + grade+" ");
				System.out.print("状态: " + status+" ");
				System.out.print("密码" + pass+" ");
				System.out.print("\n");
			}
			// 完成后关闭连接
            rs.close();
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}