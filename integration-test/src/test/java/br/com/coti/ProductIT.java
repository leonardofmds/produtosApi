package br.com.coti;

import br.com.coti.entities.Categoria;
import br.com.coti.entities.Produto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = ProdutosApiApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductIT {

    private String BASE_URL;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        BASE_URL = "http://localhost:" + port + "/api/produtos";
    }

    @Test
    @Order(1)
    public void givenEmptyProductCatalogThenReturnNoContent() {
        RestAssured.given()
                .get(BASE_URL + "/consultar")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    @Order(2)
    public void givenProductWithoutCategoryThenReturnBadRequest() {

        Produto payload = new Produto();
        payload.setNome("Produto");
        payload.setPreco(10.0);
        payload.setQuantidade(10);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(BASE_URL + "/cadastrar")
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo("Erro ao cadastrar o produto."));

    }

    @Test
    @Order(3)
    public void givenWorkingProductThenReturnProductCreated() {

    List<Categoria> categories = RestAssured.get("http://localhost:" + port + "/api/categorias/consultar")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .jsonPath()
        .getList(".", Categoria.class);

        Produto payload = new Produto();
        payload.setNome("Resma de papel");
        payload.setPreco(10.0);
        payload.setQuantidade(10);
        payload.setCategoria(categories.getFirst());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(BASE_URL + "/cadastrar")
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_CREATED)
                .body(equalTo("Produto cadastrado com sucesso."));


        RestAssured.given()
                .get(BASE_URL + "/consultar")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("size()", equalTo(1))
                .body("find { it.nome == 'Resma de papel' }.nome", equalTo("Resma de papel"));

    }
}
