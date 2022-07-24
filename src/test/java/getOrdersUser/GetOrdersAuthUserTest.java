package getOrdersUser;

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

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class GetOrdersAuthUserTest {
    private String name = "Maxhhhhhhhnnnnnhhhhh855333";
    private String password = "qwert77777777777y333";
    private String email = "Maxuuuuuuuhhhhhhuu9999@ya.ru";
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    Response response;
    private static OrderClient orderClient;
    private static OrderDto orderDto;
    Response response2;

    @Before
    public void setUp() {
        userClient = new UserClient(new RestAssuredClient());
        userDto = new UserDto();
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setPassword(password);
        orderClient = new OrderClient(new RestAssuredClient());
        orderDto = new OrderDto();
    }
    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }
    @Test
    @DisplayName("Получение списка заказов у авторизированного пользователя")
    public void GetOrderAuthUser() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        response2 = orderClient.get(token.substring("Bearer ".length()));
        boolean isCreate = response2
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }
}
