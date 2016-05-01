/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Type;
import persistencia.PeriodoSolicitacaoDAO;

/**
 *
 * @author 10070128
 */
@Entity
@Table(name="periodosolicitacao")
public class PeriodoSolicitacao implements Serializable {

    public PeriodoSolicitacao() {
        this.estado = true;
    }
    
    @Id
    @GeneratedValue
    private int id;
    
    @Column(name="data_inicio")
    @Type(type="timestamp")
    private Date dataInicio;
    @Column(name="data_fim")
    @Type(type="timestamp")
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
}
