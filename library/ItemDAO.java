package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public List<Item> searchByTitle(String titleKeyword) {
        List<Item> resultList = new ArrayList<>();

        String sql = "SELECT i.itemID, i.title, i.itemClassification, i.itemCategory, " +
                     "b.author, b.isbn, b.bookClassification, " +
                     "d.director, d.genre, d.actor " +
                     "FROM Item i " +
                     "LEFT JOIN Book b ON i.itemID = b.itemID " +
                     "LEFT JOIN DVD d ON i.itemID = d.itemID " +
                     "WHERE i.title ILIKE ?"; 

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + titleKeyword + "%"); 
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("itemID");
                String title = rs.getString("title");
                String classification = rs.getString("itemClassification");
                String category = rs.getString("itemCategory");

                if ("Book".equalsIgnoreCase(classification)) {
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String bookClass = rs.getString("bookClassification");

                    Book book = new Book(id, title, category, author, isbn, bookClass);
                    resultList.add(book);

                } else if ("DVD".equalsIgnoreCase(classification)) {
                    String director = rs.getString("director");
                    String genre = rs.getString("genre");
                    String actor = rs.getString("actor");

                    DVD dvd = new DVD(id, title, category, director, genre, actor);
                    resultList.add(dvd);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ett fel uppstod vid sökning i databasen: " + e.getMessage());
        }

        return resultList; 
    }
}