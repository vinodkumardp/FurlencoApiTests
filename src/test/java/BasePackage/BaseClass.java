package BasePackage;

import com.sun.codemodel.JTryBlock;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class BaseClass {

    protected RequestSpecification requestSpec;
    protected Response response;
    protected Properties prop;

    protected static String BASE_URI;

    public String loginToken;

    //Loads the property files
    public void loadProperties()
    {
        InputStream input;
        prop = new Properties();
        try {
            input = BaseClass.class.getClassLoader().getResourceAsStream("config.properties");

            // load a properties file
            prop.load(input);
            input.close();
            // get the property value and print it out
            System.out.println("BASE URI : " + prop.getProperty("BASE_URI"));



        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //This sets up the Base URI
    public void setupSpec() {
        BASE_URI = "https://cia.furlenco.com/api/v1";

        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    public void resetSpec() {
        requestSpec = given();
    }

    public void resetResponse() {
        response = null;
    }

    //This Serves the purpose of Signup
    public void signUp()
    {
        Response response = given()
                    .header("Referer", "https://www.furlenco.com/bengaluru")
                    .header("Origin", "https://www.furlenco.com")
                .body(prop.getProperty("signupPayload"))
                .when()
                    .post(prop.getProperty("signupPath"))
                .then()
                    .extract().response();
        if(response.statusCode()!=200 && response.asString().contains("Please login"))
            System.out.println(response.asString());
        else
            Assert.assertEquals(response.statusCode(),200);
    }

    //Calls the login api
    public void login() {
        Response response = given()
                    .body(prop.getProperty("loginPayload"))
                .when()
                    .post(prop.getProperty("loginpath"))
                .then()
                    .extract().response();
        System.out.println("login response is " + response.asString());
        loginToken = response.jsonPath().getString("session_token.token");
        System.out.println("login token is " + loginToken);
        System.out.println(response.statusLine());
        Assert.assertEquals(response.statusCode(), 200);
    }

    //Logout api is called and asserted
    public void logout()
    {
        given()
                .header("Content-Type","application/json")
                .when()
                    .post(prop.getProperty("logoutPath"))
                .then()
                    .log().all()
                .assertThat().statusCode(200);

    }

}
