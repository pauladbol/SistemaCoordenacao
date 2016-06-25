package modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="disciplina")
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String ementa;
    @ManyToOne
    @JoinColumn(name = "professor")
    private Usuario professor;
    @ManyToOne
    @JoinColumn(name = "coordenador")
    private Usuario coordenador;


    @ManyToMany(mappedBy="disciplinas")
    final private Set<Curso> cursos = new HashSet();
    
    public int getId() {
        return this.id;
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

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Usuario getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Usuario coordenador) {
        this.coordenador = coordenador;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
