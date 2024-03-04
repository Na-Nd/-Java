package sample;

import sample.persons.Admin;
import sample.persons.Book;
import sample.persons.Salor;
import sample.persons.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseHandler extends Configs{
    Connection connection;

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String urlConnection = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(urlConnection, dbUser, dbPass);
        return connection;
    }

    // Регистрация:
    public void registerUserInDB(User user){ // Регистрация пользователя в БД. Написать такую же для админа и продавца
        String insertQuery = "INSERT INTO " + Constants.USERS_TABLE + "(" + Constants.USER_NAME + "," + Constants.USER_SURNAME +
                "," + Constants.USER_LOGIN + "," + Constants.USER_PASSWORD + ")" + "VALUES(?,?,?,?)";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getSurname());
            preparedStatement.setString(3,user.getLogin());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            exception.printStackTrace();
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    // Регистрация админа в БД
    public void registerAdminInDB(Admin admin){
        String insertQuery = "INSERT INTO " + Constants.ADMINS_TABLE + "(" + Constants.ADMIN_NAME + "," + Constants.ADMIN_SURNAME +
                "," + Constants.ADMIN_LOGIN + "," + Constants.ADMIN_PASSWORD + ")" + "VALUES(?,?,?,?)";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery);
            preparedStatement.setString(1,admin.getName());
            preparedStatement.setString(2,admin.getSurname());
            preparedStatement.setString(3,admin.getLogin());
            preparedStatement.setString(4,admin.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            exception.printStackTrace();
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    // Регистрация продавца в БД
    public void registerSalorInDB(Salor salor){
        String insertQuery = "INSERT INTO " + Constants.SALORS_TABLE + "(" + Constants.SALOR_NAME + "," + Constants.SALOR_SURNAME +
                "," + Constants.SALOR_LOGIN + "," + Constants.SALOR_PASSWORD + ")" + "VALUES(?,?,?,?)";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery);
            preparedStatement.setString(1,salor.getName());
            preparedStatement.setString(2,salor.getSurname());
            preparedStatement.setString(3,salor.getLogin());
            preparedStatement.setString(4,salor.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            exception.printStackTrace();
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public void registerBookInDB(Book book){
        String insertQuery = "INSERT INTO " + Constants.BOOKS_TABLE + "(" + Constants.BOOK_TITLE + "," + Constants.BOOK_AUTHOR + "," +
                Constants.BOOK_DESCRIPTION +  "," + Constants.BOOK_COUNT + "," + Constants.BOOK_OWNER_LOGIN + "," + Constants.BOOK_LOCATION + ")" +
                "VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getCount());
            preparedStatement.setString(5, book.getOwnerLogin());
            preparedStatement.setString(6, book.getLocation());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }



    // Получение из БД:
    public ResultSet getUserFromDB(User user){ // Получить зарегистрированного пользователя
        ResultSet resultSet = null;

        // Выбрать пользователя, у которого есть логин и пароль
        String selectQuery = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USER_LOGIN + "=? AND " +
                Constants.USER_PASSWORD + "=?";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            exception.printStackTrace();
        }catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAdminFromDB(Admin admin){ // Получить зарегистрированного админа
        ResultSet resultSet = null;

        // Выбрать админа, у которого есть логин и пароль
        String selectQuery = "SELECT * FROM " + Constants.ADMINS_TABLE + " WHERE " + Constants.ADMIN_LOGIN + "=? AND " +
                Constants.ADMIN_PASSWORD + "=?";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, admin.getLogin());
            preparedStatement.setString(2, admin.getPassword());

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            exception.printStackTrace();
        }catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getSalorFromDB(Salor salor){ // Получить зарегистрированного продавца
        ResultSet resultSet = null;

        // Выбрать продавца, у которого есть логин и пароль
        String selectQuery = "SELECT * FROM " + Constants.SALORS_TABLE + " WHERE " + Constants.SALOR_LOGIN + "=? AND " +
                Constants.SALOR_PASSWORD + "=?";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, salor.getLogin());
            preparedStatement.setString(2, salor.getPassword());

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            exception.printStackTrace();
        }catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getBookFromDB(Book book){ // Получить книгу из БД, у которой есть название
        ResultSet resultSet = null;

        String selectQuery = "SELECT * FROM " + Constants.BOOKS_TABLE + " WHERE " + Constants.BOOK_TITLE + "=?";

        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, book.getTitle());

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return resultSet;
    }


    // Удаление книги без владельца
    public void deleteBookFromDB(Book book) {
        String deleteQuery = "DELETE FROM " + Constants.BOOKS_TABLE + " WHERE " +
                Constants.BOOK_TITLE + "=? AND " + Constants.BOOK_OWNER_LOGIN + "=''";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(deleteQuery);
            preparedStatement.setString(1, book.getTitle());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void deleteBookByOwnerFromDB(Book book) { // Удаление книги с владельцем
        String deleteQuery = "DELETE FROM " + Constants.BOOKS_TABLE + " WHERE " +
                Constants.BOOK_TITLE + "=? AND " + Constants.BOOK_OWNER_LOGIN + "=?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(deleteQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getOwnerLogin());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
