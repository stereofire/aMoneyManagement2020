package helloDB;
import java.sql.*;
public class try_much {

    // JDBC 驱动名，数据库 URL，数据库用户名，登录密码
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/db1st?useSSL=FALSE&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "111111";
    
	public static void main(String[] args) {
	    Connection conn = null;
	    Statement stmt = null;
        //PreparedStatement prestmt = null;
        ResultSet rs = null;
        try {
        	// 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        	// 打开链接
            System.out.println("连接数据库……");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        System.out.println("连接conn: "+conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // 执行查询
        System.out.println("实例化Statement对象...");
        try {
			stmt = conn.createStatement();
			//prestmt = conn.prepareStatement("INSERT INTO user_info VALUES(?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        try {
//			prestmt.setInt(1, 2134);
//			prestmt.setString(2, "suda");
//			prestmt.setString(3, "Monica");
//			prestmt.setString(4, "Math");
//			prestmt.setString(5, "computer_math");
//			prestmt.setInt(6, 1);
//			prestmt.setString(7,"grade3");
//			prestmt.setInt(8, 1);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
        String sql1,sql2;
        sql1 = "INSERT INTO user_info VALUES(12345,'suda','Jack','Math','computer_math',0,'grade3',1)";
        sql2 = "SELECT * FROM user_info";
       // sql2 = "INSERT INTO user_info VALUES(?,?,?,?,?,?,?,?)";
        try {
        	//prestmt.executeUpdate(sql2);
        	stmt.executeUpdate(sql1);
			rs =stmt.executeQuery(sql2);
			//System.out.println(rs);
			while (rs.next()) {
				int id = rs.getInt("stu_id");
				String collage = rs.getString("collage"); 
				String stu_name = rs.getString("stu_name"); 
				String school = rs.getString("school"); 
				String department = rs.getString("department"); 
				int gender = rs.getInt("gender"); 
				String grade = rs.getString("grade"); 
				int status = rs.getInt("status"); 
				// 输出数据
				System.out.print("ID: " + id+" ");
				System.out.print("collage: " + collage+" ");
				System.out.print("stu_name: " + stu_name+" ");
				System.out.print("school: " + school+" ");
				System.out.print("department: " + department+" ");
				System.out.print("gender: " + gender+" ");
				System.out.print("grade: " + grade+" ");
				System.out.print("status: " + status+" ");
				System.out.print("\n");
			}
			// 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
}



//String sql = "insert into table *****";
//con.setAutoCommit(false);
//ps = con.prepareStatement(sql);
//for(int i=1; i<65536; i++){
//  ps.addBatch();
//  // 1w条记录插入一次
//  if (i % 10000 == 0){
//       ps.executeBatch();
//       con.commit();
//   }
//}
////最后插入不足1w条的数据
//ps.executeBatch();
//con.commit();
