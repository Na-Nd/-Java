package sample.persons;

import java.util.ArrayList;
public class User {

    private String name;
    private String surname;
    private String login;
    private String password;
    ArrayList<Book> basket = new ArrayList<>();


    public User(String name, String surname, String login, String password){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public User() {

    }


    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSurname() {return surname;}
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {return login;}
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Метод для добавления книги в корзину
    public void addBookToBasket(Book book) {
        basket.add(book);
    }

    // Метод для получения книги из корзины по названию
    public Book getBookFromBasket(String title) {
        for (Book book : basket) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        // Возвращаем null, если книга с таким названием не найдена
        System.out.println("Книга '" + title + "' не найдена.");
        return null;
    }

    // Метод для удаления книги из корзины по названию
    public boolean removeBookFromBasket(String title) {
        for (int i = 0; i < basket.size(); i++) {
            if (basket.get(i).getTitle().equals(title)) {
                basket.remove(i);
                return true; // Возвращаем true, если книга найдена и удалена
            }
        }
        // Возвращаем false, если книга с таким названием не найдена
        System.out.println("Книга '" + title + "' не найдена.");
        return false;
    }
}
