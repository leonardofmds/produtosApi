package br.com.coti.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.coti.entities.Categoria;
import br.com.coti.factories.ConnectionFactory;

public class CategoriaRepository {
	
	public List<Categoria> findAll() throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM CATEGORIA");

		List<Categoria> categorias = new ArrayList<Categoria>();

		while (resultSet.next()) {

			Categoria categoria = new Categoria();
			categoria.setId((UUID)resultSet.getObject("id"));
			categoria.setId(UUID.fromString(resultSet.getString("id")));
			categoria.setNome(resultSet.getString("nome"));

			categorias.add(categoria);
		}

		connection.close();

		return categorias;
	}

}
