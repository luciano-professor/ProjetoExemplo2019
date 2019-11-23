/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.filmeator;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import dados.dto.FilmeAtor;
import dados.entidades.Ator;
import dados.entidades.Filme;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.persistence.EntityManager;
import servicos.AtorServico;
import servicos.FilmeServico;
import util.AlertaUtil;
import util.JPAUtil;

/**
 * FXML Controller class
 *
 * @author lusst
 */
public class JanelaFilmeAtorController implements Initializable {

    @FXML
    private JFXTabPane tabPaneFilmeAtor;
    
    //Os tabs
    
    @FXML
    private Tab tabFilme;
    @FXML
    private Tab tabAtor;
    @FXML
    private Tab tabFilmeAtor;
    
    //Componentes da Tab Filme
    @FXML
    private TableView<Filme> tabelaFilme;
    @FXML
    private TableColumn colFilmeTabelaFilme;
    @FXML
    private JFXTextField textFieldFilme;
    
    //Componentes da Tab Ator
    @FXML
    private JFXTextField textFieldFilmeSelecionado;
    @FXML
    private TableView<Ator> tabelaAtor;
    @FXML
    private TableColumn colAtorTabelaAtor;
    @FXML
    private JFXTextField textFieldAtor;
    
    //Compoenentes da Tab Filme Ator
    @FXML
    private TableView<FilmeAtor> tabelaFilmeAtor;
    @FXML
    private TableColumn colFilmeTabelaFilmeAtor;
    @FXML
    private TableColumn colAtorTabelaFilmeAtor;
    @FXML
    private JFXTextField textFieldFilmeAtor;
    
    
    //Atributos que representam os Serviços que serão utilizados
    private FilmeServico filmeServico = new FilmeServico();
    private AtorServico atorServico = new AtorServico();
    
    //Atributos que representam os dados das tabelas
    private ObservableList<Filme> dadosTabelaFilme
            = FXCollections.observableArrayList();
    private ObservableList<Ator> dadosTabelaAtor
            = FXCollections.observableArrayList();
    private ObservableList<FilmeAtor> dadosTabelaFilmeAtor
            = FXCollections.observableArrayList();
    
    
    //Atributos que representam as linhas selecionadas nas tabelas
    private Filme filmeSelecionado; //na tabela filme
    private Ator atorSelecionado; //na tabela ator
    private FilmeAtor filmeAtorSelecionado; //na tabela filmeator
   


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //configurar as tabelas
        configurarTabelas();
        
    }   

    /**
     * Pesquisa uma lista de filmes pelo nome e os carrega na tabela de filmes
     *  
     */
    @FXML
    private void pesquisarFilmePeloNome(ActionEvent event) {
        
        //Limpar os dados da tabela filme
        dadosTabelaFilme.clear();
        
        //Pegando o nome que o usuario deseja pesquisar
        String nome = textFieldFilme.getText();
        
        
        //Solicitando a camada de servico a lista de filmes pelo nome
        List<Filme> filmes = filmeServico.buscarPeloNome(nome);
        
        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dadosTabelaFilme = FXCollections.observableArrayList(filmes);
        
        //Jogando os dados na tabela
        tabelaFilme.setItems(dadosTabelaFilme);
        
    }//pesquisarFilmePeloNome
    
    /**
     * Verifica se tem um filme selecionado e habilita e passa para
     *  o Tab de Ator
     */
    @FXML
    private void confirmarFilmeSelecionado(ActionEvent event) {
        
        //Pega o filme que estiver selecionado na tabela
        filmeSelecionado = tabelaFilme.getSelectionModel().getSelectedItem();
        
        //Se tem algum filme selecionado na tabela
        if(filmeSelecionado != null){
            
            //Habilita o Tab de Ator
            ativarTab(tabAtor, true);
                        
            //Seleciona automaticamente o Tab de Ator
            selecionarTab(tabAtor);
                        
            //Carrega o nome do filme no campo filme selecionado
            textFieldFilmeSelecionado.setText(filmeSelecionado.getNome());
            
        }else{
            AlertaUtil.mensagemErro("Selecione um Filme primeiro.");
        }
        
    }

    /**
     * Busca a lista de atores pelo nome que ainda não estejam incluídos 
     * no filme selecionado
     */
    @FXML
    private void pesquisarAtorPeloNome(ActionEvent event) {
        
        //Limpar os dados da tabela filme
        dadosTabelaAtor.clear();
        
        //Pegando o nome do ator que o usuario deseja pesquisar
        String nome = textFieldAtor.getText();
                
        //Solicitando a camada de servico a lista de atores pelo nome
        //e que não estejam ainda incluidos no filme selecionado
        List<Ator> atores = atorServico.buscarPeloNomeNaoIncluidoNoFilme(nome, filmeSelecionado);
        
        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dadosTabelaAtor = FXCollections.observableArrayList(atores);
        
        //Jogando os dados na tabela
        tabelaAtor.setItems(dadosTabelaAtor);
        
    }

    /**
     * Vai pegar o filme e ator selecionado e mandar
     *  a camada de servico cadastrar a associação entre
     * eles
     */
    @FXML
    private void incluirAtorSelecionadoNoFilme(ActionEvent event) {

        //Pega o filme que estiver selecionado na tabela
        atorSelecionado = tabelaAtor.getSelectionModel().getSelectedItem();
        
        //Se tem algum ator selecionado na tabela
        if(atorSelecionado != null){
                        
            //Manda para a camada de serviço incluir o ator no filme
            filmeServico.incluirAtorNoFilme(filmeSelecionado, atorSelecionado);
            
            //Mensagem de sucesso
            AlertaUtil.mensagemSucesso("Ator incluído com sucesso no filme.");
            
            //Retirar o ator da tabela            
            dadosTabelaAtor.remove(atorSelecionado);
            
            //Colocar o ator selecionado como null (pois ja foi incluido)
            atorSelecionado = null;
            
            
        }else{
            AlertaUtil.mensagemErro("Selecione um ator primeiro.");
        }
        
    }

    /**
     * Vai pesquisar uma lista de FilmeAtor (classe que combina um ator e
     * um filme para mostrar na tabela) para exibir na tabela. A busca pode 
     * ser pelo nome do filme ou nome do ator
     * 
     * @param event 
     */
    @FXML
    private void pesquisarPorFilmeOuAtor(ActionEvent event) {
        
        //Limpar os dados da tabela filmeator
        dadosTabelaFilmeAtor.clear();
        
        //Pegando o nome do filme ou ator que o usuario deseja pesquisar
        String nome = textFieldFilmeAtor.getText();
                
        //Solicitando a camada de servico a lista de atores pelo nome
        //e que não estejam ainda incluidos no filme selecionado
        List<FilmeAtor> filmeatores = filmeServico.buscarFilmesAtoresPeloNomeFilmeOuNomeAtor(nome);
        
        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dadosTabelaFilmeAtor = FXCollections.observableArrayList(filmeatores);
        
        //Jogando os dados na tabela
        tabelaFilmeAtor.setItems(dadosTabelaFilmeAtor);
        
    }

    @FXML
    private void removerAtorDoFilme(ActionEvent event) {
        
        //Pega o filme que estiver selecionado na tabela
        filmeAtorSelecionado = tabelaFilmeAtor.getSelectionModel().getSelectedItem();
        
        //Se tem algum filme selecionado na tabela
        if(filmeAtorSelecionado != null){
                                    
            //Carrega o nome do filme no campo filme selecionado
            filmeServico.removerAtorDoFilme(filmeAtorSelecionado);
            
            //Mensagem de sucesso
            AlertaUtil.mensagemSucesso("Ator removido do filme com sucesso no filme.");
            
            //Remove da tabela
            dadosTabelaFilmeAtor.remove(filmeAtorSelecionado);
            
            //Limpa a seleção
            filmeAtorSelecionado = null;
            
        }else{
            AlertaUtil.mensagemErro("Selecione um Filme/Ator primeiro.");
        }
        
    }
    
    
    /**
     * Configura as colunas de todas as tabelas
     */
    private void configurarTabelas(){
       
        //Dizer de onde a coluna vai pegar o valor para
        //exibir, basta dizer o nome do metodo get
        //que deve ser chamado para cada coluna
        // A string entre parênteses é o nome do atributo
        // que vc deseja chamar o get (quase sempre)
        
        //Tabela Filme
        colFilmeTabelaFilme.setCellValueFactory(
                new PropertyValueFactory("nome"));
        
        //Tabela Ator
        colAtorTabelaAtor.setCellValueFactory(
                new PropertyValueFactory("nome"));
        
        //Tabela Filme Ator
        colFilmeTabelaFilmeAtor.setCellValueFactory(
                new PropertyValueFactory("filme"));
        colAtorTabelaFilmeAtor.setCellValueFactory(
                new PropertyValueFactory("ator"));
        
    }

    

    /**
     * Método para selecionar uma Tab
     * no Tab Pane dessa janela
     *  
     */
    private void selecionarTab(Tab tab) {
        tabPaneFilmeAtor.getSelectionModel().select(tab);
    }
    
    /**
     * Método para ativar ou desativar uma tab
     */
    private void ativarTab(Tab tab, boolean ativar){
        tab.setDisable(!ativar);
    }

    /**
     * Esse método é chamado toda vez que um tab é seleciconado
     * Aqui ele vai limpar os dados das tabelas
     */
    @FXML
    private void mudouDeTab(Event event) {
        
        dadosTabelaAtor.clear();
        dadosTabelaFilme.clear();
        dadosTabelaFilmeAtor.clear();
        
    }
    
}
