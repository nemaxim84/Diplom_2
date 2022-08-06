package client;

import dto.UserDto;
import io.restassured.response.Response;
import testdata.Endpoints;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class UserClient extends RestAssuredClient {

    public Response create(UserDto userDto) {
        return post(Endpoints.REG_USER, userDto);
    }

    public boolean delete(String token) {
        return delete(token, Endpoints.AUTH_USER)
                .then()
                .assertThat()
                .statusCode(SC_ACCEPTED)
                .extract()
                .path("success");
    }

    public Response patch(String token, UserDto userDto) {
        return patch(token, Endpoints.AUTH_USER, userDto);
    }
}
