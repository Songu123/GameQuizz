package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/quizgame";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        System.out.println("Connect sucess....");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
