/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.filme;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dados.entidades.Filme;
import dados.entidades.Genero;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servicos.FilmeServico;
import servicos.GeneroServico;
import util.AlertaUtil;

/**
 * FXML Controller class
 *
 * @author lusst
 */
public class JanelaFilmeController implements Initializable {

    @FXML
    private JFXTextField textFieldId;
    @FXML
    private JFXTextField textFieldNome;
    @FXML
    private JFXDatePicker dateLancamento;
    @FXML
    private JFXTextField textFieldArrecadacao;
    @FXML
    private JFXComboBox<Genero> comboGenero;
    @FXML
    private TableView<Filme> tabela;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colLancamento;
    @FXML
    private TableColumn colArrecadacao;
    @FXML
    private TableColumn colGenero;

    //Atributo que representa os dados para tabela
    private ObservableList<Filme> dados
            = FXCollections.observableArrayList();

    //Atributos para representar os servicos
    private FilmeServico filmeServico = new FilmeServico();
    private GeneroServico generoServico = new GeneroServico();

    //Atributo para representar o filme selecionado
    //na tabela para editar e excluir
    private Filme selecionado;
    @FXML
    private JFXTextField textFieldPesquisar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Configure a tabela
        configurarTabela();

        //Carregue a lista de atores na tabela
        listarFilmesTabela();

        //Carregar combo de genero
        listarGeneros();
    }

    /**
     * Fazendo configuração das colunas da tabela
     */
    private void configurarTabela() {

        //Dizer de onde a coluna vai pegar o valor para
        //exibir, basta dizer o nome do metodo get
        //que deve ser chamado para cada coluna
        // A string entre parênteses é o nome do atributo
        // que vc deseja chamar o get (quase sempre)
        //import javafx.scene.control.cell.PropertyValueFactory;
        colId.setCellValueFactory(
                new PropertyValueFactory("id"));
        colNome.setCellValueFactory(
                new PropertyValueFactory("nome"));
        colGenero.setCellValueFactory(
                new PropertyValueFactory("genero"));
        colArrecadacao.setCellValueFactory(
                new PropertyValueFactory("arrecadacao"));
        colLancamento.setCellValueFactory(
                new PropertyValueFactory("lancamentoFormatado"));

    }//configurarTabela

    /**
     * Responsável por carregar a lista de atores na tabela
     */
    private void listarFilmesTabela() {
        //Limpando quaisquer dados anteriores
        dados.clear();

        //Solicitando a camada de servico a lista de atores
        List<Filme> filmes = filmeServico.listar();

        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dados = FXCollections.observableArrayList(filmes);

        //Jogando os dados na tabela
        tabela.setItems(dados);

    }

    /**
     * Carrega o comboBox de generos no formulario
     */
    private void listarGeneros() {

        List<Genero> generos = generoServico.listar();

        comboGenero.setItems(FXCollections.observableArrayList(generos));

    }

    @FXML
    private void salvar(ActionEvent event) {

        //Verificar se está atualizando ou inserindo
        if (textFieldId.getText().isEmpty()) { //inserindo

            //Criando o objeto filme
            Filme f = new Filme(
                    textFieldNome.getText(),
                    dateLancamento.getValue(),
                    new BigDecimal(textFieldArrecadacao.getText()),
                    comboGenero.getValue()
            );

            //Mandando para a camada de serviço salvar
            filmeServico.salvar(f);

            //Exibindo mensagem
            AlertaUtil.mensagemSucesso("Filme salvo com sucesso!");

            //Carregando lista de filmes
            listarFilmesTabela();

        } else { //atualizando

            //Pegando a resposta da confirmacao do usuario
            Optional<ButtonType> btn = 
                AlertaUtil.mensagemDeConfirmacao("Deseja mesmo salvar as alterações?",
                      "EDITAR");
            
            //Se o botão OK foi pressionado
            if(btn.get() == ButtonType.OK){
                
                //Pegar os novos dados do formulário e
                //atualizar o filme
                selecionado.setNome(textFieldNome.getText());
                selecionado.setArrecadacao(new BigDecimal(textFieldArrecadacao.getText()));
                selecionado.setDataDeLancamento(dateLancamento.getValue());
                selecionado.setGenero(comboGenero.getValue());
                
                //Mandando para a camada de serviço salvar as alterações
                filmeServico.editar(selecionado);
                
                //Exibindo mensagem
                AlertaUtil.mensagemSucesso("Filme atualizado com sucesso!");
                
                //Carregando lista de filmes
                listarFilmesTabela();
                
            }
            
        }

        //Limpando o form
        limparCampos();
    }

    /**
     * Limpa os campos do formulário
     */
    private void limparCampos() {
        textFieldId.setText("");
        textFieldNome.setText("");
        textFieldArrecadacao.setText("");
        dateLancamento.setValue(null);
        comboGenero.setValue(null);
    }

    @FXML
    private void editar(ActionEvent event) {

        //Pegar o filme que foi selecionado na tabela 
        selecionado = tabela.getSelectionModel().getSelectedItem();

        //Se tem algum filme selecionado
        if (selecionado != null) {
            
            //Pega os dados do filme e joga no formulário
            textFieldId.setText(String.valueOf(selecionado.getId()));
            textFieldNome.setText(selecionado.getNome());
            textFieldArrecadacao.setText(selecionado.getArrecadacao().toString());
            dateLancamento.setValue(selecionado.getDataDeLancamento());
            comboGenero.setValue(selecionado.getGenero());
            
        }else{//não selecionou filme na tabela
            AlertaUtil.mensagemErro("Selecione um filme.");
        }

    }

    @FXML
    private void excluir(ActionEvent event) {
        
        //Pegar o filme que foi selecionado na tabela 
        selecionado = tabela.getSelectionModel().getSelectedItem();
        
        //Se tem algum filme selecionado
        if (selecionado != null) {
            
            //Pegando a resposta da confirmacao do usuario
            Optional<ButtonType> btn = 
                AlertaUtil.mensagemDeConfirmacao("Deseja mesmo excluir?",
                      "EXCLUIR");
            
             //Verificando se apertou o OK
            if(btn.get() == ButtonType.OK){
                
                //Manda para a camada de serviço excluir
                filmeServico.excluir(selecionado);
                
                //mostrar mensagem de sucesso
                AlertaUtil.mensagemSucesso("Filme excluído com sucesso");
                
                //Carregando lista de filmes
                listarFilmesTabela();
            }
            
        }
        
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        
        //Limpando quaisquer dados anteriores
        dados.clear();

        //Pegando o nome que a pessoa deseja pesquisar
        String nome = textFieldPesquisar.getText();
        
        //Solicitando a camada de servico a lista de atores
        List<Filme> filmes = filmeServico.buscarPeloNome(nome);

        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dados = FXCollections.observableArrayList(filmes);

        //Jogando os dados na tabela
        tabela.setItems(dados);
        
    }

}
