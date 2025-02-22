package br.com.coti.setup.database;


import br.com.coti.factories.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

@Slf4j
@Component
public class Postgresql {

    private static PostgreSQLContainer<?> postgres;

    public Postgresql() {
        int staticPort = 5432; // Set your preferred static port

        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
                .withDatabaseName("bd_produtosapi")
                .withUsername("admin")
                .withPassword("senha123")
                .withExposedPorts(staticPort)  // Exposing the default PostgreSQL port inside the container
                .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withPortBindings(
                        new com.github.dockerjava.api.model.PortBinding(
                                com.github.dockerjava.api.model.Ports.Binding.bindPort(staticPort),
                                new com.github.dockerjava.api.model.ExposedPort(staticPort)
                        )
                ));

        postgres.start();

        addCategories();

    }

    public  void addCategories() {


        try (Connection connection = ConnectionFactory.getConnection()) {

            Statement statement = connection.createStatement();

            createTables(connection);

            log.info("Inserting categories");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +   UUID.randomUUID() + "', 'INFORMATICA')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  UUID.randomUUID()  + "', 'ELETRÔNICOS')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  UUID.randomUUID()  + "', 'OUTROS')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  UUID.randomUUID()  + "', 'VESTUARIO')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  UUID.randomUUID()  + "', 'PAPELARIA')");
            log.info("Categories inserted");

        } catch (Exception e) {
            log.error("Error inserting categories: {}", e.getMessage());
        }
    }



    private void createTables(Connection connection) {
        log.info("Creating tables");
        try (Statement statement = connection.createStatement()) {
            String createCategoryTableSQL = "CREATE TABLE IF NOT EXISTS CATEGORIA (" +
                    "ID UUID PRIMARY KEY, " +
                    "NOME VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createCategoryTableSQL);

            String createProductTableSQL =
                "CREATE TABLE PRODUTO(" +
                    "ID UUID PRIMARY KEY DEFAULT gen_random_uuid(), " +
                    "NOME VARCHAR(50) NOT NULL, " +
                    "PRECO DECIMAL(10,2) NOT NULL CHECK(PRECO>0), " +
                    "QUANTIDADE INTEGER NOT NULL, " +
                    "CATEGORIA_ID UUID NOT NULL, " +
                    "FOREIGN KEY(CATEGORIA_ID) REFERENCES CATEGORIA(ID)" +
                ")";


            statement.executeUpdate(createProductTableSQL);
        } catch (Exception e) {
            log.error("Error creating tables: {}", e.getMessage());
        }
        log.info(" Table created");
    }



    public static void restart() {
        postgres.start();
    }

    public static void Stop() {
        postgres.stop();
    }
}
