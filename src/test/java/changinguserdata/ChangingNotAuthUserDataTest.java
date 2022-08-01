package changinguserdata;

import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testdata.UserData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangingNotAuthUserDataTest {
    private String nameNew;
    private String emailNew;
    private String message = "You should be authorised";
    UserData userData;
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private Response response;
    private Response responseNew;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userData = new UserData();
        nameNew = userData.getName();
        emailNew = userData.getEmail();
        userDto = new UserDto(userData.getName(), userData.getEmail(), userData.getPassword());
        response = userClient.create(userDto);
        token = response.path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Изменение имени не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingNameUserNotValid() {
        userDto.setName(nameNew);
        responseNew = userClient.patch("", userDto);
        assertFalse(responseNew.path("success"));
        assertTrue(responseNew.path("message").toString().contains(message));
    }
    @Test
    @DisplayName("Изменение email не авторизированного пользователя и проверка, что система выдала ошибку")
    public void changingEmailUserNotValid() {
        userDto.setEmail(emailNew);
        responseNew = userClient.patch("", userDto);
        assertFalse(responseNew.path("success"));
        assertTrue(responseNew.path("message").toString().contains(message));
    }
}
