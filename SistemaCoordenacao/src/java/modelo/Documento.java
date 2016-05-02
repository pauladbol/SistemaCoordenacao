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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="documento")
public class Documento {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private long tamanho;
    @Lob
    private byte[] arquivo;
    
    
    @ManyToOne
    @JoinColumn(name="solicitacao")
    private Solicitacao Solicitacao;
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public Solicitacao getSolicitacao() {
        return Solicitacao;
    }

    public void setSolicitacao(Solicitacao Solicitacao) {
        this.Solicitacao = Solicitacao;
    }
    
    
}