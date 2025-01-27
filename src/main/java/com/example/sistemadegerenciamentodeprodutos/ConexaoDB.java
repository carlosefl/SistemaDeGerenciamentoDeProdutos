package com.example.sistemadegerenciamentodeprodutos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL_JDBC_PADRAO = "jdbc:sqlite:banco.db";
    //metodo conectar

    public static Connection conectar(){
        try {
            return DriverManager.getConnection(URL_JDBC_PADRAO);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: "+ e.getMessage());
            return null;
        }
    }

}
