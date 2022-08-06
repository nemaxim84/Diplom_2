package client;

import dto.OrderDto;
import io.restassured.response.Response;
import testdata.Endpoints;

public class OrderClient extends RestAssuredClient {

    public Response create(String token, OrderDto orderDto) {
        return post(token, Endpoints.ORDERS, orderDto);
    }
    public Response get(String token) {
        return get(token, Endpoints.ORDERS);
    }
}
