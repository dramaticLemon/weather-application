IMAGE ?= weather-application
CONTAINER ?= $(IMAGE)-container
PORT ?= 8080
PROFILE ?= TEST

.PHONY: clean build docker-build docker-run docker-stop docker-rm compose-up compose-down run

clean:
	mvn clean

build:
	mvn clean package

docker-build: 
	docker build -t $(IMAGE) .

stop:
	docker stop $(CONTAINER) 2>/dev/null || true

docker-rm:
	docker rm $(CONTAINER) 2>/dev/null || true

docker-run: docker-stop docker-rm
	docker run -d -p $(PORT):8080 --name $(CONTAINER) $(IMAGE)

up:
	docker-compose up -d --build

down:
	docker-compose down

test-profile:
	mvn clean test -Dspring.profile.active=$(PROFILE)

test:
	docker build --build-arg SPRING_PROFILE=$(PROFILE) -t $(IMAGE)-test -f Dockerfile.test .
	docker run --rm --name $(CONTAINER)-test $(IMAGE)-test

run: build up

# flyway commands
# запустить миграции
flyway-migrate:
	docker-compose run --rm flyway-migration-service migrate
# очистить базу данных
flyway-clean:
	docker-compose run --rm flyway-migration-service clean
# показать статус миграций
flyway-info:
	docker-compose run --rm flyway-migration-service info
# проверить корекность миграций
flyway-validate:
	docker-compose run --rm flyway-migration-service validate
# создать базовую точку
flyway-baseline:
	docker-compose run --rm flyway-migration-service baseline