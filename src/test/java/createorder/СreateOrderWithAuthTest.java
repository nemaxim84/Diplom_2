package createorder;

import client.OrderClient;
import client.UserClient;
import dto.OrderDto;
import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testdata.Ingredients;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class СreateOrderWithAuthTest {
    private UserClient userClient;
    private String token;
    private UserDto userDto;
    private Response response;
    private List<String> ingredients;
    private OrderClient orderClient;
    private OrderDto orderDto;
    private Response responseNew;
    private OrderDto orderDtoEmpty;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userDto = UserDto.createUserRandom();
        orderClient = new OrderClient();
        orderDto = new OrderDto();
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.INGREDIENT_1);
        ingredients.add(Ingredients.INGREDIENT_2);
        ingredients.add(Ingredients.INGREDIENT_3);
        orderDto.setIngredients(ingredients);
        response = userClient.create(userDto);
        token = response.path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.delete(token.substring("Bearer ".length()));
    }

    @Test
    @DisplayName("Создание заказа c авторизацией и с ингредиентами")
    public void createOrderWithAuth() {
        responseNew = orderClient.create(token.substring("Bearer ".length()), orderDto);
        boolean isCreate = responseNew
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        response = orderClient.create("", orderDto);
        boolean isCreate = response
                .then()
                .extract()
                .path("success");
        assertFalse(isCreate);
    }

    @Test
    @DisplayName("Создание заказа c авторизацией и без ингредиентов")
    public void createOrderWithoutIngredients() {
        orderDtoEmpty = new OrderDto();
        responseNew = orderClient.create(token.substring("Bearer ".length()), orderDtoEmpty);
        boolean isCreate = responseNew
                .then().statusCode(SC_BAD_REQUEST)
                .extract()
                .path("success");
        assertFalse(isCreate);
    }

    @Test
    @DisplayName("Создание заказа c невалидным id ингредиентов")
    public void createOrderNotValidId() {
        ingredients.add(Ingredients.INGREDIENT_NOT_VALID);
        responseNew = orderClient.create(token.substring("Bearer ".length()), orderDto);
        assertEquals(SC_INTERNAL_SERVER_ERROR, responseNew.statusCode());
    }
}
