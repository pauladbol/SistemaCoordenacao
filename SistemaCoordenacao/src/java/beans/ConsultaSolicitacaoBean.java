/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Solicitacao;
import persistencia.SolicitacaoDAO;

/**
 *
 * @author 10070128
 */
@ManagedBean
@RequestScoped
public class ConsultaSolicitacaoBean {
    final private SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO();
    final private List<Solicitacao> solicitacoes;
    
    public ConsultaSolicitacaoBean() {
        solicitacoes = solicitacaoDAO.buscarTodas();
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }
    
    public void detalhesSolicitacao(Solicitacao solicitacao) {
        
    }
    
    public void encaminharSolicitacao(Solicitacao solicitacao) {
        
    }
}
