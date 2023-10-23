import model.RequestModel.JobRequest;
import model.RequestModel.RegisterRequest;
import model.ResponseModel.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class ApiTests extends BaseTest {

    @Test(description = "Проверка, что все имена пользователей на латинице")
    public void CompareNames(){
        List<DataResponse> users = API_STEPS.getUserList();
        API_STEPS.englishNames(users);
    }

    @Test(description = "Получение списка пользователей")
    public void UsersList(){
        API_STEPS.getUserList();
    }


    @Test(description = "Получение пользователя")
    public void GetUser(){
        UserResponse actualResponse = API_STEPS.getUserById(2, 200);
        DataResponse dataResponse = new DataResponse(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");
        SupportResponse supportResponse = new SupportResponse("https://reqres.in/#support-heading", "To keep ReqRes free, contributions towards server costs are appreciated!");
        UserResponse expectedResponse = new UserResponse(dataResponse, supportResponse);
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    @Test(description = "Сравнение массивов из цветов")
    public void CompareListColors(){
        List<ColorDataResponse> colors = API_STEPS.getColorsList();
        API_STEPS.colorsList(colors);

    }

    @Test(description = "Получение цвета по ID")
    public void ColorName(){
        API_STEPS.getColorById(2, 200);
    }

    @Test(description = "Несуществующий пользователь")
    public void UserNotFound(){
        API_STEPS.getUserById(23, 404);
    }

    @Test(description = "Несуществующий цвет")
    public void ColorNotFound(){
        API_STEPS.getColorById(23, 404);
    }

    @Test(description = "Добавить пользователя и получить этого же пользователя ")
    public void AddUser(){
        JobRequest user = new JobRequest("tishka", "programmer");
        API_STEPS.postUser(user);
    }

    @Test(description = "Изменение пользователя")
    public void PutUser(){
        JobRequest user = new JobRequest("ulyana", "designer");
        API_STEPS.putUser(user, 2);
    }

    @Test(description = "Изменение пользователя")
    public void PatchUser(){
        JobRequest user = new JobRequest("ulyana", "actor dubler");
        API_STEPS.patchUser(user, 2);
    }

    @Test(description = "Удаление пользователя")
    public void DeleteUser(){
        API_STEPS.deleteUser(2);
    }

    @Test(description = "Регистрация прошла")
    public void Register(){
        RegisterRequest registerRequest = new RegisterRequest ("eve.holt@reqres.in", "pistol");
        API_STEPS.successAuth(registerRequest);
    }

}
