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
import sample.persons.Salor;

public class homeSalorController { // Saler >:|

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button showSalesChartsButton;

    @FXML
    private Button toMainWindowButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    void initialize() {
        toMainWindowButton.setOnAction(event ->{
            System.out.println("to main window from userHome");
            openNewScene("/sample/sample.fxml");
        });
        Salor loginedSalor = loginSalorController.getLoginedSalor();
        Salor registeredSalor = registerSalorController.getRegisteredSalor();
        if (registeredSalor != null) {
            welcomeLabel.setText("Добро пожаловать, " + registeredSalor.getName() + "!");
        } else if (loginedSalor != null) {
            welcomeLabel.setText("Добро пожаловать, " + loginedSalor.getName() + "!");
        } else {
            welcomeLabel.setText("Пользователь не обнаружен");
        }

        showSalesChartsButton.setOnAction(event -> {
            openNewScene("/sample/salorGraphs.fxml");
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

}
