GOOS    = $(OS)
ifeq ($(GOOS),)
  ifeq ($(shell  uname -s), Darwin)
    GOOS	= darwin
  else
    GOOS	= linux
  endif
endif

TAG = "Makefile"

VCS = github.com
REPOSITORY = 1ambda/domain-driven-design-spring
MODULE = service-gateway

SWAGGER_DIR = ../schema/swagger
SWAGGER_SCHEMA = "$(SWAGGER_DIR)/gateway-rest.yaml"
SWAGGER_SERVER_CONFIG = "$(SWAGGER_DIR)/swagger-codegen.gateway-server.json"
SWAGGER_SERVER_OUTPUT_DIR = "src/main/java/com/github/lambda/gateway/swagger"
SWAGGER_CLIENT_CONFIG = "$(SWAGGER_DIR)/swagger-codegen.gateway-client.json"
SWAGGER_CLIENT_OUTPUT_TEMP_DIR = ".swagger-client"
SWAGGER_CLIENT_OUTPUT_DIR = "src/test/java/com/github/lambda/gateway/swagger/client"
SWAGGER_CLIENT_OUTPUT_GEN_DIR = "$(SWAGGER_CLIENT_OUTPUT_TEMP_DIR)/src/main/java/com/github/lambda/gateway/swagger/client"

.PHONY: swagger.clean
swagger.clean:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: cleaning generated swagger files"
	@ echo "-----------------------------------------\n"

	rm -rf $(SWAGGER_SERVER_OUTPUT_DIR)
	rm -rf $(SWAGGER_CLIENT_OUTPUT_DIR)

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: cleaning generated swagger files"
	@ echo ""
	@ echo ""

.PHONY: swagger.server
swagger.server:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: generating swagger server files"
	@ echo "-----------------------------------------\n"

	@ swagger-codegen generate -l spring -i $(SWAGGER_SCHEMA) -c $(SWAGGER_SERVER_CONFIG) -t $(SWAGGER_DIR) -o .

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: generating swagger server files"
	@ echo ""
	@ echo ""

.PHONY: swagger.client
swagger.client:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: generating swagger client files"
	@ echo "-----------------------------------------\n"

	@ swagger-codegen generate -l java -i $(SWAGGER_SCHEMA) -c $(SWAGGER_CLIENT_CONFIG) -t $(SWAGGER_DIR) -o $(SWAGGER_CLIENT_OUTPUT_TEMP_DIR)
	@ mv $(SWAGGER_CLIENT_OUTPUT_GEN_DIR) $(SWAGGER_CLIENT_OUTPUT_DIR)
	@ rm -rf $(SWAGGER_CLIENT_OUTPUT_TEMP_DIR)

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: generating swagger client files"
	@ echo ""
	@ echo ""

.PHONY: test.unit
test.unit:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: running unit tests"
	@ echo "-----------------------------------------\n"

	@ ./gradlew test --tests com.github.lambda*

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: running unit tests"
	@ echo ""
	@ echo ""

.PHONY: test.integration
test.integration:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: running integration tests"
	@ echo "-----------------------------------------\n"

	@ ./gradlew test --tests integration*

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: running integration tests"
	@ echo ""
	@ echo ""

.PHONY: run
run:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: running '$(MODULE)' Application"
	@ echo "-----------------------------------------\n"

	@ echo "SPRING_PROFILES_ACTIVE=local ./gradlew bootRun -x test"
	@ echo ""
	@ echo ""

	@ SPRING_PROFILES_ACTIVE=local ./gradlew bootRun -x test

.PHONY: test
test: test.unit test.integration

.PHONY: check.security
check.security:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: running dependency security check"
	@ echo "-----------------------------------------\n"

	@ ./gradlew dependencyCheckAnalyze
	@ echo ""

.PHONY: swagger
swagger: swagger.clean swagger.server swagger.client

