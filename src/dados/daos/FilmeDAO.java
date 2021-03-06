package dados.daos;

import dados.dto.FilmeAtor;
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
    public void excluir(Filme f) {

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

    public List<Filme> buscarPeloNome(String nome) {

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

    public List<FilmeAtor> buscarFilmesAtoresPeloNomeFilmeOuNomeAtor(String nomeFilmeOuAtor) {
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Criando a consulta ao BD
        TypedQuery<FilmeAtor> consulta = gerenciador.createQuery(
                "SELECT new dados.dto.FilmeAtor(f, a) FROM Filme f JOIN f.atores a WHERE f.nome like :nomeFilme or a.nome like :nomeAtor",
                FilmeAtor.class);

        consulta.setParameter("nomeFilme", nomeFilmeOuAtor + "%");
        consulta.setParameter("nomeAtor", nomeFilmeOuAtor + "%");
       
        return consulta.getResultList();

    }
    
    public static void main(String[] args) {
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Criando a consulta ao BD
        TypedQuery<FilmeAtor> consulta = gerenciador.createQuery(
                "SELECT new dados.dto.FilmeAtor(f, a) FROM Filme f JOIN f.atores a where f.nome = 'Les' OR a.nome = 'Les'",
                FilmeAtor.class);

        System.out.println("Realizando a consulta");
        List<FilmeAtor> lista = consulta.getResultList();
        System.out.println("Teste");
        
        
        /*System.out.println("Tamanho da lista");
        System.out.println(lista.size());
        
        
        for(Filme f : lista){
            System.out.println(f.getNome());
            System.out.println(f.getId());
            System.out.println(f.getAtores().size());
        }*/
        
    }

    public void incluirAtorNoFilme(Filme filme, Ator ator) {
    
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //sincronizar com o BD para tornar o status managed
        filme = gerenciador.merge(filme);
        
        //Adicionar o ator
        filme.getAtores().add(ator);

        //Commit na transação
        gerenciador.getTransaction().commit();
        
    }

    public void removerAtorDoFilme(FilmeAtor filmeAtor) {
        
        Filme filme = filmeAtor.getFilme();
        Ator ator = filmeAtor.getAtor();
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();

        //Iniciar a transação
        gerenciador.getTransaction().begin();

        //sincronizar com o BD para tornar o status managed
        filme = gerenciador.merge(filme);
        ator = gerenciador.merge(ator);
        
        //Adicionar o ator
        filme.getAtores().remove(ator);

        //Commit na transação
        gerenciador.getTransaction().commit();
        
    }

}
