package createuser;

import client.RestAssuredClient;
import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testdata.UserData;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private Response response;

    @Before
    public void setUp() {
        userClient = new UserClient();
        UserData userData = new UserData();
        userDto = new UserDto(userData.getName(), userData.getEmail(), userData.getPassword());
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Создание пользователя со всеми полями и проверка, что он создался")
    public void createUserValid() {
        response = userClient.create(userDto);
        boolean isCreated = response
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreated);
        token = response.path("accessToken");
    }
}
