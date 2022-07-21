package createUser;

import client.RestAssuredClient;
import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {
    private String name = "Max88888111ddddddsssssss11185555555555555855333";
    private String password = "qwert77777777777y333";
    private String email = "Max99991111kkkkkkkkk9999999@ya.ru";
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
