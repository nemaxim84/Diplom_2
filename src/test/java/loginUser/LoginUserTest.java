package loginUser;

import client.LoginClient;
import client.RestAssuredClient;
import client.UserClient;
import dto.LoginDto;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginUserTest {
    private String name = "Max88888118888185555555555555855333";
    private String password = "qwert77777777777y333";
    private String email = "Max99991999999hhh999999@ya.ru";
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    Response response;
    private static LoginClient loginClient;
    private static LoginDto loginDto;

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
    @DisplayName("Залогиниться пользователем существующим и проверка, что залогинился")
    public void loginUserValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        loginClient = new LoginClient(new RestAssuredClient());
        loginDto = new LoginDto(email, password);
        boolean isLogin = loginClient.login(loginDto)
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isLogin);
    }
    @Test
    @DisplayName("Залогиниться пользователем не существующим логином и проверка, что не залогинился")
    public void loginUserNotValid() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        System.out.println(token);
        loginClient = new LoginClient(new RestAssuredClient());
        loginDto = new LoginDto(email+1, password);
        boolean isLogin = loginClient.login(loginDto)
                .then().statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(isLogin);
    }
}
