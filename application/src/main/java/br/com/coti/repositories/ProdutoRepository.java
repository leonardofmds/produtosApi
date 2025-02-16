package br.com.coti.repositories;

import br.com.coti.entities.Categoria;
import br.com.coti.entities.Produto;
import br.com.coti.factories.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoRepository {

    public List<Produto> findAll(){

        try(Connection connection = ConnectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM PRODUTO");
            var categoriaRepository = new CategoriaRepository();

            var produtos = new ArrayList<Produto>();

            while (resultSet.next()) {
                Categoria categoria = categoriaRepository.findById((UUID)resultSet.getObject("categoria_id"));
                Produto produto = new Produto();
                produto.setId((UUID)resultSet.getObject("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setCategoria(categoria);

                produtos.add(produto);
            }

            return produtos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Produto produto, UUID categoriaId) throws Exception {

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO PRODUTO(NOME,PRECO,QUANTIDADE,CATEGORIA_ID) VALUES(?, ?, ?, ?)");

            connection.setAutoCommit(false);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidade());
            statement.setObject(4, categoriaId);
            statement.executeUpdate();
            connection.commit();
        }
    }

    public void update(Produto produto) throws Exception {

    	try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE PRODUTO SET NOME = ?, PRECO = ?, QUANTIDADE = ?, CATEGORIA_ID = ? WHERE ID = ?");
            connection.setAutoCommit(false);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidade());
            statement.setObject(4, produto.getCategoria().getId());
            statement.setObject(5, produto.getId());
            statement.executeUpdate();
            connection.commit();
        }
    }

    public void delete(UUID id) {

    	try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM PRODUTO WHERE ID = ?");

            connection.setAutoCommit(false);
            statement.setObject(1, id);
            statement.executeUpdate();
            connection.commit();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Produto findById(UUID id) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM PRODUTO WHERE ID = ?");
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();

            Produto produto = null;

            if (resultSet.next()) {
                var categoriaRepository = new CategoriaRepository();
                Categoria categoria = categoriaRepository.findById((UUID) resultSet.getObject("categoria_id"));

                produto = new Produto();
                produto.setId((UUID) resultSet.getObject("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setCategoria(categoria);
            }
            return produto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
