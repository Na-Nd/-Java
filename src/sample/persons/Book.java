package sample.persons;

public class Book {

    private String title;
    private String author;
    private String description; // Описание
    private int count; // Осталось в магазине
    private String ownerLogin; // Логин владельца (того, кто в корзину к себе добавил)
    private String location; // Путь к png файлу

    public Book(String title, String author, String description, int count, String ownerLogin, String location){
        this.title=title;
        this.author=author;
        this.description = description;
        this.count = count;
        this.ownerLogin=ownerLogin;
        this.location = location;
    }

    // Конструктор для добавления книги
    public Book(String title, String author, String description, int count, String location){
        this.title = title;
        this.author = author;
        this.description = description;
        this.count = count;
        this.ownerLogin = "";
        this.location = location;
    }

    public Book(){

    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }
    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}
