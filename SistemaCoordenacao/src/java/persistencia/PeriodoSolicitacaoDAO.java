/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.PeriodoSolicitacao;
import org.hibernate.Session;

/**
 *
 * @author 10070128
 */
public class PeriodoSolicitacaoDAO {
    
private final Session sessao;
    
    public PeriodoSolicitacaoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public PeriodoSolicitacao buscar() {
        return (PeriodoSolicitacao) sessao
            .createQuery("select * from periodosolicitacao where estado = 'A'").uniqueResult();
    }
    
    public void criar(PeriodoSolicitacao p){
        sessao.save(p);
    }
//create periodo solicitacao
    
    //find periodo solicitacao
}
