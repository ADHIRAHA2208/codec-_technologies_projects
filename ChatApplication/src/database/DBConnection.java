package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/chat_app",
                "root",
                "chinu@123"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
