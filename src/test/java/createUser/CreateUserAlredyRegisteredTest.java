package createUser;

import client.RestAssuredClient;
import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertFalse;

public class CreateUserAlredyRegisteredTest {
    private String name = "Max888881117777dttttttt55555555855333";
    private String password = "qweooooooo333";
    private String email = "Maxmmmmmmmm8888mmmm@ya.ru";
    private static UserClient userClient;
    private String token;
    public static UserDto userDto;
    Response response;

    @Before
    public void setUp() {
        userClient = new UserClient(new RestAssuredClient());
        userDto = new UserDto();
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setPassword(password);
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегестрирован, и проверка, что он не создался")
    public void createCourierNotValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        boolean isCreated = userClient.create(userDto)
                .then().statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isCreated);
    }

}
