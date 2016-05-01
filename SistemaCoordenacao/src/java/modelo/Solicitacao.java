/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="solicitacao")
public class Solicitacao {
    @Id
    @GeneratedValue
    private int id;
    private int protocolo;
    private String tipo;
    private String estado;
    private String observacao;
    private String jutificativa;
    private String nome_disciplina;
    
//    @ManyToOne
//    @JoinColumn(name="disciplina")
//    private Disciplina disciplina;
  
//    @ManyToOne
//    @JoinColumn(name="usuario")
//    private Usuario usuario;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getJutificativa() {
        return jutificativa;
    }

    public void setJutificativa(String justificativa) {
        this.jutificativa = justificativa;
    }

    
//    public Disciplina getDisciplina() {
//        return disciplina;
//    }
//
//    public void setDisciplina(Disciplina disciplina) {
//        this.disciplina = disciplina;
//    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }
    
}
