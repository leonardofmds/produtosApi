package br.com.coti.repositories;

import br.com.coti.entities.Categoria;
import br.com.coti.factories.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class CategoriaRepository {
	
	public List<Categoria> findAll() {

		try(Connection connection = ConnectionFactory.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM CATEGORIA");

			List<Categoria> categorias = new ArrayList<>();

			while (resultSet.next()) {

				Categoria categoria = new Categoria();
				categoria.setId(UUID.fromString(resultSet.getString("id")));
				categoria.setNome(resultSet.getString("nome"));

				categorias.add(categoria);
			}

			return categorias;
		} catch (Exception e) {
            log.error("Erro ao consultar as categorias: {}", e.getMessage());
			return List.of();
        }
    }

	public Categoria findById(UUID id) {

		try(Connection connection = ConnectionFactory.getConnection();
		var statement = connection.prepareStatement("SELECT * FROM CATEGORIA WHERE ID = ?")) {

			statement.setObject(1, id);
            Categoria categoria;

            try (ResultSet resultSet = statement.executeQuery()) {
                categoria = null;

                if (resultSet.next()) {

                    categoria = new Categoria();
                    categoria.setId((UUID) resultSet.getObject("id"));
                    categoria.setId(UUID.fromString(resultSet.getString("id")));
                    categoria.setNome(resultSet.getString("nome"));
                }
            }

            return categoria;
		}  catch (Exception e) {
            log.error("Erro ao consultar a categoria: {}", e.getMessage());
			return null;
        }
    }

}
