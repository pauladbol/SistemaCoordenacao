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
import org.hibernate.annotations.Type;

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
    
    @Column(name="data_inicio")
    @Type(type="date")
    private Date dataInicio;
    @Column(name="data_fim")
    @Type(type="date")
    private Date dataFim;

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
}
