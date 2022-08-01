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

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertFalse;

public class CreateUserWithoutFieldTest {
    private static UserDto userDto;
    private Response response;
    private UserClient userClient;
    private String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    @After
    public void tearDown() throws Exception {
            try {
                userClient.delete(token.substring("Bearer ".length()));
            } catch (Exception e){
                System.out.println("Пользователь не создался");
            }
    }
    @Test
    @DisplayName("Создание пользователя со всеми пустыми полями и проверка, что он не создался")
    public void createUserNotValid() {
        userDto = new UserDto();
        response = userClient.create(userDto);
        boolean isCreated = response
                .then().statusCode(SC_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(isCreated);
        token = response.path("accessToken");
    }
}
