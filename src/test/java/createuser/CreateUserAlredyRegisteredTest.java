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

public class CreateUserAlredyRegisteredTest {
    private static UserClient userClient;
    private String token;
    private String tokenNew;
    private static UserDto userDto;
    private Response response;
    private Response responseNew;

    @Before
    public void setUp() {
        userClient = new UserClient();
        UserData userData = new UserData();
        userDto = new UserDto(userData.getName(), userData.getEmail(), userData.getPassword());
    }

    @After
    public void tearDown() throws Exception {
        userClient.delete(token.substring("Bearer ".length()));
        try {
            userClient.delete(tokenNew.substring("Bearer ".length()));
        } catch (Exception e){
            System.out.println("Пользователь не создался");
        }
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегестрирован, и проверка, что он не создался")
    public void createCourierNotValid() {
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
}
