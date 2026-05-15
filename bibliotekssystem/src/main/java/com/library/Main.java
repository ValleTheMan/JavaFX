package com.library;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Försöker ansluta till PostgreSQL...");
        
        // Anropa vår nya klass
        Connection conn = DatabaseManager.getConnection();
        
        if (conn != null) {
            System.out.println("SUCCÉ! Uppkopplingen till databasen fungerar.");
            try {
                conn.close(); // Stäng anslutningen när vi testat klart
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("MISSLYCKANDES. Kontrollera URL, användarnamn och lösenord.");
        }
    }
}
