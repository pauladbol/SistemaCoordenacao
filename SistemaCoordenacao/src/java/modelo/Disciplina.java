/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="disciplina")
public class Disciplina {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String ementa;

    @ManyToMany(mappedBy="disciplinas")
    private Set<Curso> cursos = new HashSet<Curso>();
    
    
    @OneToOne(mappedBy="solicitacao")
    @JoinColumn(name="disciplina")
    private Solicitacao solicitacao;
    
    public int getId() {
        return id;
    } 

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }
    
}
