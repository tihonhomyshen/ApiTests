package steps;
import io.qameta.allure.Step;
import model.RequestModel.JobRequest;
import model.RequestModel.LogRegRequest;
import model.ResponseModel.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    @Step("Получить список пользоваталей ")
    public List<DataResponse> getUserList(){
        return given()
                .when()
                .get("/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200).extract().response().body().jsonPath().getList("data", DataResponse.class);
    }
    @Step("Получить список цветов ")
    public List<ColorDataResponse> getColorsList(){
        return given()
                .when()
                .get("/api/unknown")
                .then()
                .assertThat()
                .statusCode(200).extract().response().body().jsonPath().getList("data", ColorDataResponse.class);
    }

    @Step("Получить пользователя по ID")
    public UserResponse getUserById(Integer Id, Integer statusCode){

        return given()
                .when()
                .get("/api/users/"+Id)
                .then()
                .assertThat()
                .statusCode(statusCode).extract().response().body().as(UserResponse.class);
    }

    @Step("Список пользователей, полученный с задержкой в 3 секунды")
    public List<DataResponse> getUserListDelay(){
        return given()
                .when()
                .get("/api/users?delay=3")
                .then()
                .assertThat()
                .statusCode(200).extract().response().body().jsonPath().getList("data", DataResponse.class);
    }

    @Step("Получить цвет по ID")
    public ColorResponse getColorById(Integer Id, Integer statusCode){
        return given()
                .when()
                .get("/api/unknown/"+Id)
                .then()
                .assertThat()
                .statusCode(statusCode).extract().response().body().as(ColorResponse.class);
    }

    @Step("Список цветов соотвествует")
    public void colorsList(List<ColorDataResponse> colors){
        ArrayList<String> newColorsList = new ArrayList<>(Arrays.asList("#98B2D1", "#C74375", "#BF1932", "#7BC4C4", "#E2583E", "#53B0AE"));
        List<String> getColorsList  = colors.stream().map(ColorDataResponse:: getColor).
                collect(Collectors.toList());
        Assert.assertEquals(newColorsList, getColorsList);
    }

    @Step("Проверка, что все имена пользователей на латинице")
    public void englishNames(List<DataResponse> users){
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        List<String> names = users.stream().map(DataResponse:: getFirstName).
                collect(Collectors.toList());
        Assert.assertTrue(names.stream().allMatch(name -> pattern.matcher(name).matches()));
    }

    @Step("Добавить пользователя")
    public JobResponse postUser(JobRequest jobRequest){
        return given()
                .when()
                .body(jobRequest)
                .post("/api/users")
                .then()
                .assertThat()
                .statusCode(201).extract().response().body().as(JobResponse.class);
    }

    @Step("Изменить пользователя")
    public JobUpdateResponse putUser(JobRequest jobRequest, Integer Id){
        return given()
                .when()
                .body(jobRequest)
                .put("/api/users/" +Id)
                .then()
                .assertThat()
                .statusCode(200).extract().response().body().as(JobUpdateResponse.class);
    }

    @Step("Частично изменить пользователя")
    public JobUpdateResponse patchUser(JobRequest jobRequest, Integer Id){
        return given()
                .when()
                .body(jobRequest)
                .patch("/api/users/" +Id)
                .then()
                .assertThat()
                .statusCode(200).extract().response().body().as(JobUpdateResponse.class);
    }

    @Step("Удалить пользователя")
    public void deleteUser(Integer Id){
            given()
                .when()
                .delete("/api/users/" +Id)
                .then()
                .statusCode(204).extract().response();
    }


    @Step("Успешная регистрация и сравнение полученного токена")
    public void regUser(LogRegRequest logRegRequest){
        RegisterResponse actualResponse = given().when().
                body(logRegRequest)
                .contentType("application/json")
                .post("api/register")
                .then()
                .statusCode(200).extract().response().body().as(RegisterResponse.class);
        RegisterResponse expectedResponse = new RegisterResponse(4,  "QpwL5tke4Pnpja7X4" );
        Assert.assertEquals(actualResponse.token, expectedResponse.token);
    }

    @Step("Неверная регистрация и сравнение полученной ошибки")
    public void regUserError(LogRegRequest logRegRequest){
        ErrorResponse actualResponse = given().when().
                body(logRegRequest)
                .contentType("application/json")
                .post("api/register")
                .then()
                .statusCode(400).extract().response().body().as(ErrorResponse.class);
        Assert.assertEquals(actualResponse.error, "Missing password");
    }

    @Step("Успешная логин и сравнение полученного токена")
    public void authUser(LogRegRequest logRegRequest){
        LoginResponse actualResponse = given().when().
                body(logRegRequest)
                .contentType("application/json")
                .post("api/login")
                .then()
                .statusCode(200).extract().response().body().as(LoginResponse .class);
        LoginResponse expectedResponse = new LoginResponse("QpwL5tke4Pnpja7X4" );
        Assert.assertEquals(actualResponse.token, expectedResponse.token);
    }

    @Step("Успешная логин и сравнение полученного токена")
    public void authUserError(LogRegRequest logRegRequest){
        ErrorResponse actualResponse = given().when().
                body(logRegRequest)
                .contentType("application/json")
                .post("api/login")
                .then()
                .statusCode(400).extract().response().body().as(ErrorResponse .class);
        ErrorResponse expectedResponse = new ErrorResponse("Missing password" );
        Assert.assertEquals(actualResponse.error, expectedResponse.error);
    }

}
