package getordersuser;

import client.OrderClient;
import client.RestAssuredClient;
import client.UserClient;
import dto.OrderDto;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testdata.UserData;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class GetOrdersAuthUserTest {
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private Response response;
    private static OrderClient orderClient;
    private static OrderDto orderDto;
    private Response responseNew;

    @Before
    public void setUp() {
        userClient = new UserClient();
        UserData userData = new UserData();
        userDto = new UserDto(userData.getName(), userData.getEmail(), userData.getPassword());
        orderClient = new OrderClient();
        orderDto = new OrderDto();
    }
    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }
    @Test
    @DisplayName("Получение списка заказов у авторизированного пользователя")
    public void getOrderAuthUser() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        responseNew = orderClient.get(token.substring("Bearer ".length()));
        boolean isCreate = responseNew
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }
}
