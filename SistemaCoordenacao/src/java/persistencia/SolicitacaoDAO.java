/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Solicitacao;
import org.hibernate.Session;

public class SolicitacaoDAO {
    private Session sessao;
    
    public SolicitacaoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Solicitacao carregar(int id) {
        return (Solicitacao) sessao.load(Solicitacao.class, id);
    }
    
    public void salvar(Solicitacao solicitacao) {
        sessao.saveOrUpdate(solicitacao);
    }
}
