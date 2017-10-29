package cn.xt.base.base.web.lib.test.profile;

import java.sql.*;

public class DbTest {
    public static void main(String[] args) {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.1.220:3306/centnet_fpi";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String sql = "select * from voip_ip";
            ResultSet rs = statement.executeQuery(sql);

            rs.last();
            System.out.println(rs.getString("ip")+","+rs.getDate("insert_date"));

            rs.first();
            System.out.println(rs.getString("ip")+","+rs.getDate("insert_date"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
