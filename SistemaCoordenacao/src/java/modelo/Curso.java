/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="curso")
public class Curso implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    
    @ManyToMany()
    @JoinTable(name = "DisciplinaCurso", joinColumns = { 
			@JoinColumn(name = "curso_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "disciplina_id") })

    private Set<Disciplina> disciplinas = new HashSet<Disciplina>();
    
    @OneToOne
    @JoinColumn(name="coordenador")
    private Usuario coordenador;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
