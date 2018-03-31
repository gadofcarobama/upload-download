import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJDBCUtils {
    //获取dc3p0数据库连接池
    static ComboPooledDataSource ds=new ComboPooledDataSource();
    //获取数据库连接的方法
    public static Connection getConnection() throws Exception{
        Connection conn=ds.getConnection();
        return conn;
    }
    //返回datasource
    public static DataSource getDataSource(){
        return ds;
    }

//关闭流
public static void clear(Connection conn, Statement stat){

   if (stat!=null){
       try {
           stat.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }stat=null;
   }
    if (conn!=null){
        try {
            conn.close();
        } catch (SQLException e) {

        }conn=null;
    }
 }
    public static void clear(ResultSet rs, Connection conn, Statement stat){
         if (rs!=null){
             try {
                 rs.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }rs=null;
         }

        if (stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }stat=null;
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {

            }conn=null;
        }
    }

}