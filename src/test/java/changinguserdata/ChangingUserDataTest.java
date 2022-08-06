package changinguserdata;

import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangingUserDataTest {
    private String nameNew;
    private String emailNew;
    private UserClient userClient;
    private String message = "You should be authorised";
    private String token;
    private static UserDto userDto;
    private Response response;
    private Response responseNew;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userDto = UserDto.createUserRandom();
        response = userClient.create(userDto);
        token = response.path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Изменение имени пользователя с авторизацией и проверка, что данные изменились")
    public void changingNameUserValid() {
        nameNew = UserDto.createUserRandom().getName();
        userDto.setName(nameNew);
        responseNew = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = responseNew.path("success");
        assertTrue(isCreated);
        assertTrue(responseNew.path("user").toString().contains(nameNew));
    }

    @Test
    @DisplayName("Изменение email пользователя с авторизацией и проверка, что данные изменились")
    public void changingEmailUserValid() {
        emailNew = UserDto.createUserRandom().getEmail();
        userDto.setEmail(emailNew);
        responseNew = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = responseNew.path("success");
        assertTrue(isCreated);
        assertTrue(responseNew.path("user").toString().contains(emailNew));
    }

    @Test
    @DisplayName("Изменение имени не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingNameUserNotValid() {
        nameNew = UserDto.createUserRandom().getName();
        userDto.setName(nameNew);
        responseNew = userClient.patch("", userDto);
        assertFalse(responseNew.path("success"));
        assertTrue(responseNew.path("message").toString().contains(message));
    }

    @Test
    @DisplayName("Изменение email не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingEmailUserNotValid() {
        emailNew = UserDto.createUserRandom().getEmail();
        userDto.setEmail(emailNew);
        responseNew = userClient.patch("", userDto);
        assertFalse(responseNew.path("success"));
        assertTrue(responseNew.path("message").toString().contains(message));
    }
}
