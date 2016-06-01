/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Solicitacao;
import persistencia.SolicitacaoDAO;

/**
 *
 * @author 10070128
 */
@ManagedBean
@SessionScoped
public class ConsultaSolicitacaoBean {
    final private SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO();
    final private List<Solicitacao> solicitacoes;
    private Solicitacao solicitacao;
    
    public ConsultaSolicitacaoBean() {
        solicitacoes = solicitacaoDAO.buscarTodas();
    }
    
    public String detalhesSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
        return "detalheSolicitacao";
    }
    
    public void encaminharSolicitacao(Solicitacao solicitacao) {
        
    }
    
    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }
    
}
