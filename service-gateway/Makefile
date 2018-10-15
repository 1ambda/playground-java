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
MODULE = spring

SWAGGER_DIR = ../schema/swagger
SWAGGER_SCHEMA = "$(SWAGGER_DIR)/gateway-rest.yaml"
SWAGGER_SERVER_CONFIG = "$(SWAGGER_DIR)/swagger-codegen.spring-server.json"
SWAGGER_OUTPUT_DIR = "src/main/java/com/github/lambda/gateway/swagger"
SWAGGER_CLIENT_CONFIG = "$(SWAGGER_DIR)/swagger-codegen.spring-client.json"
SWAGGER_CLIENT_OUTPUT_TEMP_DIR = ".swagger-client"
SWAGGER_CLIENT_OUTPUT_DIR = "src/main/java/com/github/lambda/gateway/swagger/client"
SWAGGER_CLIENT_OUTPUT_GEN_DIR = "$(SWAGGER_CLIENT_OUTPUT_TEMP_DIR)/$(SWAGGER_CLIENT_OUTPUT_DIR)"

.PHONY: swagger.clean
swagger.clean:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: cleaning generated swagger files"
	@ echo "-----------------------------------------\n"

	rm -rf $(SWAGGER_OUTPUT_DIR)

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

.PHONY: swagger
swagger: swagger.clean swagger.server swagger.client

