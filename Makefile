TAG = "Makefile"

DOCKER = docker
MYSQLCLIENT = mycli
PIP = pip
GOCMD = go
INSTALLER = brew install
DOCKER_HOST_IP := $(shell ipconfig getifaddr en0)

VCS = github.com
REPOSITORY = "1ambda/domain-driven-design-spring"

.PHONY: install.tool
prepare:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Installing prerequisites"
	@ $(PIP) install -U mycli
	@ $(GOCMD) get -u -v github.com/holys/redis-cli
	@ $(GOCMD) get -u -v github.com/fgeller/kt
	@ @(INSTALLER) kafkacat

.PHONY: compose.prepare
compose.prepare:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Preparing docker-compose"
	@ echo "-----------------------------------------\n"
	@ echo "export DOCKER_HOST_IP=$(DOCKER_HOST_IP)"
	@ echo "\n-----------------------------------------"
	@ echo ""

.PHONY: compose
compose: compose.prepare
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Running docker-compose"
	@ docker stop $(docker ps -a -q) || true
	@ docker rm -f $(docker ps -a -q) || true
	@ docker volume rm $(docker volume ls -f dangling=true -q) || true
	@ docker-compose -f docker-compose.storage.yml rm -fsv || true
	@ DOCKER_HOST_IP=$(DOCKER_HOST_IP) docker-compose -f docker-compose.storage.yml up

.PHONY: compose.clean
compose.clean:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Starting: Cleaning docker resources"
	@ echo "-----------------------------------------\n"
	@ docker stop `docker ps -a -q` || true
	@ docker rm -f `docker ps -a -q` || true
	@ docker rmi -f `docker images --quiet --filter "dangling=true"` || true
	@ docker volume rm `docker volume ls -f dangling=true -q` || true
	@ rm -rf ./docker-volumes
	@ docker network rm `docker network ls -q` || true
	@ echo ""
	@ echo "\n-----------------------------------------"
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Finished: Cleaning docker resources"

.PHONY: mysql.cli
mysql.cli:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Connecting to mysql"
	@ $(MYSQLCLIENT) -u root -h localhost application -p root

.PHONY: redis.cli
redis.cli:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Connecting to redis"
	@ redis-cli

.PHONY: kafka.consume.websocket
kafka.consume.websocket:
	@ KT_BROKERS=0.0.0.0:9092 kt consume \
		-topic application.gateway.websocket.raw \
		-group makefile | jq

.PHONY: kafka.topic
kafka.topic:
	@ KT_BROKERS=0.0.0.0:9092 kt topic | jq

.PHONY: kafka.partition
kafka.partition:
	@ KT_BROKERS=0.0.0.0:9092 kt group

