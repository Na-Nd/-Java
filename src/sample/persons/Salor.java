package sample.persons;
public class Salor {

    private String name;
    private String surname;
    private String login;
    private String password;



    public Salor(String name, String surname, String login, String password){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Salor() {

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
}
