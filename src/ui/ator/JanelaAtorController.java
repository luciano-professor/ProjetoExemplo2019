package ui.ator;

import com.jfoenix.controls.JFXTextField;
import dados.entidades.Ator;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servicos.AtorServico;
import util.AlertaUtil;

/**
 * FXML Controller class
 *
 * @author lusst
 */
public class JanelaAtorController implements Initializable {

    @FXML
    private JFXTextField textFieldId;

    @FXML
    private JFXTextField textFieldNome;

    //Atributo para representar o servico
    private AtorServico servico = new AtorServico();

    @FXML
    private TableView<Ator> tabela;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNome;

    //Atributo que representa os dados para tabela
    private ObservableList<Ator> dados
            = FXCollections.observableArrayList();

    //Atributo que vai armazenar qual o ator 
    //foi selecionado na tabela
    private Ator selecionado;

    /**
     * Initializes the controller class. Tudo que é feito ao inicializar a
     * Janela
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Configure a tabela
        configurarTabela();

        //Carregue a lista de atores na tabela
        listarAtoresTabela();

    }

    @FXML
    private void salvar(ActionEvent event) {
        
        //Verificar se está atualizando ou inserindo
        if(textFieldId.getText().isEmpty()){ //inserindo
            //Pega os dados do fomulário
            //e cria um objeto ator
            Ator a = new Ator(textFieldNome.getText());

            //Mandar o ator para a camada de servico
            servico.salvar(a);
            
            //Exibindo mensagem
            AlertaUtil.mensagemSucesso("Ator salvo com sucesso!");
            
            //Chama o metodo para atualizar a tabela
            listarAtoresTabela();
            
        }else{ //atualizando o ator
           
            //Pegando a resposta da confirmacao do usuario
            Optional<ButtonType> btn = 
                AlertaUtil.mensagemDeConfirmacao("Deseja mesmo salvar as alterações?",
                      "EDITAR");
            
            //Se o botão OK foi pressionado
            if(btn.get() == ButtonType.OK){
                //Pegar os novos dados do formulário e
                //atualizar o meu ator
                selecionado.setNome(textFieldNome.getText());
                
                //Mandando pra camada de serviço salvar as alterações
                servico.editar(selecionado);
                
                //Exibindo mensagem
                AlertaUtil.mensagemSucesso("Ator atualizado com sucesso!"); 
                
                //Chama o metodo para atualizar a tabela
                 listarAtoresTabela();
            }
            
        }

        //Limpando os campos do form
        limparCampos();
        
    }
    
    /**
     * Limpa os campos do formulário
     */
    private void limparCampos(){
        //Limpando o form
        textFieldId.setText("");
        textFieldNome.setText("");
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

    }//configurarTabela

    /**
     * Responsável por carregar a lista de atores na tabela
     */
    private void listarAtoresTabela() {
        //Limpando quaisquer dados anteriores
        dados.clear();

        //Solicitando a camada de servico a lista de atores
        List<Ator> atores = servico.listar();

        //Transformar a lista de atores no formato que a tabela
        //do JavaFX aceita
        dados = FXCollections.observableArrayList(atores);

        //Jogando os dados na tabela
        tabela.setItems(dados);

    }

    @FXML
    private void editar(ActionEvent event) {

        //Pegar o ator que foi selecionado na tabela
        selecionado = tabela.getSelectionModel()
                .getSelectedItem();

        //Se tem algum ator selecionado
        if (selecionado != null) { //tem ator selecionado
            //Pegar os dados do ator e jogar nos campos do
            //formulario
            textFieldId.setText(
                    String.valueOf( selecionado.getId() ) );
            textFieldNome.setText( selecionado.getNome() );    
        }else{ //não tem ator selecionado na tabela
            AlertaUtil.mensagemErro("Selecione um ator.");
        }

    }
    
    

    @FXML
    private void excluir(ActionEvent event) {
        
        //Pegar o ator que foi selecionado na tabela
        selecionado = tabela.getSelectionModel()
                .getSelectedItem();
        
        //Verifico se tem ator selecionado
        if(selecionado != null){ //existe ator selecionado
            
            //Pegando a resposta da confirmacao do usuario
            Optional<ButtonType> btn = 
                AlertaUtil.mensagemDeConfirmacao("Deseja mesmo excluir?",
                      "EXCLUIR");
            
            //Verificando se apertou o OK
            if(btn.get() == ButtonType.OK){
                
                //Manda para a camada de serviço excluir
                servico.excluir(selecionado);
                
                //mostrar mensagem de sucesso
                AlertaUtil.mensagemSucesso("Ator excluído com sucesso");
                
                //Atualizar a tabela
                listarAtoresTabela();              
                
            }
            
            
            
        }else{
            AlertaUtil.mensagemErro("Selecione um ator.");
        }
        
    }

}
