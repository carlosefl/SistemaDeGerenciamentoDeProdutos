package com.example.sistemadegerenciamentodeprodutos;

public class Produto {
    private int id;
    private String nome;
    private  int quantidade;
    private double preco;
    private String status;
    Produto(String nome, int quantidade, double preco, String status){
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.status = status;
    }
    Produto(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public String getStatus() {
        return status;
    }

    public String getNome(){
        return nome;
    }

}
