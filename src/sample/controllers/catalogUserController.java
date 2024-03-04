package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.sql.Statement;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Constants;
import sample.DataBaseHandler;
import sample.persons.Book;
import sample.persons.User;
import javafx.scene.layout.HBox;

public class catalogUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addToBasketButton;

    @FXML
    private ImageView searchBookImage;

    @FXML
    private ScrollPane catalogScrollPane;

    @FXML
    private VBox catalogVBox;

    @FXML
    private Label infoLabel;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button toUserHomeButton;

    @FXML
    private void loadImagesToImageViews() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String selectQuery = "SELECT * FROM " + Constants.BOOKS_TABLE;

        try {
            Statement statement = dataBaseHandler.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            int imageIndex = 1;
            while (resultSet.next()) {
                String bookTitle = resultSet.getString(Constants.BOOK_TITLE);
                String imagePath = resultSet.getString(Constants.BOOK_LOCATION);
                String bookDescription = resultSet.getString(Constants.BOOK_DESCRIPTION);
                String bookOwnerLogin = resultSet.getString(Constants.BOOK_OWNER_LOGIN);

                // Проверяем, что поле bookOwnerLogin пустое
                if (bookOwnerLogin.equals("")) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(190.0);
                    imageView.setFitWidth(147.0);

                    // Загружаем изображение в компонент ImageView
                    Image image = new Image("file:" + imagePath);
                    imageView.setImage(image);

                    // Обработчик события для нажатия на ImageView
                    imageView.setOnMouseClicked(event -> {
                        StringBuilder formattedText = new StringBuilder();
                        int charCount = 0;
                        StringBuilder word = new StringBuilder();

                        for (char c : bookDescription.toCharArray()) {
                            if (Character.isWhitespace(c)) {
                                formattedText.append(word).append(c);
                                charCount += word.length() + 1;
                                word.setLength(0);
                            } else {
                                word.append(c);
                            }

                            if (charCount >= 25) {
                                formattedText.append("\n");
                                charCount = 0;
                            }
                        }

                        formattedText.append(word); // Добавляем оставшееся слово или пробел, если последним был пробел
                        infoLabel.setText(formattedText.toString());
                    });

                    Button button = new Button("В корзину");
                    button.setStyle("-fx-background-color: #49423D; -fx-text-fill: #FFFFFF;");

                    // Обработчик события для кнопки
                    button.setOnAction(event -> {
                        // Передаём авторизованного пользователя
                        User loginedUser = homeUserController.getLoginedUserToCatalogAndToBasket();
                        User registeredUser = homeUserController.getRegisteredUserToCatalogAndToBasket();

                        // Определяем пользователя для установки в качестве владельца книги
                        String userLogin = (loginedUser != null) ? loginedUser.getLogin() : (registeredUser != null) ? registeredUser.getLogin() : null;

                        // Если пользователь авторизован и книга еще не имеет владельца
                        if (userLogin != null) {
                            try {
                                // Обновляем bookOwnerLogin в БД
                                String updateQuery = "UPDATE " + Constants.BOOKS_TABLE + " SET " + Constants.BOOK_OWNER_LOGIN + "=? WHERE " + Constants.BOOK_TITLE + "=?";
                                PreparedStatement updateStatement = dataBaseHandler.getConnection().prepareStatement(updateQuery);
                                updateStatement.setString(1, userLogin);
                                updateStatement.setString(2, bookTitle);
                                updateStatement.executeUpdate();
                                System.out.println("Книга '" + bookTitle + "' добавлена в корзину для пользователя " + userLogin);
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        openNewScene("/sample/catalogUser.fxml");
                    });

                    // Создаем HBox и добавляем в нее картинку и кнопку
                    HBox hbox = new HBox(imageView, button);
                    hbox.setAlignment(Pos.CENTER);
                    // Затем добавим этот HBox в VBox
                    catalogVBox.getChildren().add(hbox);
                    imageIndex++;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void initialize() {
        loadImagesToImageViews();

        toUserHomeButton.setOnAction(event -> {
            openNewScene("/sample/homeUser.fxml");
        });

        // Передаём авторизованного пользователя. При нажатии "добаить в корзину" к полю книги ownerLogin, у книги которой это поле пустое (равно ""), будет добавляться этот пользователь
        User loginedUser = homeUserController.getLoginedUserToCatalogAndToBasket();
        User registeredUser = homeUserController.getRegisteredUserToCatalogAndToBasket();
        if (registeredUser != null) {
            welcomeLabel.setText(registeredUser.getName());
        } else if (loginedUser != null) {
            welcomeLabel.setText(loginedUser.getName());
        } else {
            welcomeLabel.setText("Пользователь не обнаружен");
        }

        searchButton.setOnAction(event -> {
            String searchTitleText = searchField.getText().trim(); // Получаем название
            if(!searchTitleText.equals("")){
                findBook(searchTitleText);
            }
        });

    }

    private void findBook(String findTitle){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        Book book = new Book();
        book.setTitle(findTitle);
        ResultSet resultSet = dataBaseHandler.getBookFromDB(book);
        boolean flag = false;
        try{
            if(resultSet.next()){
                flag=true;
                String title = resultSet.getString("bookTitle");
                System.out.println(title);
                book.setTitle(title);

                String imagePath = resultSet.getString("bookLocation");
                String descriptionText = resultSet.getString("bookDescription");

                Image image = new Image("file:" + imagePath);
                System.out.println("Путь к книге:"+imagePath);
                searchBookImage.setImage(image);

                // Разбиваем описание (поле descriptionText) на строки по 25 символов, иначе не влезет
                StringBuilder formattedText = new StringBuilder();
                int charCount = 0;
                StringBuilder word = new StringBuilder();

                for (char c : descriptionText.toCharArray()) {
                    if (Character.isWhitespace(c)) {
                        formattedText.append(word).append(c);
                        charCount += word.length() + 1;
                        word.setLength(0);
                    } else {
                        word.append(c);
                    }

                    if (charCount >= 25) {
                        formattedText.append("\n");
                        charCount = 0;
                    }
                }

                formattedText.append(word); // Добавляем оставшееся слово или пробел, если последним был пробел
                infoLabel.setText(formattedText.toString());

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(flag){
            System.out.println("Книга нашлась");
        }
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
