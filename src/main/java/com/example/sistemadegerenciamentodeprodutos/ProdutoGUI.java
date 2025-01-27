package com.example.sistemadegerenciamentodeprodutos;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoGUI extends Application{

    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private TableView<Produto> tableView;
    private  TextField nomeInput, quantidadeInput, precoInput;
    private ComboBox<String> statusComboBox;
    private Connection conexaoDB;

    @Override
    public void start(Stage janela){
        conexaoDB = ConexaoDB.conectar();
        produtoDAO = new ProdutoDAO(conexaoDB);
        produtos = FXCollections.observableArrayList(produtoDAO.listarTodos());

        janela.setTitle("Gerenciamento de estoque de produtos");

        VBox vBox = new VBox();
        vBox.setPadding( new Insets(10,10,10,10));
        vBox.setSpacing(10);

        HBox nomeProdutoHbox = new HBox();
        nomeProdutoHbox.setSpacing(10);
        Label nomeLabel = new Label("Produto: ");
        nomeInput = new TextField();
        nomeProdutoHbox.getChildren().addAll(nomeLabel,nomeInput);

        HBox quantidadeHbox = new HBox();
        quantidadeHbox.setSpacing(10);
        Label quantidadeLabel = new Label("Quantidade: ");
        quantidadeInput = new TextField();
        quantidadeHbox.getChildren().addAll(quantidadeLabel,quantidadeInput);
        HBox precoHbox = new HBox();
        precoHbox.setSpacing(10);
        Label precoLabel = new Label("Preço: ");
        precoInput = new TextField();
        precoHbox.getChildren().addAll(precoLabel, precoInput);
        HBox statusHbox = new HBox();
        statusHbox.setSpacing(10);
        Label statusLabel = new Label("Status: ");
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Estoque Normal", "Estoque Baixo");
        statusHbox.getChildren().addAll(statusLabel,statusComboBox);
        Button adicionar = new Button("Adicionar");
        adicionar.setOnAction(e -> {
            Produto produto = new Produto(nomeInput.getText(), Integer.parseInt(quantidadeInput.getText()),
                    Double.parseDouble(precoInput.getText().replace(",",".")), statusComboBox.getValue());
            produtoDAO.inserir(produto);
            produtos.setAll(produtoDAO.listarTodos());
            limparCampos();

        });
        Button atualizar = new Button("Atualizar");
        atualizar.setOnAction(e -> {
            Produto selecaoProduto = tableView.getSelectionModel().getSelectedItem(); // pega o item selecionado
            if (selecaoProduto != null){
                selecaoProduto.setNome(nomeInput.getText());
                selecaoProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
                selecaoProduto.setPreco(Double.parseDouble(precoInput.getText().replace(",",".")));
                selecaoProduto.setStatus(statusComboBox.getValue());
                produtoDAO.atualizar(selecaoProduto);
                produtos.setAll(produtoDAO.listarTodos());
                limparCampos();
            }
        });

        Button excluir = new Button("Excluir");
        excluir.setOnAction(e -> {
            Produto selecaoProduto = tableView.getSelectionModel().getSelectedItem();
            if (selecaoProduto != null){
                produtoDAO.excluir(selecaoProduto.getId());
                produtos.setAll(produtoDAO.listarTodos());
                limparCampos();
            }
        });

        Button limpar = new Button("Limpar");
        limpar.setOnAction(e -> {
            limparCampos();
        });
        tableView = new TableView<>();
        tableView.setItems(produtos);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // ajusta as colunas

        List<TableColumn<Produto, ?>> columns =List.of(
                criarColuna("ID", "id"),
                criarColuna("Nome", "nome"),
                criarColuna("Quantidade", "quantidade"),
                criarColuna("Preço", "preco"),
                criarColuna("Status","status")
        );
        tableView.getColumns().addAll(columns);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeInput.setText(newSelection.getNome());
                quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
                precoInput.setText(String.valueOf(newSelection.getPreco()));
                statusComboBox.setValue(newSelection.getStatus());
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(adicionar,atualizar,excluir,limpar);

        vBox.getChildren().addAll(nomeProdutoHbox,quantidadeHbox,precoHbox,statusHbox,buttonBox,tableView);

         Scene scene = new Scene(vBox,800,600);
         scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // coloca a folha de estilo
         janela.setScene(scene);
         janela.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void  stop(){
        try {
            conexaoDB.close();
        }catch (SQLException e){
            System.err.println("Erro ao fechar conexão: "+ e.getMessage());
        }
    }



    private void limparCampos(){
        nomeInput.clear();
        quantidadeInput.clear();
        precoInput.clear();
        statusComboBox.setValue(null);
    }

    private TableColumn<Produto, String> criarColuna(String titulo, String propriedade){
        TableColumn<Produto, String> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(propriedade)); //definição da propriedade da coluna
        return col;
    }
}
