package servicos;

import dados.daos.FilmeDAO;
import dados.dto.FilmeAtor;
import dados.entidades.Ator;
import dados.entidades.Filme;
import java.util.List;

public class FilmeServico {
    
    //Atributo para representar a camada de dados
    private FilmeDAO dao = new FilmeDAO();
    
    /**
     * Solicita a camada DAO para buscar os atores
     * cadastrados
     * @return 
     */
    public List<Filme> listar(){
        
        //Qualquer regra de negócio (se aplicável)
        
        //Pedir a DAO para listar e retornar
        return dao.listar();
        
    }
    
    /**
     * Recebe um filme e manda para a camada DAO salvar
     * no BD
     */
    public void salvar(Filme f){
        
        //Qualquer regra de negócio (se aplicável)
        
        //Manda a camada DAO salvar no BD
        dao.salvar(f);
        
    }
    
    /**
     * Recebe um filme e manda para a camada DAO atualizar 
     */
    public void editar(Filme f){
        
        //Qualquer regra de negócio (se aplicável)
        
        
        //Mandar a DAO atualizar os dados no BD
        dao.editar(f);
        
    }
    
    /**
     *  Recebe um filme para passar para a DAO excluir no BD
     */
    public void excluir(Filme f){
        //Qualquer regra de negócio (se aplicável)
        
        //Mandar para a DAO excluir
        dao.excluir(f);
    }
    
    public List<Filme> buscarPeloNome(String nome){
        
        //Qualquer regra de negócio (se aplicável)
        
        //Mandar para a DAO buscar os filmes pelo nome
        return dao.buscarPeloNome(nome);
    }
    
    /**
     * Busca uma lista de FilmeAtor (classe de Projeção que mostra uma relação entre um
     * filme e um ator) pelo nome do filme ou pelo nome do ator
     * 
     */
    public List<FilmeAtor> buscarFilmesAtoresPeloNomeFilmeOuNomeAtor(String nomeFilme) {
        
        return dao.buscarFilmesAtoresPeloNomeFilmeOuNomeAtor(nomeFilme);
        
    }

    public void incluirAtorNoFilme(Filme filme, Ator ator) {
    
        //Mandar a DAO atualizar os dados no BD
        dao.incluirAtorNoFilme(filme, ator);
    }

    public void removerAtorDoFilme(FilmeAtor filmeAtor) {
    
        //Manda a camada DAO remover o ator do filme
        dao.removerAtorDoFilme(filmeAtor);
        
    }
    
}
