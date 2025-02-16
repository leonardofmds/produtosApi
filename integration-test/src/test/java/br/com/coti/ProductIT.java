package br.com.coti;

import br.com.coti.dtos.ProdutoRequestDto;
import br.com.coti.dtos.ProdutoResponseDto;
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
                .statusCode(HttpStatus.SC_OK)
                .body("size()", equalTo(0));

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
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", equalTo("Bad Request"));

    }

    @Test
    @Order(3)
    public void givenProductThenSuccess() {

    List<Categoria> categories = RestAssured.get("http://localhost:" + port + "/api/categorias/consultar")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", equalTo(5))
            .extract()
            .jsonPath()
            .getList(".", Categoria.class);


        Categoria papelaria =
                categories.stream().filter(c -> "PAPELARIA".equals(c.getNome())).findFirst().orElse(null);
        Assertions.assertNotNull(papelaria);

        ProdutoRequestDto payload = new ProdutoRequestDto();
        payload.setNome("Resma de papel");
        payload.setPreco(10.0);
        payload.setQuantidade(10);

        payload.setCategoriaId(papelaria.getId());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(BASE_URL + "/cadastrar")
                .then()
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

    @Test
    @Order(4)
    public void givenProductThenDeleteIt() {

        ProdutoResponseDto product = RestAssured.given()
                .get(BASE_URL + "/consultar")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("size()", equalTo(1))
                .extract()
                .jsonPath()
                .getList(".", ProdutoResponseDto.class)
                .getFirst();

        RestAssured
                .given()
                .get(BASE_URL + "/consultar" + "/" + product.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        RestAssured
                .given()
                .queryParam("id", product.getId())
                .delete(BASE_URL + "/excluir")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("Produto excluído com sucesso."));


        System.out.println(BASE_URL + "/consultar" + "/" + product.getId());
        RestAssured
                .given()
                .get(BASE_URL + "/consultar" + "/" + product.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);


    }
}
