package restApitests;

import BasePackage.BaseClass;
import io.restassured.RestAssured;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class ApiTests extends BaseClass {

    @BeforeSuite
    public void init() {
        loadProperties();
        setupSpec();
        signUp();
        login();
        requestSpec = RestAssured.requestSpecification
                .header("x-panem-token", loginToken);
    }

    //This functions gets the users info and asserts the user details.
    @Test
    public void getUserInfo()
    {
        given()
            .when()
                .get(prop.getProperty("getInfoPath"))
            .then()
                .assertThat().statusCode(200)
                //.log().all()
                .assertThat().body("current_user.token",equalTo(loginToken))
                // Using containsString since the name is registered with space at the end
                .assertThat().body("current_user.name", containsString("test3"))
                .assertThat().body("current_user.contact_no", equalTo("9071100317"))
                .assertThat().body("current_user.email_id",equalTo("test3@test3.com"));
    }

    //This function calls teh logout api
    @AfterSuite
    public void afterTest()
    {
        logout();
    }

}
