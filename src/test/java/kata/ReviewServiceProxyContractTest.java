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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ReviewServiceProxyContractTest {
    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("review_service", "localhost", 8080, PactSpecVersion.V3, this);

    @Pact(provider="review_service", consumer="ec_app")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        // Pass the parameters to the provider by pact
        Map<String, Object> params = new HashMap<>();
        params.put("productId", "123456");
        params.put("userName", "ben");

        return builder.given("The ratings in Review service are ready", params)
                .uponReceiving("A request for ratings for a product")
                .path("/ratings")
                .query("productId=123456&userName=ben")
                .method("GET")
                .willRespondWith()
                .headers(responseHeaders())
                .status(200)
                .body(new PactDslJsonArray().arrayEachLike()
                        .stringMatcher("productId", "^[0-9][0-9][0-9][0-9][0-9][0-9]$", "123456")
                        .stringMatcher("userName", "[a-z]+", "ben")
                        .stringMatcher("rating", "^[0-5]$", "5")
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
        ReviewServiceProxy reviewServiceProxy = new ReviewServiceProxy("http://localhost:8080/ratings?productId=123456&userName=ben");

        final List<Rating> actual = reviewServiceProxy.getRatings();

        assertThat(actual.get(0).getProductId(), instanceOf(String.class));
        assertThat(actual.get(0).getProductId().length(), is(6));
        assertThat(actual.get(0).getUserName(), instanceOf(String.class));
        assertThat(actual.get(0).getRating(), instanceOf(String.class));
        assertThat(Integer.valueOf(actual.get(0).getRating()), greaterThanOrEqualTo(0));
        assertThat(Integer.valueOf(actual.get(0).getRating()), lessThanOrEqualTo(5));
    }
}
