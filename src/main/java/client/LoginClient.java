package client;

import dto.LoginDto;

import io.restassured.response.Response;
import testdata.Endpoints;

public class LoginClient extends RestAssuredClient {
    public Response login(LoginDto loginDto) {
        return post(Endpoints.LOGIN, loginDto);
    }
}
