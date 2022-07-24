package createOrder;

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

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class СreateOrderWithAuthTest {
    private String name = "Maxhhhhhhhnnnnnhhhhh855333";
    private String password = "qwert77777777777y333";
    private String email = "Maxuuuuuuuhhhhhhuu9999@ya.ru";
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    Response response;
    private String ingredient1 = "61c0c5a71d1f82001bdaaa6d";
    private String ingredient2 = "61c0c5a71d1f82001bdaaa6f";
    private String ingredient3 = "61c0c5a71d1f82001bdaaa72";
    private List<String> ingredients;
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
        ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        orderDto.setIngredients(ingredients);
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Создание заказа c авторизацией и с ингредиентами")
    public void createOrderWithoutAuth() {
        response = userClient.create(userDto);
        token = response.path("accessToken");
        response2 = orderClient.create(token.substring("Bearer ".length()), orderDto);
        boolean isCreate = response2
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }
}
