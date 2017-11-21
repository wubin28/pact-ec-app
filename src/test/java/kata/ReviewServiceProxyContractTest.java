package kata;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ReviewServiceProxyContractTest {
    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("review_service", "localhost", 8080, PactSpecVersion.V2, this);

    @Pact(provider="review_service", consumer="ec_app")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder.given("The ratings in Review service are ready")
                .uponReceiving("A request for ratings")
                .path("/ratings")
                .query("id=123&name=ben")
                .method("GET")
                .willRespondWith()
                .headers(reqHeader())
                .status(200)
                .body("[{\"rating\":3}, {\"rating\":4}]")
                .toPact();
    }

    @NotNull
    private Map<String, String> reqHeader() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    @Test
    @PactVerification("review_service")
    public void should_get_a_list_of_ratings() {
        ReviewServiceProxy reviewServiceProxy = new ReviewServiceProxy("http://localhost:8080/ratings?id=123&name=ben");
        assertEquals(Arrays.asList(new Rating(3), new Rating(4)),
                reviewServiceProxy.getRatings());
    }
}
