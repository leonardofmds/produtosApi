swagger:
	open http://localhost:8081/swagger-ui.html

up:
	docker compose up -d

getCat:
	curl http://localhost:8081/api/categorias/consultar

getProd:
	curl http://localhost:8081/api/categorias