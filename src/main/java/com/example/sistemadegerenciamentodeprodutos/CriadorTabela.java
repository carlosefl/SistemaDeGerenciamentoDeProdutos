package com.example.sistemadegerenciamentodeprodutos;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class CriadorTabela {
    public static void main(String[] args) {
        try (Connection conexao = ConexaoDB.conectar();
            Statement stmt = conexao.createStatement()){
        //Definindo comando SQL para criar tabelas
            String comandoSql = "CREATE TABLE produtos ("+
                    "id_produto INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "nome_produto TEXT NOT NULL,"+
                    "preco REAL,"+
                    "quantidade INTEGER,"+
                    "status TEXT"+
                    ");";
            System.out.println(comandoSql);
            //executando o comando
            stmt.execute(comandoSql);
            System.out.println("Tabela criada com sucesso");
        }catch (SQLException e){


            System.err.println("Ocorreu um erro: "+ e.getMessage());
            e.printStackTrace();
        }




    }
}
