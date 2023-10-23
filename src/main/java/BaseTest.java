import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import steps.Steps;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;

public class BaseTest implements Steps{

    @BeforeMethod
    public void configureRestAssured()
    {
        RestAssured.baseURI = "https://reqres.in/";
    }
}
