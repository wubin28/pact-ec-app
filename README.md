# The Pact Java Sample

The repo "pact-ec-app" and "pact-ec-review-service" compose the sample project. 

The repo "pact-ec-app" demonstrates an e-commerce application which depends on the "pact-ec-review-service" microservice. The former requests the latter to provide rating information.

# How to Run It on Mac?
- At the consumer side
1. Git clone the ["pact-ec-app"](https://github.com/wubin28/pact-ec-app) repo.
2. Run command "mvn clean test"
3. The pact file target/pacts/ec_app-review_service.json will be generated.

- At the provider side
1. Git clone the ["pact-ec-review-service"](https://github.com/wubin28/pact-ec-review-service) repo.
2. Make sure the port 8080 is not used by running command "lsof -i:8080". You could use "kill \<pid\>" to terminate a process which is occupying the 8080 port.
3. Create a folder "pacts" under the project folder and copy the pact file generated in the consumer side to it.
4. Run command "mvn integration-test"


