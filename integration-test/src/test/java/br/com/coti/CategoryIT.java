package br.com.coti;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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
    public void nonExistentEndPoint() {
        RestAssured.get(BASE_URL + "/swagger-config")
                .then()
                .statusCode(404);
    }

    @Test
    public void emptyCategory() {
        RestAssured.get(BASE_URL + "/consultar")
                .then()
                .statusCode(200);

    }
}
