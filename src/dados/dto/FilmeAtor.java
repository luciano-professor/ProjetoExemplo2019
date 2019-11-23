package dados.dto;

import dados.entidades.Ator;
import dados.entidades.Filme;

/**
 *
 * Essa classe é uma Projeção da JPA que vai servir para
 * denotar a relação de um filme com um ator para mostrar na tabela
 * Esse tipo de classe normalmente é conhecida por DTO (Data Transfer Object)
 */
public class FilmeAtor {
    
    private Filme filme;
    private Ator ator;
    
    public FilmeAtor(Filme f, Ator a){
        setAtor(a);
        setFilme(f);
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Ator getAtor() {
        return ator;
    }

    public void setAtor(Ator ator) {
        this.ator = ator;
    }
    
    public String getNomeFilme(){
        return filme.getNome();
    }
    
    public String getNomeAtor(){
        return ator.getNome();
    }
    
    
}
