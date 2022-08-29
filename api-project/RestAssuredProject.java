package project;

import static org.hamcrest.CoreMatchers.*;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredProject {
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String sshKey;
	int sshKeyId;

	@BeforeClass
	public void setUp() {
		reqSpec=new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token ghp_7KgptesgTijgzBknD7bItUQHEDnqiJC189A5n")
				.setBaseUri("https://api.github.com")
				.build();

		sshKey="ssh-rsa kkkkB3NzaC1yc2EAAAADAQABAAABgQC7phu3G1iiq0vmeuTHwS7z2+9U0K0LVk1xq8mgo+JviTWVZTiCxl3ewGipHy6rVdg6q0B+hqUXiRRFEAxyPfxocjBoK5CXhPhzHcRlZZ5Jc+IfGVbjjfsjfk+oc2P5tbFA0QmV3yrqgzT3SrSGxCnRsFQEqj3FAoPIan6pX3FvCq+RdPyLe3czTh9ctxSeGbeSvMbFOKiV3yNEjdYgVaIvOD5rn7/xYWcTHZEky3Ktk1Tac+Zfmo+bKGBQG75xTEc8rhdaWg+QIVsJyQ5JHAEO40Jf/USCWGQr418g6mEz3l31Rbp/UyhIWELqBIFOm9MgCWv90fegApEOSHDsg9otkYQuFXZL6iKbDBAYPfaJAV+QmhM38+E/ASOQkSEJlLNAsnByrSTzxcgOB7V/64dQhaj26/A5Nd1z8UnHgqsw4qCEN9uKmgYueEV07KxNES7xeFURWwyUmbN2dCF1Rkk/BWioIPTaMaBB7MRUVHuY9RN0U1Zbidn01VV4iuiybT9g";
	}



	@Test(priority=1)
	public void postKey() {
		// Create JSON request
		String reqBody = "{\"title\": \"RestAssuredAPIKey\",  \"key\": \""+sshKey+"\" }";

		Response response=given().spec(reqSpec).body(reqBody).when().post("/user/keys");
		String resBody= response.getBody().asPrettyString();
		System.out.println(resBody);
		System.out.println(response.getStatusCode());
		sshKeyId=response.then().extract().path("id");
		System.out.println(sshKeyId);
		
		Assert.assertEquals(response.getStatusCode(), 201, "Correct status code is not returned");

	}
	
	
	@Test(priority=2)
    public void getKey() {
        Response response = 
        		given().spec(reqSpec).when()
            .get("/user/keys");
       System.out.println(response.asPrettyString());
 
       
      Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");

    }
	
	@Test(priority=3)
    public void deleteKey() {
        Response response = 
        		given().spec(reqSpec)// Set headers
            .when()
            .delete("/user/keys/"+sshKeyId); // Send DELETE request
        
        System.out.println(response.asPrettyString());
        
        
        Assert.assertEquals(response.getStatusCode(), 204, "Correct status code is not returned");

 
    }
}