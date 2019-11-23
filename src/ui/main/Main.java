package ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.JPAUtil;

/**
 *
 * @author lusst
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //Iniciando a JPA pela primeira vez quando a aplicação inicia
        //Vai melhorar a performance do primeiro carregamento de dados
        JPAUtil.getGerenciador();
        
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        
        Scene scene = new Scene(root);
        
        //Icone da janela
        stage.getIcons().add(new Image("/ui/imagens/icone.png"));
        
        //Abrir maximizado
        stage.setMaximized(true);
        
        
        
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
