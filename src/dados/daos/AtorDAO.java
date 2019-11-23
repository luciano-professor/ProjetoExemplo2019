package dados.daos;

import dados.entidades.Ator;
import dados.entidades.Filme;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class AtorDAO {

    /**
     * Salvar o ator no BD
     */
    public void salvar(Ator a) {

        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //Mandar persistir o ator
        gerenciador.persist(a);

        //Commit
        gerenciador.getTransaction().commit();

    }

    /**
     * Retorna uma lista com todos os atores que estejam cadastrados no banco de
     * dados
     *
     * @return
     */
    public List<Ator> listar() {

        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Criando a consulta ao BD
        TypedQuery consulta = gerenciador.createQuery(
                "Select a from Ator a", Ator.class);

        //Retornar a lista de atores
        return consulta.getResultList();

    }

    /**
     * Salva as alterações no BD
     */
    public void editar(Ator a) {

        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //Mandar sincronizar as alterações 
        gerenciador.merge(a);
        
        //Commit na transação
        gerenciador.getTransaction().commit();

    }
    
    /**
     * Exclui o ator do BD
     */
    public void excluir(Ator a){
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();
        
        //Para excluir tem que dar o merge primeiro para 
        //sincronizar o ator do BD com o ator que foi
        //selecionado na tela
        a = gerenciador.merge(a);

        //Mandar sincronizar as alterações 
        gerenciador.remove(a);
        
        //Commit na transação
        gerenciador.getTransaction().commit();
        
    }

    public List<Ator> buscarPeloNomeNaoIncluidoNoFilme(String nome, Filme filme) {
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Criando a consulta ao BD
        TypedQuery<Ator> consulta = gerenciador.createQuery(
                "SELECT a FROM Ator a WHERE a NOT IN "
                        + "(SELECT a1 FROM Filme f INNER JOIN f.atores a1 WHERE f = :filme) "
                        + "AND a.nome LIKE :nome "
                        + "ORDER BY a.nome",
                Ator.class);

        consulta.setParameter("nome", nome + "%");
        consulta.setParameter("filme", filme);
       
        List<Ator> lista = consulta.getResultList();
        
        return lista;
        
    }

}
