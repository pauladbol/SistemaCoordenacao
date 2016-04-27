/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Disciplina;
import modelo.Solicitacao;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@RequestScoped
public class SolicitacaoBean {
    Solicitacao solicitacao = new Solicitacao();
    SolicitacaoDAO dao = new SolicitacaoDAO();
    Disciplina disciplina = new Disciplina();
    
    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    
    public void salvar() {
        dao.salvar(solicitacao);
    }
    
    public Solicitacao carrega(){
        return dao.carregar(solicitacao.getId());
    }
}
