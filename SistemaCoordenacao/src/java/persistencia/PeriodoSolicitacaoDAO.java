/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.PeriodoSolicitacao;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 10070128
 */
public class PeriodoSolicitacaoDAO {
    
private final Session sessao;
    
    public PeriodoSolicitacaoDAO() {
        this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<PeriodoSolicitacao> buscar() {
        return (List<PeriodoSolicitacao>) this.sessao.createCriteria(PeriodoSolicitacao.class).list();
    }
    
    public void criar(PeriodoSolicitacao p){
        this.sessao.save(p);
    }
//create periodo solicitacao
    
    //find periodo solicitacao
}
