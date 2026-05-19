package com.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

    ItemDAO dao = new ItemDAO();
    System.out.println("Söker efter 'Java' i databasen...");
    List<Item> searchResults = dao.searchByTitle("Java");

    for (Item item : searchResults) {
    System.out.println("Hittade: " + item.getTitle() + " (Typ: " + item.getItemClassification() + ")");
    }

        Label välkomstText = new Label("Välkommen till Bibliotekssystemet! Databasen är redo.");
        StackPane root = new StackPane(välkomstText);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Mitt Bibliotek");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Startar systemet...");
        
        Connection conn = DatabaseManager.getConnection();
        if (conn != null) {
            System.out.println("SUCCÉ! Uppkopplingen till databasen fungerar.");
        }

        launch(args); 
    }
}