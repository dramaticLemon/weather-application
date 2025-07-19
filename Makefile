IMAGE ?= weather-application
CONTAINER ?= $(IMAGE)-container
PORT ?= 8080

.PHONY: clean build docker-build docker-run docker-stop docker-rm compose-up compose-down run

clean:
	mvn clean

build:
	mvn clean package

docker-build: 
	docker build -t $(IMAGE) .

docker-stop:
	docker stop $(CONTAINER) 2>/dev/null || true

docker-rm:
	docker rm $(CONTAINER) 2>/dev/null || true

docker-run: docker-stop docker-rm
	docker run -d -p $(PORT):8080 --name $(CONTAINER) $(IMAGE)

compose-up:
	docker-compose up -d --build

compose-down:
	docker-compose down

run: build docker-build docker-run