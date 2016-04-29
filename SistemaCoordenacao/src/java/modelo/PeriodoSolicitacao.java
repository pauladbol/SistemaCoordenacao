/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import persistencia.PeriodoSolicitacaoDAO;

/**
 *
 * @author 10070128
 */
@Entity
@Table(name="periodosolicitacao")
public class PeriodoSolicitacao implements Serializable {
    
    @Id
    @GeneratedValue
    private int id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="data_inicio")
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="data_fim")
    private Date dataFim;
    private boolean estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public static void main(String[] args) {
        PeriodoSolicitacaoDAO dao = new PeriodoSolicitacaoDAO();
        PeriodoSolicitacao periodo = new PeriodoSolicitacao();
        
        periodo.setDataInicio(new Date());
        periodo.setDataFim(new Date());
        periodo.setEstado(true);
        dao.criar(periodo);
    }
}
