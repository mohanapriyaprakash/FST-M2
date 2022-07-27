package live_Project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.CoreMatchers.equalTo;

public class GitHubProject {
    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;

    String sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQClmgT69j6eF/lXigGzgU59vY+4XPMOq4v9hXl7IbUSx4TzV4kTdu78r+JV6140JhQPMgpwChoyGCx3DK2fSlti0mkIGK8GEHBfUPfccVZwTdfnrrhXuayOOTUdu1q82344LGBL4KACSmuY0ocGdYp3Zgr3AaaWQpt1SeAhcQBqg9C85p0bdtx1zq6zFnmnR5tZRtEmpdNLZ0WisVrOZLFvgqq1uhRVL4GpElNrKkYovsiIkF1P7DGwdgrjBXOh28raDa4zPRitLBn6Bn4qYU4MCAT4rfhUFS+G0CdavD8Kl0uCidRwoK55Sa1oHxduK91mdzZYRQ12/6g6Idvh8jEZ";
    int id;
    @BeforeClass
    public void setup()
    {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set base URL
                .setBaseUri( "https://api.github.com")
                .setAuth(oauth2("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"))
                // Build request specification
                .build();

    }

    @Test(priority = 1)
    public void addKey()
    {
        Map<String,String> map= new HashMap<String,String>();
        map.put("title","TestAPIKey");
        map.put("key",sshKey);
     Response response= given().spec(requestSpec)
             .body(map).when().post("/user/keys");
     id=response.then().extract().path("id");

     System.out.println("The id of the key is:"+id);
     System.out.print("The status received:"+response.statusLine());
     response.then().statusCode(201).log().all();
    }

    @Test (priority = 2)
    public void getKey()
    {
    Response response=given().spec(requestSpec).pathParams("keyId",id).when().get("/user/keys/{keyId}");
   response.then().statusCode(200).log().all();

    }
    @Test (priority = 3)
    public void deleteKey()
    {
     Response response= given().spec(requestSpec).pathParam("keyId",id).when().delete("/user/keys/{keyId}");
     response.then().statusCode(200).log().all();
    }
}

