package Model;

public class User {
  String userName;
  String passWord;
  int id;

    public User(String nameName, String PassWord, int id ) {
        this.userName = nameName;
        this.passWord = PassWord;
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
