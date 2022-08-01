package client;

import dto.LoginDto;
import dto.OrderDto;
import dto.UserDto;
import io.restassured.response.Response;
import testdata.RequestSpecification;

public class OrderClient extends RestAssuredClient {

    public Response create(String token, OrderDto orderDto) {
        return post(token,RequestSpecification.orders, orderDto);
    }
    public Response get(String token) {
        return get(token, RequestSpecification.orders);
    }
}
