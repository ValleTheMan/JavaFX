package com.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class ItemViewController {

    @FXML private ImageView logoImageView;
    @FXML private ImageView bookCoverImageView;
    @FXML private Text titleText;
    @FXML private Text authorText;
    @FXML private Text isbnText;
    @FXML private TextArea descriptionArea;
    @FXML private Button borrowButton;
    @FXML private Button backButton;
    @FXML private Button loginButton;

    private Item currentItem;
    private ItemDAO itemDAO = new ItemDAO();

    public void setItem(Item item) {
        this.currentItem = item;
        titleText.setText("Titel: " + item.getTitle());
        
        if (item instanceof Book) {
            Book book = (Book) item;
            authorText.setText("Författare: " + book.getAuthor());
            isbnText.setText("ISBN: " + book.getIsbn());
            descriptionArea.setText("Klassificering: " + book.getBookClassification() + "\nKategori: " + book.getItemCategory());
        } else if (item instanceof DVD) {
            DVD dvd = (DVD) item;
            authorText.setText("Regissör: " + dvd.getDirector());
            isbnText.setText("Genre: " + dvd.getGenre());
            descriptionArea.setText("Skådespelare: " + dvd.getActor() + "\nKategori: " + dvd.getItemCategory());
        } else if (item instanceof Magazine) {
            authorText.setText("Typ: Tidskrift");
            isbnText.setText("Referenslitteratur (Får ej lånas hem)");
            descriptionArea.setText("Kategori: " + item.getItemCategory());
        }
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> {
            SceneManager.switchScene("Search_Item.fxml", "Bibliotekssystem - Sök media");
        });

        loginButton.setOnAction(event -> {
            SceneManager.openLogin("Item-view.fxml");
        });

        borrowButton.setOnAction(event -> {
            
            if (!UserSession.isLoggedIn()) {
                borrowButton.setText("Logga in först!");
                borrowButton.setStyle("-fx-text-fill: red;");
                return;
            }

            if (currentItem instanceof Magazine) {
                borrowButton.setText("Kan ej lånas!");
                borrowButton.setStyle("-fx-text-fill: red;");
                return;
            }

            String barcode = itemDAO.getAvailableBarcode(currentItem.getItemID());
            if (barcode == null) {
                borrowButton.setText("Utlånad/Slut!");
                borrowButton.setStyle("-fx-text-fill: red;");
                return;
            }

            LibraryUser user = UserSession.getLoggedInUser();
            String receiptNumber = "REC-" + System.currentTimeMillis();
            
            boolean success = itemDAO.createLoan(receiptNumber, user.getUserID(), barcode);

            if (success) {
                borrowButton.setText("Lån registrerat!");
                borrowButton.setStyle("-fx-text-fill: green;");
                borrowButton.setDisable(true);
            } else {
                borrowButton.setText("Något gick fel...");
                borrowButton.setStyle("-fx-text-fill: red;");
            }
        });
    }
}