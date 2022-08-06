package loginuser;

import client.LoginClient;
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
    private String password ;
    private String email;
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private Response response;
    private static LoginClient loginClient;
    private static LoginDto loginDto;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userDto=UserDto.createUserRandom();
        response = userClient.create(userDto);
        token = response.path("accessToken");
        loginClient = new LoginClient();
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Залогиниться пользователем существующим и проверка, что залогинился")
    public void loginUserValid() {
        loginDto = new LoginDto(userDto.getEmail(), userDto.getPassword());
        boolean isLogin = loginClient.login(loginDto)
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isLogin);
    }
    @Test
    @DisplayName("Залогиниться пользователем не существующим логином и проверка, что не залогинился")
    public void loginUserNotValid() {
        loginDto = new LoginDto(UserDto.createUserRandom().getEmail(), userDto.getPassword());
        boolean isLogin = loginClient.login(loginDto)
                .then().statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(isLogin);
    }
}
