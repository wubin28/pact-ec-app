# The two Pact Sample Projects Based on pact-jvm-consumer-junit_2.11 and pact-jvm-provider-spring-mvc_2.10. 

The repo "pact-ec-app" and "pact-ec-review-service" are the sample projects to show how to use the pact-jvm-consumer-junit_2.11 and the pact-jvm-provider-spring-mvc_2.10 correspondingly. 

The repo "pact-ec-app" demonstrates an e-commerce application which depends on the "pact-ec-review-service" restful service. The former (as a consumer) requests the latter (as a provider) to provide rating information (such as a 3-star of a 5-star rating) for a product on the e-commerce website.

# How to Run It
- At the consumer side
1. Git clone the ["pact-ec-app"](https://github.com/wubin28/pact-ec-app) repo in the consumer folder.
2. Run command "mvn clean test"
3. The pact file target/pacts/ec_app-review_service.json will be generated.

- At the provider side
1. Git clone the ["pact-ec-review-service"](https://github.com/wubin28/pact-ec-review-service) repo in the provider folder.
3. Copy the pact file generated in the consumer side above to the folder "src/test/resources" under the project folder.
4. Run command "mvn clean test"


