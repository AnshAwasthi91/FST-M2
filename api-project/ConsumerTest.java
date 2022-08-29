package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    // Headers
    Map<String,String> headers = new HashMap<>();
    // Resource Path
    String resourcePath = "/api/users";

    //Create the PACT
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        // Headers
        headers.put("Content-Type", "application/json");
        // Request & Response Body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id",123)
                .stringType("firstName","Ansh")
                .stringType("lastName","Awasthi")
                .stringType("email","test@example.com");

        // PACT
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .path(resourcePath)
                .method("POST")
                .headers(headers)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerPostTest(){
        //Mock URL
        String mockServer = "http://localhost:8282";

        // Create request body
        Map<String, Object> reqBody = new HashMap<String, Object>();
        reqBody.put("id", 101);
        reqBody.put("firstName", "Jack");
        reqBody.put("lastName", "Darren");
        reqBody.put("email", "jackD@test.com");

        //Generate request
        given().body(reqBody).headers(headers)
                .when().post(mockServer + resourcePath)
                .then().statusCode(201).log().all();

    }
}
