package com.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class MyAccountController {

    @FXML private Button backButton;
    @FXML private Button logoutButton;
    @FXML private ListView<String> loanListView;
    @FXML private Text welcomeText;
    @FXML private Button returnButton;

    @FXML
    public void initialize() {

        if (UserSession.isLoggedIn()) {
            LibraryUser user = UserSession.getLoggedInUser();
            welcomeText.setText("Välkommen, " + user.getName() + "!");
        } else {
            welcomeText.setText("Välkommen till din sida!");
        }

        backButton.setOnAction(event -> {
            SceneManager.switchScene("Search_Item.fxml", "Bibliotekssystem - Sök media");
        });

        logoutButton.setOnAction(event -> {
            SceneManager.switchScene("Search_Item.fxml", "Bibliotekssystem - Sök media");
        });

        loanListView.getItems().addAll(
            "Dragon Treasure - Lånat: 2026-05-10, Retur senast: 2026-06-10",
            "Mjukvarutestning av Yogesh Singh - Lånat: 2026-05-15, Retur senast: 2026-06-15"
        );

        if (returnButton != null) {
            returnButton.setOnAction(event -> {
                String selected = loanListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    loanListView.getItems().remove(selected);
                }
            });
        }
    }
}