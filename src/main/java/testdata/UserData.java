package testdata;

public class UserData {
    private String name = "Max";
    private String email= "max@ya.ru";
    private String password = "123456";

    public String getName() {
        return name = (int)(Math.random()*((100-50)+1)+50) + name;
    }

    public String getEmail() {
        return email = (int)(Math.random()*((100-50)+1)+50) + email;
    }

    public String getPassword() {
        return password;
    }
}
