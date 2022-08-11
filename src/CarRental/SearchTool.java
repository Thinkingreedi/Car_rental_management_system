package CarRental;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* 查找工具类 */
public class SearchTool {
    public static boolean hasData(String strData) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        CarRental leaseForm = new CarRental();
        boolean sign = false;

        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();

            if (!CharTool.isNumeric(strData)) {
                String sql = "select * from lease_form.lease_form where name = '" + strData + "'";//!
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String str = resultSet.getString("name");
                    if (str != null) {
                        sign = true;
                    }
                    break;
                }
            } else {
                String sql = "select * from lease_form.lease_form where phone_number = '" + strData + "'";//!
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String str = resultSet.getString("name");
                    if (str != null) {
                        sign = true;
                    }
                    break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.release(resultSet, statement, connection);
        return sign;
    }
}
