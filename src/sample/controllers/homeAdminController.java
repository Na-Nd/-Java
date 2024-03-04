package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.persons.Admin;

public class homeAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editExistingBooksButton;

    @FXML
    private Button toMainWindowButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    void initialize() {
        editExistingBooksButton.setOnAction(event -> {
            openNewScene("/sample/addEditChangeBooksAdmin.fxml");
        });

        toMainWindowButton.setOnAction(event -> {
            openNewScene("/sample/sample.fxml");
        });

        toMainWindowButton.setOnAction(event ->{
            System.out.println("to main window from admin home");
            openNewScene("/sample/sample.fxml");
        });

        Admin loginedAdmin = loginAdminController.getLoginedAdmin();
        Admin registeredAdmin = registerAdminController.getRegisteredAdmin();
        if (registeredAdmin != null) {
            welcomeLabel.setText("Добро пожаловать, " + registeredAdmin.getName() + "!");
        } else if (loginedAdmin != null) {
            welcomeLabel.setText("Добро пожаловать, " + loginedAdmin.getName() + "!");
        } else {
            welcomeLabel.setText("Пользователь не обнаружен");
        }

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
}
