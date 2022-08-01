package changinguserdata;

import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testdata.UserData;

import static org.junit.Assert.assertTrue;

public class ChangingAuthUserDataTest {
    private String nameNew;
    private String emailNew;
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private UserData userData;
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
    @DisplayName("Изменение имени пользователя с авторизацией и проверка, что данные изменились")
    public void changingNameUserValid() {
        userDto.setName(nameNew);
        responseNew = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = responseNew.path("success");
        assertTrue(isCreated);
        Assert.assertTrue(responseNew.path("user").toString().contains(nameNew));
    }

    @Test
    @DisplayName("Изменение email пользователя с авторизацией и проверка, что данные изменились")
    public void changingEmailUserValid() {
        userDto.setEmail(emailNew);
        responseNew = userClient.patch(token.substring("Bearer ".length()), userDto);
        boolean isCreated = responseNew.path("success");
        assertTrue(isCreated);
        assertTrue(responseNew.path("user").toString().contains(emailNew));
    }
}
