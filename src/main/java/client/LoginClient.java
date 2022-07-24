package client;

import dto.LoginDto;

import io.restassured.response.Response;

public class LoginClient {
    private final RestAssuredClient restAssuredClient;

    public LoginClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }
    public Response login(LoginDto loginDto) {
        return restAssuredClient.post("auth/login", loginDto);
    }
}
