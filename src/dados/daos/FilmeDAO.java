
package dados.daos;

import dados.entidades.Ator;
import dados.entidades.Filme;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;


public class FilmeDAO {
    
    /**
     * Retorna uma lista com todos os atores que estejam cadastrados no banco de
     * dados
     *
     * @return
     */
    public List<Filme> listar() {

        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Criando a consulta ao BD
        TypedQuery consulta = gerenciador.createQuery(
                "Select f from Filme f", Filme.class);

        //Retornar a lista de atores
        return consulta.getResultList();

    }

    /**
     * Manda salvar um filme no BD
     *  
     */
    public void salvar(Filme f) {
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //Mandar persistir o ator
        gerenciador.persist(f);

        //Commit
        gerenciador.getTransaction().commit();
        
    }
    
    /**
     * Salva as alterações no BD
     */
    public void editar(Filme f) {

        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //Mandar sincronizar as alterações 
        gerenciador.merge(f);
        
        //Commit na transação
        gerenciador.getTransaction().commit();

    }
    
    /**
     * Exclui o filme do BD
     */
    public void excluir(Filme f){
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();
        
        //Para excluir tem que dar o merge primeiro para 
        //sincronizar o ator do BD com o ator que foi
        //selecionado na tela
        f = gerenciador.merge(f);

        //Mandar sincronizar as alterações 
        gerenciador.remove(f);
        
        //Commit na transação
        gerenciador.getTransaction().commit();
        
    }
    
    
    public List<Filme> buscarPeloNome(String nome){
        
       //Pegando o gerenciador de acesso ao BD
       EntityManager gerenciador = JPAUtil.getGerenciador(); 
       
       //Criando a consulta ao BD
       TypedQuery<Filme> consulta = gerenciador.createQuery(
                "Select f from Filme f where f.nome like :nome", 
               Filme.class);
       
       //Substituindo o parametro :nome pelo valor da variavel n
       consulta.setParameter("nome", nome + "%");
       
       //Retornar os dados
       return consulta.getResultList();
        
    }
    
    
}
