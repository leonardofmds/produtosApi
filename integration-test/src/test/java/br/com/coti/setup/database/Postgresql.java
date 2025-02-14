package br.com.coti.setup.database;


import br.com.coti.factories.ConnectionFactory;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

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



    }

    public  void addCategories() {


        try {
            Connection connection = ConnectionFactory.getConnection();

            Statement statement = connection.createStatement();




            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  new UUID(2, 2) + "', 'INFORMATICA')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  new UUID(2, 2) + "', 'ELETRÔNICOS')");
            statement.executeUpdate("INSERT INTO CATEGORIA(ID,NOME) VALUES('" +  new UUID(2, 2) + "', 'OUTROS')");



            connection.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static void restart() {
        postgres.start();
    }

    public static void Stop() {
        postgres.stop();
    }
}
