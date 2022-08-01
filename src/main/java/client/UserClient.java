package client;

import dto.UserDto;
import io.restassured.response.Response;
import testdata.RequestSpecification;

public class UserClient extends RestAssuredClient {

    public Response create(UserDto userDto) {
        return post(RequestSpecification.regUser, userDto);
    }

    public boolean delete(String token) {
        return delete(token, RequestSpecification.authUser)
                .then()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }

    public Response patch(String token, UserDto userDto) {
        return patch(token, RequestSpecification.authUser, userDto);
    }
}
