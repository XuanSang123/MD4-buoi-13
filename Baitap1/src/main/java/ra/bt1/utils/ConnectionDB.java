package ra.bt1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyhocsinh?createDatabaseIfNotExist=true", "root", "0964417590");
            return connection;
        } catch (ClassNotFoundException e) {
                        System.err.println("Driver not found");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("lỗi kết nối");
            throw new RuntimeException(e);
        }
    }

    ;

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
