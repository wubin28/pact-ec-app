package kata;


import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.Rule;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ReviewServiceProxyContractTest {

    public static final String PRODUCT_ID = "123456";
    public static final String USER_NAME = "ben";
    // This sets up a mock server that pretends to be our provider
    @Rule
    public PactProviderRule rule = new PactProviderRule("review_service", "localhost", 1234, this);

    // This defines the expected interaction for out test
    @Pact(provider="review_service", consumer="ec_app")
    public RequestResponsePact pact(PactDslWithProvider builder) {
        // Pass the parameters to the provider by pact
        Map<String, Object> params = new HashMap<>();
        params.put("productId", PRODUCT_ID);
        params.put("userName", USER_NAME);

        return builder
                .given("The ratings in Review service are ready")
                .uponReceiving("A request for ratings of a product")
                .path("/ratings")
                .method("GET")
                .query("productId=" + PRODUCT_ID + "&" + "userName=" + USER_NAME)
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
        ReviewServiceProxy reviewServiceProxy = new ReviewServiceProxy("http://localhost:1234/ratings?productId=123456&userName=ben");

        final List<Rating> actual = reviewServiceProxy.getRatings();

        assertThat(actual.get(0).getProductId(), instanceOf(String.class));
        assertThat(actual.get(0).getProductId().length(), is(6));
        assertThat(actual.get(0).getUserName(), instanceOf(String.class));
        assertThat(actual.get(0).getRating(), instanceOf(String.class));
        assertThat(Integer.valueOf(actual.get(0).getRating()), greaterThanOrEqualTo(0));
        assertThat(Integer.valueOf(actual.get(0).getRating()), lessThanOrEqualTo(5));
    }
}
