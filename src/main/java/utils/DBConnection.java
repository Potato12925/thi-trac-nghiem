package utils;

import java.sql.Connection;
import java.sql.DriverManager;

import config.DBConfig;

public class DBConnection {

    public static Connection getConnection() {
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(
                    DBConfig.URL,
                    DBConfig.USER,
                    DBConfig.PASSWORD
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}