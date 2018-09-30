TAG = "Makefile"

DOCKER = docker
MYSQLCLIENT = mycli
PIP = pip
GOCMD = go

VCS = github.com
REPOSITORY = 1ambda/playground-java

.PHONY: install.tool
prepare:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Installing prerequisites"
	@ $(PIP) install -U mycli
	@ $(GOCMD) get -u -v github.com/holys/redis-cli

.PHONY: compose
compose:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Running docker-compose"
	@ docker stop $(docker ps -a -q) || true
	@ docker rm -f $(docker ps -a -q) || true
	@ docker volume rm $(docker volume ls -f dangling=true -q) || true
	@ docker-compose -f docker-compose.storage.yml rm -fsv || true
	@ docker-compose -f docker-compose.storage.yml up

.PHONY: mycli
mycli:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Connecting to mysql"
	@ $(MYSQLCLIENT) -u root -h localhost application -p root

.PHONY: redis-cli
redis-cli:
	@ echo "[$(TAG)] ($(shell TZ=UTC date -u '+%H:%M:%S')) - Connecting to redis"
	@ redis-cli


