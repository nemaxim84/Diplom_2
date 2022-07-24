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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangingNotAuthUserDataTest {
    private String name = "Max88gg777777vvvvv555555855333";
    private String nameNew = "Max88gg855333";
    private String password = "qwert77777777777y333";
    private String email = "Maxjkkkkkkkkkkkk9999@ya.ru";
    private String emailNew = "maxmmmmmmmmhhhhhhhhhhhhhhmmmmmmm@ya.ru";
    private String message = "You should be authorised";
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
    @DisplayName("Изменение имени не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingNameUserNotValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        userDto.setName(nameNew);
        response2 = userClient.patch("", userDto);
        assertFalse(response2.path("success"));
        Assert.assertTrue(response2.path("message").toString().contains(message));
    }
    @Test
    @DisplayName("Изменение email не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingEmailUserNotValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        userDto.setEmail(emailNew);
        response2 = userClient.patch("", userDto);
        assertFalse(response2.path("success"));
        Assert.assertTrue(response2.path("message").toString().contains(message));
    }
}
