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
REPOSITORY = 1ambda/playground-java
MODULE = spring

SWAGGER_DIR = ../schema/swagger
SWAGGER_SCHEMA = "$(SWAGGER_DIR)/gateway-rest.yaml"
SWAGGER_CONFIG = "$(SWAGGER_DIR)/swagger-codegen.spring.json"
SWAGGER_OUTPUT_DIR = "src/main/java/com/github/lambda/playground/swagger"

.PHONY: swagger.clean
swagger.clean:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: cleaning generated swagger files"
	@ echo "-----------------------------------------\n"

	rm -rf $(SWAGGER_OUTPUT_DIR)

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: cleaning generated swagger files"
	@ echo ""
	@ echo ""

.PHONY: swagger.generate
swagger.generate:
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Started: generating swagger files"
	@ echo "-----------------------------------------\n"

	@ swagger-codegen generate -l spring -i $(SWAGGER_SCHEMA) -c $(SWAGGER_CONFIG) -t $(SWAGGER_DIR) -o .

	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($$(date -u '+%H:%M:%S')) - Finished: generating swagger files"
	@ echo ""
	@ echo ""

.PHONY: swagger
swagger: swagger.clean swagger.generate

