package createUser;

import client.RestAssuredClient;
import client.UserClient;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateUserWithoutFieldTest {
    public static UserDto userDto;
    Response response;
    @Test
    @DisplayName("Создание пользователя со всеми пустыми полями и проверка, что он не создался")
    public void createUserNotValid() {
        UserClient userClient = new UserClient(new RestAssuredClient());
        userDto = new UserDto();
        response = userClient.create(userDto);
        boolean isCreated = response
                .then().statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isCreated);
    }
}
