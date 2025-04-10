package br.com.coti.repositories;

import br.com.coti.dtos.CategoriaQtdProdutoResponseDto;
import br.com.coti.entities.Categoria;
import br.com.coti.factories.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
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

	public Categoria findById(UUID id) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		var statement = connection.prepareStatement("SELECT * FROM CATEGORIA WHERE ID = ?");
		statement.setObject(1, id);
		ResultSet resultSet = statement.executeQuery();

		Categoria categoria = null;

		if(resultSet.next()) {

			categoria = new Categoria();
			categoria.setId((UUID)resultSet.getObject("id"));
			categoria.setId(UUID.fromString(resultSet.getString("id")));
			categoria.setNome(resultSet.getString("nome"));
		}

		connection.close();

		return categoria;
	}

	public List<CategoriaQtdProdutoResponseDto> groupByQtdProdutos() throws Exception {

		var query = """
				select c.nome as nomecategoria,
				       sum(p.quantidade) as qtdprodutos
				from produto p
				         inner join categoria c
				                    on c.id = p.categoria_id
				group by c.nome;
				""";


		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		List<CategoriaQtdProdutoResponseDto> lista = new ArrayList<CategoriaQtdProdutoResponseDto>();

		while (resultSet.next()) {

			CategoriaQtdProdutoResponseDto dto = new CategoriaQtdProdutoResponseDto();
			dto.setNomeCategoria(resultSet.getString("nomecategoria"));
			dto.setQtdProdutos(resultSet.getInt("qtdprodutos"));

			lista.add(dto);
		}
		connection.close();

		return lista;
	}

}
