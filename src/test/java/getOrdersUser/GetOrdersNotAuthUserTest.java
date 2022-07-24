package getOrdersUser;

import client.OrderClient;
import client.RestAssuredClient;
import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertFalse;

public class GetOrdersNotAuthUserTest {
    private static OrderClient orderClient;
    private static OrderDto orderDto;
    Response response2;

    @Before
    public void setUp() {
        orderClient = new OrderClient(new RestAssuredClient());
        orderDto = new OrderDto();
    }

    @Test
    @DisplayName("Получение списка заказов у неавторизированного пользователя")
    public void GetOrderNotAuthUser() {
        response2 = orderClient.get("");
        boolean isCreate = response2
                .then().statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(isCreate);
    }
}
