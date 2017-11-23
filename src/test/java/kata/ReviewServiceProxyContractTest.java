package kata;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class ReviewServiceProxyContractTest {
    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("review_service", "localhost", 8080, PactSpecVersion.V2, this);

    @Pact(provider="review_service", consumer="ec_app")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder.given("The ratings in Review service are ready")
                .uponReceiving("A request for ratings for a product")
                .path("/ratings")
                .matchQuery("productId", "\\d+")
                .matchQuery("userName", "[a-z]+")
                .method("GET")
                .willRespondWith()
                .headers(responseHeaders())
                .status(200)
                .body(new PactDslJsonArray().arrayEachLike()
                        .integerType("productId")
                        .stringMatcher("userName", "[a-z]+", "ben")
                        .integerType("rating")
                )
                .toPact();
    }

    @NotNull
    private Map<String, String> responseHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    @Test
    @PactVerification("review_service")
    public void should_get_a_list_of_ratings() {
        ReviewServiceProxy reviewServiceProxy = new ReviewServiceProxy("http://localhost:8080/ratings?productId=123&userName=ben");
        final List<Rating> actual = reviewServiceProxy.getRatings();
        assertThat(actual.get(0).getProductId(), instanceOf(Integer.class));
        assertThat(actual.get(0).getUserName(), instanceOf(String.class));
        assertThat(actual.get(0).getRating(), instanceOf(Integer.class));
    }
}
