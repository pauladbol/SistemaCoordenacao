/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Solicitacao;
import org.hibernate.Session;

public class SolicitacaoDAO {
    final private Session sessao;
    
    public SolicitacaoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Solicitacao> listar() {
        return (List<Solicitacao>) this.sessao.createCriteria(Solicitacao.class).list();
    }
    
    public Solicitacao carregar(int id) {
        return (Solicitacao) this.sessao.load(Solicitacao.class, id);
    }
    
    public void salvar(Solicitacao solicitacao) {
        this.sessao.saveOrUpdate(solicitacao);
    }
}
