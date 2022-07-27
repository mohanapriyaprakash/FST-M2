package live_Project;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.dsl.PactDslWithState;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    Map<String, String> reqHeadres = new HashMap<String, String>();
    String resourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        reqHeadres.put("Content-Type", "application/json");

        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(reqHeadres)
                .body(reqResBody)
                .willRespondWith().status(201)
                .body(reqResBody)
                .toPact();

    }

    @Test
    @PactTestFor(providerName="UserProvider", port="8282")
            public void consumerTest()

    {
    String baseURI="https://localhost:8282";

    Map <String,Object> reqBody=new HashMap<>();
    reqBody.put("id",123);
    reqBody.put("firstName","Mohana");
    reqBody.put("lastName","Priya");
    reqBody.put("email","mohana@test.com");

    given().headers(reqHeadres).body(reqBody).
            when().post(baseURI+resourcePath).then().statusCode(201).log().all();

    }
}