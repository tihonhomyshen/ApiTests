import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import model.RequestModel.JobRequest;
import model.RequestModel.LogRegRequest;
import model.ResponseModel.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


@Epic("Api Tests")

public class ApiTests extends BaseTest {


    @Feature("GET")
    @Test(description = "Проверка, что все имена на первой странице с пользователями на латинице", groups = {"GET"})
    public void CompareNames() {
        List<DataResponse> users = API_STEPS.getUserList();
        API_STEPS.englishNames(users);
    }

    @Feature("GET")
    @Test(description = "Получение списка пользователей", groups = {"GET"})
    public void UsersList() {
        API_STEPS.getUserList();
    }

    @Feature("GET")
    @Test(description = "Получение пользователя", groups = {"GET"})
    public void GetUser() {
        UserResponse actualResponse = API_STEPS.getUserById(2, 200);
        DataResponse dataResponse = new DataResponse(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");
        SupportResponse supportResponse = new SupportResponse("https://reqres.in/#support-heading", "To keep ReqRes free, contributions towards server costs are appreciated!");
        UserResponse expectedResponse = new UserResponse(dataResponse, supportResponse);
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    @Feature("GET")
    @Test(description = "Сравнение массивов из цветов")
    public void CompareListColors() {
        List<ColorDataResponse> colors = API_STEPS.getColorsList();
        API_STEPS.colorsList(colors);

    }

    @Feature("GET")
    @Test(description = "Получение цвета по ID")
    public void ColorName() {
        API_STEPS.getColorById(2, 200);
    }

    @Feature("GET")
    @Test(description = "Несуществующий пользователь")
    public void UserNotFound() {
        API_STEPS.getUserById(23, 404);
    }

    @Feature("GET")
    @Test(description = "Несуществующий цвет")
    public void ColorNotFound() {
        API_STEPS.getColorById(23, 404);
    }

    @Feature("POST")
    @Test(description = "Добавить пользователя ")
    public void AddUser() {
        JobRequest user = new JobRequest("tishka", "programmer");
        API_STEPS.postUser(user);
    }

    @Feature("PUT")
    @Test(description = "Изменение пользователя")
    public void PutUser() {
        JobRequest user = new JobRequest("ulyana", "designer");
        API_STEPS.putUser(user, 2);
    }

    @Feature("PATCH")
    @Test(description = "Изменение пользователя")
    public void PatchUser() {
        JobRequest user = new JobRequest("ulyana", "actor dubler");
        API_STEPS.patchUser(user, 2);
    }

    @Feature("DELETE")
    @Test(description = "Удаление пользователя")
    public void DeleteUser() {
        API_STEPS.deleteUser(2);
    }

    @Feature("POST")
    @Test(description = "Успешная регистрация")
    public void successReg(){
        LogRegRequest logRegRequest = new LogRegRequest("eve.holt@reqres.in", "pistol");
        API_STEPS.regUser(logRegRequest);
    }

    @Feature("POST")
    @Test(description = "Неудачная регистрация")
    public void unsuccessReg(){
        LogRegRequest logRegRequest = new LogRegRequest("sydney@fife", "");
        API_STEPS.regUserError(logRegRequest);
    }

    @Feature("POST")
    @Test(description = "Успешный вход")
    public void successLogin(){
        LogRegRequest logRegRequest = new LogRegRequest("eve.holt@reqres.in", "cityslicka");
        API_STEPS.authUser(logRegRequest);
    }

    @Feature("POST")
    @Test(description = "Неудачный вход ")
    public void unsuccessLogin(){
        LogRegRequest logRegRequest = new LogRegRequest("peter@klaven", "");
        API_STEPS.authUserError(logRegRequest);
    }

    @Feature("GET")
    @Test(description = "Запрос с задержкой")
    public void DelayGet(){
        API_STEPS.getUserListDelay();
    }

}
