package br.com.coti;

import br.com.coti.entities.Categoria;
import br.com.coti.entities.Produto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = ProdutosApiApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIT {

    private String BASE_URL;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        BASE_URL = "http://localhost:" + port + "/api/produtos";
    }

    @Test
    public void createProductWithoutCategory() {

        Produto payload = new Produto();
        payload.setNome("Produto 1");
        payload.setPreco(10.0);
        payload.setQuantidade(10);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(BASE_URL + "/cadastrar")
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(400)
                .body(equalTo("Erro ao cadastrar o produto."));

    }

    @Test
    public void createProductWithCategory() {

        Produto payload = new Produto();
        payload.setNome("Produto 1");
        payload.setPreco(10.0);
        payload.setQuantidade(10);
        payload.setCategoria(new Categoria(new UUID(1,1 ), "Categoria 1", null));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(BASE_URL + "/cadastrar")
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(400)
                .body(equalTo("Erro ao cadastrar o produto."));

    }
}
