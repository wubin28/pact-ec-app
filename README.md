# The Consumer Sample Project Using Pact JVM Consumer

The repo "pact-ec-app" demonstrates an e-commerce application 
which depends on the "pact-ec-review-service" restful service. 
The former (as a consumer) sends requests to the latter 
(as a provider) to get rating information (5-star rating) 
for a product on the e-commerce website with product id 
and user name.

# How to Run It
0. Install jdk 1.8+ and maven 3
1. Git clone the ["pact-ec-app"](https://github.com/wubin28/pact-ec-app) repo.
2. Run command "./mvnw clean test"
3. The pact file target/pacts/ec_app-review_service.json will be generated.

# Please refer to the ["pact-ec-review-service"](https://github.com/wubin28/pact-ec-review-service) for how to implement the corresponding pact provider.
