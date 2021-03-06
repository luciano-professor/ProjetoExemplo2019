package teste;

import dados.entidades.Ator;
import javax.persistence.*;
import util.JPAUtil;

public class TestaAtor {
    
    public static void main(String[] args) {
        
        //Criando um objeto ator
        Ator a1 = new Ator();
        a1.setNome("David Gonçalves");
        
        Ator a2 = new Ator();
        a2.setNome("Petrônio Augusto");
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();
        
        //Mandando persistir o objeto
        gerenciador.persist(a1);
        gerenciador.persist(a2);
        
        //Finalizo a transação
        gerenciador.getTransaction().commit();
        
        //Fechar o gerenciador
        gerenciador.close();
        
    }
    
}
