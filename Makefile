swagger:
	open http://localhost:8081/swagger-ui.html

up:
	docker compose -f docker/docker-compose-app.yml up -d


db:
	docker compose -f docker/docker-compose.yml up -d

down:
	docker compose -f docker/docker-compose.yml down -v

compile:
	mvn clean compile jib:dockerBuild

gen:
	mvn clean compile jib:dockerBuild

gup: gen up

all: down gup