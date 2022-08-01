package createorder;

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
import testdata.Ingredients;
import testdata.UserData;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class СreateOrderWithAuthTest {
    private static UserClient userClient;
    private String token;
    private static UserDto userDto;
    private Response response;
    private List<String> ingredients;
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
        ingredients = new ArrayList<>();
        ingredients.add(Ingredients.ingredient1);
        ingredients.add(Ingredients.ingredient2);
        ingredients.add(Ingredients.ingredient3);
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
        responseNew = orderClient.create(token.substring("Bearer ".length()), orderDto);
        boolean isCreate = responseNew
                .then().statusCode(SC_OK)
                .extract()
                .path("success");
        assertTrue(isCreate);
    }
}
