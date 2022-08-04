package getordersuser;

import client.OrderClient;
import client.UserClient;
import dto.OrderDto;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetOrdersTest {
    private UserClient userClient;
    private String token;
    private UserDto userDto;
    private Response response;
    private OrderClient orderClient;
    private OrderDto orderDto;


    @Before
    public void setUp() {
        userClient = new UserClient();
        userDto=UserDto.createUserRandom();
        orderClient = new OrderClient();
        orderDto = new OrderDto();
        response = userClient.create(userDto);
        token = response.path("accessToken");
    }
    @After
    public void tearDown() throws Exception {
        userClient.delete(token.substring("Bearer ".length()));
    }
    @Test
    @DisplayName("Получение списка заказов у авторизированного пользователя")
    public void getOrderAuthUser() {
        boolean isCreate = orderClient.get(token.substring("Bearer ".length()))
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }
    @Test
    @DisplayName("Получение списка заказов у неавторизированного пользователя")
    public void getOrderNotAuthUser() {
        boolean isCreate = orderClient.get("")
                .then().statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(isCreate);
    }
}
