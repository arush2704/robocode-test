package org.example;

import java.sql.*;

public class VulnerableSQL {

    public static void main(String[] args) throws SQLException {
        String userInput = "admin' OR '1'='1";

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "user", "pass");
        Statement stmt = conn.createStatement();

        // Vulnerable to SQL Injection
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";

        ResultSet rs = stmt.executeQuery(query);

        // Always close resources in production code
        rs.close();
        stmt.close();
        conn.close();
    }
}