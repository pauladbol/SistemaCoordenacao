/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.Date;
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
    
    public List<PeriodoSolicitacao> listar() {
        return (List<PeriodoSolicitacao>) this.sessao.createCriteria(PeriodoSolicitacao.class).list();
    }
    
    public void criar(PeriodoSolicitacao p){
        this.sessao.save(p);
    }
    
    public PeriodoSolicitacao findPeriodoValido(Date dataAtual){
        String sql = "FROM PeriodoSolicitacao p WHERE p.dataInicio <= :dataAtual AND p.dataFim >= :dataAtual";
        return (PeriodoSolicitacao) this.sessao.createQuery(sql)
                .setParameter("dataAtual", dataAtual)
                .uniqueResult();
    }
//create periodo solicitacao
    
    //find periodo solicitacao
    public static void main(String[] args) {
        String sql = "FROM PeriodoSolicitacao p WHERE p.dataInicio < :dataAtual AND p.dataFim > :dataAtual";
        Date dataAtual = new Date();
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        PeriodoSolicitacao p = (PeriodoSolicitacao) s.createQuery(sql)
                .setParameter("dataAtual", dataAtual)
                .uniqueResult();
        System.out.println(p);
    }
}
