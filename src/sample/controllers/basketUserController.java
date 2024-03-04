package sample.controllers;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Constants;
import sample.DataBaseHandler;
import sample.persons.User;

public class basketUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane basketScrollPane;

    @FXML
    private VBox catalogVBox;

    @FXML
    private Label infoLabel;

    @FXML
    private Button toUserHomeButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void loadBooksToBasket() {
        try {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            // Передаём авторизованного пользователя, для работы с его логином
            User loginedUser = homeUserController.getLoginedUserToCatalogAndToBasket();
            User registeredUser = homeUserController.getRegisteredUserToCatalogAndToBasket();

            String userLogin = (loginedUser != null) ? loginedUser.getLogin() : (registeredUser != null) ? registeredUser.getLogin() : null;

            if (userLogin != null) {
                String selectQuery = "SELECT * FROM " + Constants.BOOKS_TABLE + " WHERE " + Constants.BOOK_OWNER_LOGIN + "='" + userLogin + "'";

                Statement statement = dataBaseHandler.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while (resultSet.next()) {
                    String bookTitle = resultSet.getString(Constants.BOOK_TITLE);
                    String bookImagePath = resultSet.getString(Constants.BOOK_LOCATION);

                    ImageView bookImageView = new ImageView(new Image("file:" + bookImagePath));
                    bookImageView.setFitHeight(190.0);
                    bookImageView.setFitWidth(147.0);

                    Button bookButton = new Button("Удалить");
                    bookButton.setStyle("-fx-background-color: #49423D; -fx-text-fill: #FFFFFF;");

                    HBox bookEntry = new HBox(bookImageView, bookButton);
                    bookEntry.setAlignment(Pos.CENTER); // Центрируем содержимое HBox для лучшего вида
                    catalogVBox.getChildren().add(bookEntry);


                    bookButton.setOnAction(event -> {
                        try {
                            // Обновляем bookOwnerLogin в БД
                            String updateQuery = "UPDATE " + Constants.BOOKS_TABLE + " SET " + Constants.BOOK_OWNER_LOGIN + "='' WHERE " + Constants.BOOK_TITLE + "=?";
                            PreparedStatement updateStatement = dataBaseHandler.getConnection().prepareStatement(updateQuery);
                            updateStatement.setString(1, bookTitle);
                            updateStatement.executeUpdate();

                            // Удаляем соответствующий HBox из VBox
                            catalogVBox.getChildren().remove(bookEntry);
                            openNewScene("/sample/basketUser.fxml");
                            System.out.println("Книга '" + bookTitle + "' удалена из корзины пользователя " + userLogin);
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        loadBooksToBasket();

        User loginedUser = homeUserController.getLoginedUserToCatalogAndToBasket();
        User registeredUser = homeUserController.getRegisteredUserToCatalogAndToBasket();
        if (registeredUser != null) {
            welcomeLabel.setText(registeredUser.getName());
        } else if (loginedUser != null) {
            welcomeLabel.setText(loginedUser.getName());
        } else {
            welcomeLabel.setText("Пользователь не обнаружен");
        }

        toUserHomeButton.setOnAction(event -> {
            openNewScene("/sample/homeUser.fxml");
        });


    }
    public void openNewScene(String window) {
        Stage currentStage = (Stage) toUserHomeButton.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
