package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Animations;
import sample.DataBaseHandler;
import sample.persons.Admin;
import sample.persons.User;

public class loginAdminController {

    public static Admin loginedAdmin;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button toMainWindowButton;

    @FXML
    void initialize() {
        toMainWindowButton.setOnAction(event -> {
            openNewScene("/sample/sample.fxml");
        });

        loginButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String passwordText = password_field.getText().trim();

            if(!loginText.equals("") && !passwordText.equals("")){ // Если поля не пустые
                loginNewAdmin(loginText, passwordText); //
            }
            else{
                if(loginText.equals("")) {
                    Animations loginAnim = new Animations(login_field);
                    loginAnim.playAnim();
                }
                if(passwordText.equals("")){
                    Animations passwordAnim = new Animations(password_field);
                    passwordAnim.playAnim();
                }
                System.out.println("Не все поля заполнены или что-то заполнено неправильно");
            }
        });

    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) toMainWindowButton.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load(); //
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    private void loginNewAdmin(String loginText, String passwordText) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        Admin admin = new Admin();
        admin.setLogin(loginText);
        admin.setPassword(passwordText);
        ResultSet resultSet = dataBaseHandler.getAdminFromDB(admin);
        boolean flag = false;

        try {
            if (resultSet.next()) {
                flag = true;
                String name = resultSet.getString("adminname");
                admin.setName(name);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (flag) {
            loginedAdmin = admin;
            openNewScene("/sample/homeAdmin.fxml"); //
        }
    }

    public static Admin getLoginedAdmin(){
        return loginedAdmin;
    }

}
