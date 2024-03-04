package sample.controllers;

import java.io.IOException;
import java.net.URL;
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
import sample.Constants;
import sample.DataBaseHandler;
import sample.persons.Salor;

public class registerSalorController {

    public static Salor registeredSalor;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private TextField name_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button register_Button;

    @FXML
    private TextField salorKey_field;

    @FXML
    private TextField surname_field;

    @FXML
    private Button toHome_Button;

    @FXML
    void initialize() {
        toHome_Button.setOnAction(event -> {
            System.out.println("to home from salor");
            openNewScene("/sample/sample.fxml");
        });

        register_Button.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String passwordText = password_field.getText().trim();
            String salorKey = salorKey_field.getText().trim();
            String nameText = name_field.getText().trim();
            String surnameText = surname_field.getText().trim();

            if(!loginText.equals("") && !passwordText.equals("") && salorKey.equals(Constants.SALOR_KEY) && !nameText.equals("") && !surnameText.equals("")){ // Если поля не пустые
                registerNewSalor();
                openNewScene("/sample/homeSalor.fxml");
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
                if(!salorKey.equals(Constants.ADMIN_KEY)){
                    Animations salorKeyAnim = new Animations(salorKey_field);
                    salorKeyAnim.playAnim();
                }
                if(nameText.equals("")){
                    Animations nameAnim = new Animations(name_field);
                    nameAnim.playAnim();
                }
                if(surnameText.equals("")){
                    Animations surnameAnim = new Animations(surname_field);
                    surnameAnim.playAnim();
                }
                System.out.println("Не все поля заполнены или что-то заполнено неправильно");
            }
        });
    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) toHome_Button.getScene().getWindow();
        currentStage.hide();

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


    public void registerNewSalor(){


        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String registerName = name_field.getText();
        String registerSurname = surname_field.getText();
        String regusterLogin = login_field.getText();
        String registerPassword = password_field.getText();

        Salor salor = new Salor(registerName, registerSurname, regusterLogin, registerPassword);
        registeredSalor = salor;

        dataBaseHandler.registerSalorInDB(salor);
    }

    public static Salor getRegisteredSalor(){ // Для доступа к зарегистрированному пользователю
        return registeredSalor;
    }

}
