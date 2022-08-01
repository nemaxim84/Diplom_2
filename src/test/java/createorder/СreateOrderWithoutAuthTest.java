package createorder;

import client.OrderClient;
import client.RestAssuredClient;
import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testdata.Ingredients;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class СreateOrderWithoutAuthTest {
    private List<String> ingredients;
    private static OrderClient orderClient;
    private static OrderDto orderDto;
    private Response response;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        orderDto = new OrderDto();
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.ingredient1);
        ingredients.add(Ingredients.ingredient2);
        ingredients.add(Ingredients.ingredient3);
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
