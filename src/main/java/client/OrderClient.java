package client;

import dto.LoginDto;
import dto.OrderDto;
import dto.UserDto;
import io.restassured.response.Response;

public class OrderClient {
    private final RestAssuredClient restAssuredClient;

    public OrderClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }
    public Response create(String token, OrderDto orderDto) {
        return restAssuredClient.post(token,"orders", orderDto);
    }
    public Response get(String token) {
        return restAssuredClient.get(token, "orders");
    }
}
