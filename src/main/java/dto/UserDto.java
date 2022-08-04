package dto;

import com.github.javafaker.Faker;

public class UserDto {
    private static String name;
    private static String email;
    private static String password;


    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto() {
    }
    public static UserDto createUserRandom(){
        Faker faker= new Faker();
        return new UserDto(faker.name().name(),faker.internet().emailAddress(),faker.internet().password(7,20));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
