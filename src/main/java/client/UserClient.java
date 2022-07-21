package client;

import dto.UserDto;
import io.restassured.response.Response;

public class UserClient {
    private final RestAssuredClient restAssuredClient;

    public UserClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }
    public String tokenValid (UserDto userDto){
        return restAssuredClient.post("auth/register", userDto).then()
                .extract()
                .path("accessToken");
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

}
