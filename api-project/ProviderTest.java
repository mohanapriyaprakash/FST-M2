package live_Project;

import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.provider.PactVerification;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpsTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
@PactFolder("traget/pacts")
public class ProviderTest {

    @BeforeEach
    public void setUp(PactVerificationContext context)
    {
        HttpsTestTarget target=new HttpsTestTarget("localhost",8585);
        context.setTarget(target);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void providerTest(PactVerificationContext context)
    {
    context.verifyInteraction();
    }

    @State("Request to create user")
    public void state1()
    {

    }
}
