package helloDB;
import java.io.IOException;
import java.nio.charset.Charset;
//FROM https://www.cnblogs.com/xingxing0521/p/5914008.html
import java.sql.*;
//import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class SQLDatabaseToCSV {
	// JDBC 驱动名，数据库 URL，数据库用户名，登录密码
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/db20200213?useUnicode=true&characterEncoding=UTF-8&useSSL=FALSE&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "111111";
    
    static Connection conn = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
  
    //返回数据库结果集rs
    ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(rs);//rs实际是类似coon连接的一串代码
        return rs;
    }
    
    // 打印结果集（用户信息表）
    void show(ResultSet rs) {
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
    
    //创建CSV对象并写入表头
    public static void write(){
        String filePath = "E:\\shrimpSUDAProject\\aMoneyManagement2020\\OutputCSV\\test1.csv";
        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("GBK"));
            //CsvWriter csvWriter = new CsvWriter(filePath);
            // 写表头
            String[] headers = {"学号","学校","姓名","学院","专业","性别","年级","在读状态","密码"};
//            String[] content = {"1234567898","苏大","刘舒璇","数学学院","信计","女","16",1,"zxcvbnm"};
            csvWriter.writeRecord(headers);
//            csvWriter.writeRecord(content);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	//CsvReader reader = new CsvReader(null);
    
    public static void main(String[] args) {
    	
    	//实例化本java类“SQLDatabaseToCSV”
        SQLDatabaseToCSV h = new SQLDatabaseToCSV();
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        
        //连接数据库
        try {
            Class.forName(JDBC_DRIVER);
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
        
      //打印查看待导出表格全部数据
      String sql = "select * from 用户信息表_copy1";
      ResultSet resultSet = h.selectSQL(sql); //结果集
      h.show(resultSet);
        
        
//       //带表头导出数据到csv---2019/3/30
//      String sql1 = "SELECT  * " + 
//                "into outfile 'E:/shrimpSUDAProject/aMoneyManagement2020/OutputCSV/用户信息表_copy1.csv' " +
//
//                "fields terminated by ',' " +
//        		  "optionally enclosed by '\"'"+
//                "lines terminated by '\\r\\n'"+
//
//        		"from(select '学号','学校','姓名','院系','专业','性别','年级','在读状态','密码' "+
//        		"union select 学号,学校,姓名,院系,专业,性别,年级,在读状态,密码 from 用户信息表_copy1) b";
//        try {
//			ps = conn.prepareStatement(sql1);
//	        rs = ps.executeQuery();
//	        System.out.println("数据表导出csv文件成功");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

        
//        //csv不带表头写入数据库---2019/3/31
//        //sql语句有错--20200213
//        String sqlInport = "LOAD DATA INFILE " + 
//        "E:/shrimpSUDAProject/aMoneyManagement2020/OutputCSV/用户信息表_copy1.csv "+
//        "INTO TABLE 用户信息表_copy2 "+
//        "CHARACTER SET 'utf8'"+
//        "FIELDS TERMINATED BY ',' "+
////        "OPTIONALLY ENCLOSED BY '\"'"+
////        "escaped by ',' "+
//        "lines terminated by '\\r\\n' "+
//        "ignore 1 lines"+
//        "(学号,学校,姓名,院系,专业,性别,年级,在读状态,密码 )";
//    
//        try {
//			ps2 = conn.prepareStatement(sqlInport);
//	        rs2 = ps2.executeQuery();
//	        System.out.println("csv文件写入数据表成功");
//	        conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

        
        
        
        
        
        
        
        
        
//        //有navicat-mysql，学生端不再需要后台的这种操作--20200213
//        //java批量导入数据库---2019/3/29
//        String sql = "INSERT INTO user_info VALUES(?,?,?,?,?,?,?,?)";
//        
//		try {
//				conn.setAutoCommit(false);
//				stmt = conn.prepareStatement(sql);
//				stmt.setInt(1,4321);
//				stmt.setString(2,"suda");
//				stmt.setString(3,"Monica");
//				stmt.setString(4,"Math");
//				stmt.setString(5,"Computer_Math");
//				stmt.setInt(6,1);
//				stmt.setString(7,"grade3");
//				stmt.setInt(8,1);
//	
//				for(int i=1; i<2; i++){
//					  stmt.addBatch();
//					  // 1w条记录插入一次
//					  if (i % 10000 == 0){
//					       stmt.executeBatch();
//					       conn.commit();
//					   }
//				}
//				//最后插入不足1w条的数据//        String sql = "INSERT INTO user_info VALUES(?,?,?,?,?,?,?,?)";
//			try {
//				conn.setAutoCommit(false);
//				stmt = conn.prepareStatement(sql);
//				stmt.setInt(1,4321);
//				stmt.setString(2,"suda");
//				stmt.setString(3,"Monica");
//				stmt.setString(4,"Math");
//				stmt.setString(5,"Computer_Math");
//				stmt.setInt(6,1);
//				stmt.setString(7,"grade3");
//				stmt.setInt(8,1);
//	
//				for(int i=1; i<2; i++){
//					  stmt.addBatch();
//					  // 1w条记录插入一次
//					  if (i % 10000 == 0){
//					       stmt.executeBatch();
//					       conn.commit();
//					   }
//				}
//				//最后插入不足1w条的数据
//				stmt.executeBatch();
//				conn.commit();
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//				stmt.executeBatch();
//				conn.commit();
//				
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
    }
}
