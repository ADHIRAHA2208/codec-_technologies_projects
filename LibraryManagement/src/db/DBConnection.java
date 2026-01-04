package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);

            Statement stmt = conn.createStatement();

            // Book Table
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "author TEXT," +
                    "quantity INTEGER)");

            // Issue Table
            stmt.execute("CREATE TABLE IF NOT EXISTS issued (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "book_id INTEGER," +
                    "user_name TEXT," +
                    "issue_date TEXT," +
                    "return_date TEXT)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
