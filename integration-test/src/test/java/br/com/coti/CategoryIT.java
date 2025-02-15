package br.com.coti;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = ProdutosApiApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIT {

    private String BASE_URL;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        BASE_URL = "http://localhost:" + port + "/api/categorias";
    }

    @Test
    public void givenNonExistentEndPointThenReturnNOFOUND() {
        RestAssured.get(BASE_URL + "/cadastrar")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void givenCategoriesExistsThenReturnCategories() {
        RestAssured.get(BASE_URL + "/consultar")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", equalTo(5))
                .body("find { it.nome == 'INFORMATICA' }.nome", equalTo("INFORMATICA"))
                .body("find { it.nome == 'OUTROS' }.nome", equalTo("OUTROS"))
                .body("find { it.nome == 'PAPELARIA' }.nome", equalTo("PAPELARIA"))
                .body("find { it.nome == 'VESTUARIO' }.nome", equalTo("VESTUARIO"))
                .body("find { it.nome == 'ELETRÔNICOS' }.nome", equalTo("ELETRÔNICOS"));

    }
}
