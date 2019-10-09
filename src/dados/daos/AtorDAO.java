package dados.daos;

import dados.entidades.Ator;
import javax.persistence.EntityManager;
import util.JPAUtil;

public class AtorDAO {
    
    /**
     * Salvar o ator no BD
     */
    public void salvar(Ator a){
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();
        
        //Mandar persistir o ator
        gerenciador.persist(a);
        
        //Commit
        gerenciador.getTransaction().commit();
        
    }
    
}
