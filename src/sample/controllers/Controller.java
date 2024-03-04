package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class Controller {

    public boolean adminFlag = false;
    public boolean salorFlag = false;
    public boolean userFlag = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleButton adminSignInToggle;

    @FXML
    private Button registerButton;

    @FXML
    private ToggleButton salorSignInToggle;

    @FXML
    private Button signInButton;

    @FXML
    private ToggleButton userSignInToggle;

    @FXML
    void initialize() {

        adminSignInToggle.setOnAction(event -> {
            adminFlag = true;
            salorFlag = false;
            userFlag = false;
            System.out.println("Admin");
        });

        salorSignInToggle.setOnAction(event -> {
            adminFlag = false;
            salorFlag = true;
            userFlag = false;
            System.out.println("Salor");
        });

        userSignInToggle.setOnAction(event -> {
            adminFlag = false;
            salorFlag = false;
            userFlag = true;
            System.out.println("User");
        });

        registerButton.setOnAction(event -> {
            if(adminFlag){
                openNewScene("/sample/registerAdmin.fxml");
            }
            if(salorFlag){
                openNewScene("/sample/registerSalor.fxml");
            }
            if(userFlag){
                openNewScene("/sample/registerUser.fxml");
            }
        });

        signInButton.setOnAction(event -> {
            if(adminFlag){
                openNewScene("/sample/loginAdmin.fxml");
            }
            if(salorFlag){
                openNewScene("/sample/loginSalor.fxml");
            }
            if(userFlag){
                openNewScene("/sample/loginUser.fxml");
            }
        });

    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) registerButton.getScene().getWindow();
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
