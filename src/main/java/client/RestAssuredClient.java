package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RestAssuredClient {
    private RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .setBaseUri(testdata.RequestSpecification.baseUrl)
            .setBasePath(testdata.RequestSpecification.basePath)
            .build();

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public <T> Response post(String path, T body, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .body(body)
                .when()
                .post(path, pathParams);
    }
    public <T> Response post(String token, String path, T body, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                .body(body)
                .when()
                .post(path, pathParams);
    }

    public Response delete(String token, String path, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                .when()
                .delete(path, pathParams);
    }

    public Response get(String token, String path, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                .when()
                .get(path, pathParams);
    }
    public  <T> Response patch(String token, String path, T body, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                .body(body)
                .when()
                .patch(path, pathParams);
    }
}
