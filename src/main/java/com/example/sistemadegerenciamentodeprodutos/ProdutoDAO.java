package com.example.sistemadegerenciamentodeprodutos;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final Connection CONEXAO_DB;


    public ProdutoDAO(Connection conexao){
        this.CONEXAO_DB =conexao;
    }

    public void inserir(Produto produto){

        String comandoSQL = "INSERT INTO produtos (nome_produto, preco, quantidade,status) VALUES (?,?,?,?)";
    try ( PreparedStatement stmt = CONEXAO_DB.prepareStatement(comandoSQL);){
       stmt.setString(1, produto.getNome());
       stmt.setDouble(2,produto.getPreco());
       stmt.setInt(3,produto.getQuantidade());
       stmt.setString(4,produto.getStatus());
       stmt.executeUpdate();
    }catch (SQLException e){

        System.err.println("erro ao adicionar produto: "+ e.getMessage());
        e.printStackTrace();
    }}

    public void excluirTodos(){
        String sql = "DELETE FROM produtos";
        try(PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
            stmt.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao excluit todos os itens: "+ e.getMessage());
        }}

    public void excluir(int id){
        String sql = "DELETE FROM produtos WHERE id_produto = ?";
        try(PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {

            stmt.setInt(1,id);
            stmt.executeUpdate();

        }catch (SQLException e){
            System.err.println("Ocorreu um erro na exclusão do item de id = "+id+" erro: "+e.getMessage());
        }

    }
    public Produto consutarPorId(int id){
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";
        try(PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setStatus(rs.getString("status"));
                    return produto;}}
        }catch (SQLException e){
            System.err.println("ocorreu um erro: " + e.getMessage());}
        return null;
    }

    public List<Produto> listarTodos(){
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try(PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome_produto"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }
        }catch (SQLException e){
            System.err.println("ocorreu um erro ao listar: "+ e.getMessage());
        }
        return produtos;
    }
    public void  atualizar(Produto produto){
        String sql = "UPDATE produtos SET nome_produto = ?,preco = ?, quantidade = ?, status = ? WHERE id_produto = ?";
        try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)){
         stmt.setInt(5,produto.getId());
         stmt.setString(1, produto.getNome());
         stmt.setDouble(2,produto.getPreco());
         stmt.setInt(3,produto.getQuantidade());
         stmt.setString(4,produto.getStatus());
         stmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("erro ao atualizar o produto: "+ e.getMessage());
        }

    }

    



}
