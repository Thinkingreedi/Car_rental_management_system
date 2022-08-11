package CarRental;

import java.sql.*;

//数据库连接
public class JDBCUtils {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {  //应用程序与数据库之间的接口
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/lease_form?useSSL=true";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(url, username, password);  //建立数据库连接
    }

    public static void release(Statement statement, Connection connection) {  //释放资源
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public static void release(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet = null;
        }
        release(statement, connection);
    }
}