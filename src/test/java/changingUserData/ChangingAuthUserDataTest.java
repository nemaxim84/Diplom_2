package changingUserData;

import client.RestAssuredClient;
import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChangingAuthUserDataTest {
    private String name = "Max88gg777777vvvvv555555855333";
    private String nameNew = "Max88gg855333";
    private String password = "qwert77777777777y333";
    private String email = "Maxjkkkkkkkkkkkk9999@ya.ru";
    private String emailNew = "maxmmmmmmmmhhhhhhhhhhhhhhmmmmmmm@ya.ru";
    private static UserClient userClient;
    private String token;
    public static UserDto userDto;
    Response response;
    Response response2;

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
    @DisplayName("Изменение имени пользователя с авторизацией и проверка, что данные изменились")
    public void changingNameUserValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        userDto.setName(nameNew);
        response2 = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = response2.path("success");
        assertTrue(isCreated);
        Assert.assertTrue(response2.path("user").toString().contains(nameNew));
    }
    @Test
    @DisplayName("Изменение email пользователя с авторизацией и проверка, что данные изменились")
    public void changingEmailUserValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        userDto.setEmail(emailNew);
        response2 = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = response2.path("success");
        assertTrue(isCreated);
        Assert.assertTrue(response2.path("user").toString().contains(emailNew));
    }
}
