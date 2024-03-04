package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Animations;
import sample.DataBaseHandler;
import sample.persons.Book;

public class addEditChangeBooksAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBookButton;

    @FXML
    private TextField author_field;

    @FXML
    private Button deleteBookByTitleButton;

    @FXML
    private TextField description_field;

    @FXML
    private TextField pngLocation_field;

    @FXML
    private TextField title_field;

    @FXML
    private Button toMainWindowButton;

    @FXML
    private TextField deleteIfOwnerExists_field;

    @FXML
    private TextField titleDelete_field;

    @FXML
    void initialize() {
        toMainWindowButton.setOnAction(event -> {
            openNewScene("/sample/homeAdmin.fxml");
        });

        addBookButton.setOnAction(event -> {
            String titleText = title_field.getText().trim();
            String authorText = author_field.getText().trim();
            String descriptionText = description_field.getText().trim();
            String countText = "1"; // В методе регистрации книги написано, почему 1
            String pngLocation = pngLocation_field.getText().trim();

            if(!titleText.equals("") && !authorText.equals("") && !descriptionText.equals("") && !countText.equals("") && !pngLocation.equals("")){
                registerNewBook();
                System.out.println("Обновление страницы");
                openNewScene("/sample/addEditChangeBooksAdmin.fxml");
            }
            else{
                if(titleText.equals("")) {
                    Animations loginAnim = new Animations(title_field);
                    loginAnim.playAnim();
                }
                if(authorText.equals("")){
                    Animations passwordAnim = new Animations(author_field);
                    passwordAnim.playAnim();
                }
                if(descriptionText.equals("")){
                    Animations nameAnim = new Animations(description_field);
                    nameAnim.playAnim();
                }
                if(pngLocation.equals("")){
                    Animations surnameAnim = new Animations(pngLocation_field);
                    surnameAnim.playAnim();
                }
                System.out.println("Не все поля заполнены или что-то заполнено неправильно");
            }

        });


        deleteBookByTitleButton.setOnAction(event -> {
            String ifOwnerExists = deleteIfOwnerExists_field.getText().trim();
            String titleText = titleDelete_field.getText().trim();
            if(!titleText.equals("")){
                if(!ifOwnerExists.equals("")){
                    deleteBookByOwner();
                    // Перезагружаем окно
                    openNewScene("/sample/addEditChangeBooksAdmin.fxml");
                }
                else{
                    deleteBook();
                    openNewScene("/sample/addEditChangeBooksAdmin.fxml");
                }

            }
            else {
                Animations deleteAnim = new Animations(titleDelete_field);
                deleteAnim.playAnim();
            }
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

    public void registerNewBook(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String registerTitle = title_field.getText();
        String registerAuthor = author_field.getText();
        String registerDescription = description_field.getText();
        String registerCount = "1"; // Устанавливается всегда в 1. Слишком поздно понял, что лучше было бы сделать большую строку для bookOwnerLogin и пихать туда всех владельцев, и затем работать с их количеством
        int count = Integer.parseInt(registerCount);
        String registerPngLocation = pngLocation_field.getText();

        Book book = new Book(registerTitle, registerAuthor, registerDescription, count, "", registerPngLocation);
        dataBaseHandler.registerBookInDB(book);
    }

    public void deleteBook() { // Если владельца нет
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String deleteTitle = titleDelete_field.getText();
        Book bookToDelete = new Book(deleteTitle, "", "", 0, "", "");

        dataBaseHandler.deleteBookFromDB(bookToDelete);
    }

    public void deleteBookByOwner() { //Если владелец есть
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String deleteTitle = titleDelete_field.getText();
        String ownerLogin = deleteIfOwnerExists_field.getText();
        Book bookToDelete = new Book(deleteTitle, "", "", 0, ownerLogin, "");

        dataBaseHandler.deleteBookByOwnerFromDB(bookToDelete);
    }



}
