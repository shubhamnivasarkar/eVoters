package com.core.utils;

import java.sql.*;

public class DBUtils {
    private static Connection conn;

    public static Connection getConnection () throws ClassNotFoundException, SQLException {
        if (conn != null) return conn;
        String hostname = "localhost:3306";
        String database = "wbj";
        String username = "anshuman";
        String password = "029511%&@Me";
        String url = "jdbc:mysql://" + hostname + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    
    public static void closeConnection () throws SQLException {
        if (conn != null) conn.close();
    }
}
