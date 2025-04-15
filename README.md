# 🛍️ produtosApi

**produtosApi** é uma API REST desenvolvida com **Spring Boot** e **JDBC** para gerenciamento de produtos e categorias, com segurança baseada em **JWT (JSON Web Token)**. O projeto utiliza **PostgreSQL** como banco de dados relacional e está preparado para execução em ambientes com **Docker** via `docker-compose`.

---

## 🚀 Tecnologias Utilizadas

- **Spring Boot** – Framework para criação de aplicações Java modernas e robustas.  
  🔗 [Documentação](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

- **JDBC (Java Database Connectivity)** – API para acesso direto ao banco de dados relacional.  
  🔗 [Documentação](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)

- **PostgreSQL** – Banco de dados relacional de código aberto.  
  🔗 [Documentação](https://www.postgresql.org/docs/)

- **JWT (JSON Web Token)** – Autenticação segura via token.  
  🔗 [Documentação](https://jwt.io/introduction)

- **Docker** – Contêinerização da aplicação para ambientes isolados.  
  🔗 [Documentação](https://docs.docker.com/)

- **docker-compose** – Orquestração de contêineres Docker para configurar o ambiente.  
  🔗 [Documentação](https://docs.docker.com/compose/)

---

## 📑 Endpoints da API

| Rota             	   				    		| Método | Descrição                                 | Autenticação |
|-----------------------------------------------|--------|-------------------------------------------|--------------|
| `/api/categorias/consultar`		    	    | GET    | Consulta lista de categorias              | ✅ JWT       |
| `/api/produtos/consultar`  		    		| GET    | Lista todos os produtos                   | ✅ JWT       |
| `/api/produtos/consultarPorNome`      		| GET    | Consultar produtos pelo nome       		 | ✅ JWT       |
| `/api/produtos/consultarPorId/{id}`   		| GET    | Consultar produtos pelo nome       		 | ✅ JWT       |
| `/api/produtos/cadastrar`  					| POST   | Cria um novo produto                      | ✅ JWT       |
| `/api/produtos/atualizar/{id}`				| PUT    | Atualiza um produto pelo ID               | ✅ JWT       |
| `/api/produtos/excluir/{id}`					| DELETE | Remove um produto pelo ID              	 | ✅ JWT       |
| `/api/dashboard/totalProdutosPorCategoria` 	| GET    | Retorna estatísticas e dados de resumo    | ✅ JWT       |

> ❗ Todos os endpoints exigem envio de um **token JWT válido** no cabeçalho `Authorization: Bearer <token>`.

