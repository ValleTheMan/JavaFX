package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public LibraryUser validateLogin(String loginIdentifier) {
        String sql = "SELECT * FROM LibraryUser WHERE email = ? OR name = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, loginIdentifier);
            pstmt.setString(2, loginIdentifier);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new LibraryUser(
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("userCategory")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Fel vid inloggning i databasen: " + e.getMessage());
        }
        return null;
    }
}