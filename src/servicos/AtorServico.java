package servicos;

import dados.daos.AtorDAO;
import dados.entidades.Ator;
import dados.entidades.Filme;
import java.util.List;

public class AtorServico {
    
    //Atributo para representar a camada de dados
    private AtorDAO dao = new AtorDAO();
    
    public void salvar(Ator a){
        //Fazer qualquer regra de negócio
        
        //Mandar o ator para a camada de dados
        //para ser salvo no banco de dados
        dao.salvar(a);
    }
    
    /**
     * Solicita a camada DAO para buscar os atores
     * cadastrados
     * @return 
     */
    public List<Ator> listar(){
        
        //Qualquer regra de negócio (se aplicável)
        
        //Pedir a DAO para listar e retornar
        return dao.listar();
        
    }
    
    /**
     * Recebe um ator e manda para a camada DAO atualizar 
     */
    public void editar(Ator a){
        
        //Qualquer regra de negócio (se aplicável)
        
        
        //Mandar a DAO atualizar os dados no BD
        dao.editar(a);
        
    }
    
    /**
     *  Recebe um ator para passar para a DAO excluir no BD
     */
    public void excluir(Ator a){
        
        //Qualquer regra de negócio (se aplicável)
        
        //Mandar para a DAO excluir
        dao.excluir(a);
    }

    /**
     * Busca a lista de atores pelo nome e que não estejam incluidos no
     *  filme selecionado
     */
    public List<Ator> buscarPeloNomeNaoIncluidoNoFilme(String nome, Filme filme) {
        
        //Qualquer regra de negócio (se aplicável)
        
        //Mandar para a DAO buscar
        return dao.buscarPeloNomeNaoIncluidoNoFilme(nome, filme);
        
    }
    
}
