package br.com.coti.repositories;

import br.com.coti.entities.Produto;
import br.com.coti.factories.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoRepository {

    public List<Produto> findAll() throws Exception {

        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUTO");

        var produtos = new ArrayList<Produto>();

        while (resultSet.next()) {

            Produto produto = new Produto();
            produto.setId((UUID)resultSet.getObject("id"));
            produto.setId(UUID.fromString(resultSet.getString("id")));
            produto.setNome(resultSet.getString("nome"));
            produto.setPreco(resultSet.getDouble("preco"));
            produto.setQuantidade(resultSet.getInt("quantidade"));

            produtos.add(produto);
        }

        connection.close();

        return produtos;
    }

    public void create(Produto produto) throws Exception {

    	Connection connection = ConnectionFactory.getConnection();
    	var statement = connection.prepareStatement("INSERT INTO PRODUTO(NOME,PRECO,QUANTIDADE,CATEGORIA_ID) VALUES(?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidade());
            statement.setObject(4, produto.getCategoria().getId());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    public void update(Produto produto) throws Exception {

    	Connection connection = ConnectionFactory.getConnection();
    	var statement = connection.prepareStatement("UPDATE PRODUTO SET NOME = ?, PRECO = ?, QUANTIDADE = ?, CATEGORIA_ID = ? WHERE ID = ?");

        try {
            connection.setAutoCommit(false);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidade());
            statement.setObject(4, produto.getCategoria().getId());
            statement.setObject(5, produto.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    public void delete(UUID id) throws Exception {

    	Connection connection = ConnectionFactory.getConnection();
    	var statement = connection.prepareStatement("DELETE FROM PRODUTO WHERE ID = ?");

        try {
            connection.setAutoCommit(false);
            statement.setObject(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    public Produto findById(UUID id) throws Exception {

        Connection connection = ConnectionFactory.getConnection();
        var statement = connection.prepareStatement("SELECT * FROM PRODUTO WHERE ID = ?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();

        Produto produto = null;

        if (resultSet.next()) {
            produto = new Produto();
            produto.setId((UUID)resultSet.getObject("id"));
            produto.setId(UUID.fromString(resultSet.getString("id")));
            produto.setNome(resultSet.getString("nome"));
            produto.setPreco(resultSet.getDouble("preco"));
            produto.setQuantidade(resultSet.getInt("quantidade"));
        }

        connection.close();

        return produto;
    }

}
