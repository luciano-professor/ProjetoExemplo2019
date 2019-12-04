package dados.entidades;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Filme {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    private LocalDate dataDeLancamento;
    private BigDecimal arrecadacao;
    
    @ManyToOne(optional=false)
    private Genero genero;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ator> atores = new HashSet<Ator>();
    
    //Construtor vazio da JPA (OBRIGATÓRIO)
    public Filme(){}
    
    //Construtor usado na hora de criar um novo filme
    public Filme(String n, LocalDate dl, BigDecimal a, Genero g){
        setNome(n);
        setDataDeLancamento(dl);
        setArrecadacao(a);
        setGenero(g);
    }
    
    public String getLancamentoFormatado(){
        
        
        DateTimeFormatter formatador = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM);
        
        String formatado = dataDeLancamento.format(formatador);
        return formatado;
    }
    
    

    public Set<Ator> getAtores() {
        return atores;
    }

    public void setAtores(Set<Ator> atores) {
        this.atores = atores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    public BigDecimal getArrecadacao() {
        return arrecadacao;
    }
    
    /**
     * Para retornar uma String com o valor da arrecadaco
     * formatada no formato parecido com dinheiro
     * @return 
     */
    public String getArrecadacaoFormatada(){
        DecimalFormat formatador = new DecimalFormat("#0.00");
        return formatador.format(arrecadacao);
    }

    public void setArrecadacao(BigDecimal arrecadacao) {
        this.arrecadacao = arrecadacao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public String toString(){
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Filme other = (Filme) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
