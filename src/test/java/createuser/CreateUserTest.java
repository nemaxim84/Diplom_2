package createuser;

import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {
    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private UserClient userClient;
    private String token;
    private String tokenNew;
    private UserDto userDto;
    private Response response;
    private Response responseNew;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userDto = UserDto.createUserRandom();
    }

    @After
    public void tearDown() throws Exception {
        try {
            userClient.delete(token.substring("Bearer ".length()));
            userClient.delete(tokenNew.substring("Bearer ".length()));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Нет токена или пользователь не создавался");
        }
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегестрирован, и проверка, что он не создался")
    public void createUserNotValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        responseNew = userClient.create(userDto);
        tokenNew = responseNew.path("accessToken");
        boolean isCreated = responseNew
                .then().statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isCreated);
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

    @Test
    @DisplayName("Создание пользователя со всеми пустыми полями и проверка, что он не создался")
    public void createUserEmptyField() {
        userDto = new UserDto(null, null, null);
        response = userClient.create(userDto);
        boolean isCreated = response
                .then().statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isCreated);
        tokenNew = response.path("accessToken");
    }
}
