/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import dados.entidades.Ator;
import dados.entidades.Filme;
import dados.entidades.Genero;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import util.JPAUtil;

/**
 *
 * @author lusst
 */
public class PipularAtoresGenerosFilmes {
    
    public static void main(String[] args) {
        
        //Pegando o gerenciador de acesso ao BD
        EntityManager gerenciador = JPAUtil.getGerenciador();
        
        //Iniciar a transação
        gerenciador.getTransaction().begin();
        
        //Criando atores
        Ator a1 = new Ator("Luciano Soares");
        Ator a2 = new Ator("David Gonçalves");
        Ator a3 = new Ator("Carla Rafaela");
        Ator a4 = new Ator("Samuel Gomes");
        Ator a5 = new Ator("Samuel Santana");
        
        gerenciador.persist(a1);
        gerenciador.persist(a2);
        gerenciador.persist(a3);
        gerenciador.persist(a4);
        gerenciador.persist(a5);
        
        Genero g1 = new Genero();
        g1.setNome("Drama");
        Genero g2 = new Genero();
        g2.setNome("Romance");
        
        gerenciador.persist(g1);
        gerenciador.persist(g2);
        
        Filme f1 = new Filme("Les Miserables", LocalDate.now(), new BigDecimal("1111"), g1);
        Filme f2 = new Filme("Les Hombres", LocalDate.now(), new BigDecimal("2222"), g2);
        
        gerenciador.persist(f1);
        gerenciador.persist(f2);
        
        //Finalizo a transação
        gerenciador.getTransaction().commit();
        
        //Fechar o gerenciador
        gerenciador.close();
    }
    
}
