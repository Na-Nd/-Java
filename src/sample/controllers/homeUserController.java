package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.persons.User;

public class homeUserController {

    public static User loginedUserToCatalogAndToBasket;
    public static User registeredUserToCatalogAndToBasket;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button showBasketButton;

    @FXML
    private Button showCatalogButton;

    @FXML
    private Button toMainWindowButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    void initialize() {
        toMainWindowButton.setOnAction(event ->{
            openNewScene("/sample/sample.fxml");
        });

        User loginedUser = loginUserController.getLoginedUser();
        User registeredUser = registerUserController.getRegisteredUser();
        loginedUserToCatalogAndToBasket = loginedUser;
        registeredUserToCatalogAndToBasket = registeredUser;
        if (registeredUser != null) {
            welcomeLabel.setText("Добро пожаловать, " + registeredUser.getName() + "!");
        } else if (loginedUser != null) {
            welcomeLabel.setText("Добро пожаловать, " + loginedUser.getName() + "!");
        } else {
            welcomeLabel.setText("Пользователь не обнаружен");
        }

        showCatalogButton.setOnAction(event ->{
            openNewScene("/sample/catalogUser.fxml");
        });

        showBasketButton.setOnAction(event -> {
            openNewScene("/sample/basketUser.fxml");
        });

    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) toMainWindowButton.getScene().getWindow();
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

    public static User getLoginedUserToCatalogAndToBasket(){
        return loginedUserToCatalogAndToBasket;
    }
    public static User getRegisteredUserToCatalogAndToBasket() { return registeredUserToCatalogAndToBasket; }


}
