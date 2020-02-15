package helloDB;
//FROM  https://www.cnblogs.com/tina-smile/p/3587688.html
import java.sql.*;
public class SQLsTest {
    private Connection conn = null;
    PreparedStatement statement = null;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/db20200213?useUnicode=true&characterEncoding=UTF-8&useSSL=FALSE&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "111111";
    // 加载驱动程序以连接数据库
    void connSQL() {
        try { 
            Class.forName(JDBC_DRIVER); 
            System.out.println("连接数据库……");
            conn = DriverManager.getConnection( DB_URL,USER, PASS ); 
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
    }

    //关闭数据库连接
    void deconnSQL() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.out.println("关闭数据库问题 ：");
            e.printStackTrace();
        }
    }

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

    // 执行sql插入语句
    boolean insertSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }
    
    //执行sql删除语句
    boolean deleteSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("删除数据时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("删除时出错：");
            e.printStackTrace();
        }
        return false;
    }
    
    //执行sql更新语句
    boolean updateSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("更新数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("更新时出错：");
            e.printStackTrace();
        }
        return false;
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

    public static void main(String args[]) {
    	SQLsTest h = new SQLsTest();
        h.connSQL();
        String s = "select * from 用户信息表_copy1";
        String insert = "insert into 用户信息表_copy1 values('1234567898','苏大','刘舒璇','数学学院','信计','女','16',1,'zxcvbnm')";
        String update = "update 用户信息表_copy1 set 院系 ='英语学院' where 学号= '1234567898'";
        String delete = "delete from 用户信息表_copy1  where 学号= '1234567898'";

        if (h.insertSQL(insert) == true) {
            System.out.println("\ninsert successfully");
            ResultSet resultSet = h.selectSQL(s);//结果集
            h.show(resultSet);
        }
        if (h.updateSQL(update) == true) {
            System.out.println("\nupdate successfully");
            ResultSet resultSet = h.selectSQL(s);//结果集
            h.show(resultSet);
        }
        if (h.deleteSQL(delete) == true) {
            System.out.println("\ndelete successfully");
            ResultSet resultSet = h.selectSQL(s);//结果集
            h.show(resultSet);
        }
        h.deconnSQL();
    }
}


/*运行结果如下（测试时间：202002131808）：
 * 
 连接数据库……
连接conn: com.mysql.cj.jdbc.ConnectionImpl@1a04f701

insert successfully
-----------------
执行结果如下所示:
-----------------
学号		学校		姓名		学院		专业		性别		年级		在读状态		密码
-----------------
1234567898		苏大		刘舒璇		数学学院		信计		女		16		1		zxcvbnm
1607400001		苏州大学		锁依琴		数学科学学院		数学与应用数学（基地）		男		16		1		63YXh9fM
1607400002		苏州大学		颜辰		数学科学学院		数学与应用数学（基地）		男		16		1		iW7mvUmT

update successfully
-----------------
执行结果如下所示:
-----------------
学号		学校		姓名		学院		专业		性别		年级		在读状态		密码
-----------------
1234567898		苏大		刘舒璇		英语学院		信计		女		16		1		zxcvbnm
1607400001		苏州大学		锁依琴		数学科学学院		数学与应用数学（基地）		男		16		1		63YXh9fM
1607400002		苏州大学		颜辰		数学科学学院		数学与应用数学（基地）		男		16		1		iW7mvUmT

delete successfully
-----------------
执行结果如下所示:
-----------------
学号		学校		姓名		学院		专业		性别		年级		在读状态		密码
-----------------
1607400001		苏州大学		锁依琴		数学科学学院		数学与应用数学（基地）		男		16		1		63YXh9fM
1607400002		苏州大学		颜辰		数学科学学院		数学与应用数学（基地）		男		16		1		iW7mvUmT

 */
 

