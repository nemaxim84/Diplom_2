package client;

import dto.LoginDto;

import io.restassured.response.Response;
import testdata.RequestSpecification;

public class LoginClient extends RestAssuredClient {
    public Response login(LoginDto loginDto) {
        return post(RequestSpecification.login, loginDto);
    }
}
