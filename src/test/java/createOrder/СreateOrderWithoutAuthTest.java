package createOrder;

import client.OrderClient;
import client.RestAssuredClient;
import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class СreateOrderWithoutAuthTest {
    private String ingredient1 = "61c0c5a71d1f82001bdaaa6d";
    private String ingredient2 = "61c0c5a71d1f82001bdaaa6f";
    private String ingredient3 = "61c0c5a71d1f82001bdaaa72";
    private List<String> ingredients;
    private static OrderClient orderClient;
    private static OrderDto orderDto;
    Response response;

    @Before
    public void setUp() {
        orderClient = new OrderClient(new RestAssuredClient());
        orderDto = new OrderDto();
        ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        orderDto.setIngredients(ingredients);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        response = orderClient.create("",orderDto);
        boolean isCreate = response
                .then()
                .extract()
                .path("success");
        assertFalse(isCreate);
    }
}
