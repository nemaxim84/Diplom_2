package client;

import dto.UserDto;
import io.restassured.response.Response;

public class UserClient {
    private final RestAssuredClient restAssuredClient;

    public UserClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }

    public Response create(UserDto userDto) {
        return restAssuredClient.post("auth/register", userDto);
    }
    public boolean delete(String token) {
        return restAssuredClient.delete(token, "auth/user")
                .then()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }
    public Response patch(String token, UserDto userDto) {
        return restAssuredClient.patch(token, "auth/user", userDto);
    }
}
