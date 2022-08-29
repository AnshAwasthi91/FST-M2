package liveProject;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
@PactFolder("target/pacts")
public class ProviderTest {

    @BeforeEach
    public void setup(PactVerificationContext context){
        //Set target for provider to send request to --> Actual Server
        HttpTestTarget target = new HttpTestTarget("localhost",8585);
        context.setTarget(target);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactTestTemplate(PactVerificationContext context){
        //Verify interactions b/w Consumer & Provider using contract generated in target/pacts folder
        context.verifyInteraction();
    }

    // State is used to send the call to consumer
    @State("A request to create a user")
    public void sampleSate(){}
}
