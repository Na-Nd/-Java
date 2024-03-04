package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Constants;
import sample.DataBaseHandler;

public class salorGraphsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button toSalorHomeButton;

    @FXML
    private BarChart<?, ?> topSaleGraph;

    @FXML
    private Label welcomeLabel;

    @FXML
    void initialize() {
        populateBarChart();

        toSalorHomeButton.setOnAction(event -> {
            openNewScene("/sample/homeSalor.fxml");
        });
    }

    private void populateBarChart() {
        try {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            String selectQuery = "SELECT " + Constants.BOOK_TITLE + ", " + Constants.BOOK_OWNER_LOGIN + " " +
                    "FROM " + Constants.BOOKS_TABLE + " " +
                    "WHERE " + Constants.BOOK_OWNER_LOGIN + " IS NOT NULL AND " + Constants.BOOK_OWNER_LOGIN + " <> ''";

            Statement statement = dataBaseHandler.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Используем Map для подсчета количества владельцев каждой книги
            Map<String, Integer> bookOwnersCount = new HashMap<>();

            while (resultSet.next()) {
                String bookTitle = resultSet.getString(Constants.BOOK_TITLE);
                int ownersCount = bookOwnersCount.getOrDefault(bookTitle, 0);
                bookOwnersCount.put(bookTitle, ownersCount + 1);
            }

            // Сортируем книги по количеству владельцев
            bookOwnersCount.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .limit(10) // Ограничиваем по 10 книг
                    .forEach(entry -> {
                        String bookTitle = entry.getKey();
                        int ownersCount = entry.getValue();
                        CategoryAxis xAxis = new CategoryAxis();
                        NumberAxis yAxis = new NumberAxis();
                        topSaleGraph.setTitle("Топ 10 книг по количеству владельцев");
                        xAxis.setLabel("Название книги");
                        yAxis.setLabel("Количество владельцев");

                        XYChart.Series series = new XYChart.Series();
                        series.getData().add(new XYChart.Data(bookTitle, ownersCount));
                        topSaleGraph.getData().addAll(series);
                    });

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Метод для получения случайного цвета
    private String getRandomColor() {
        String[] colors = {"#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf"};
        return colors[(int) (Math.random() * colors.length)];
    }
    */

    public void openNewScene(String window) {
        Stage currentStage = (Stage) toSalorHomeButton.getScene().getWindow();
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
