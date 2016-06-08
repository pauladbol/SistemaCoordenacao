package persistencia;

import modelo.Documento;
import modelo.Solicitacao;
import org.hibernate.Query;
import org.hibernate.Session;

public class DocumentoDAO {
    private Session sessao;
    
    public DocumentoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Documento carregar(int id) {
        return (Documento) sessao.load(Documento.class, id);
    }
    
    public void salvar(Documento documento) {
        sessao.saveOrUpdate(documento);
    }
    
    public Documento findBySolicitacao(Solicitacao solicitacao) {
        String sql = "from Documento where solicitacao = :id";
        Query query = sessao.createQuery(sql).setParameter("id", solicitacao.getId());
        return (Documento) query.uniqueResult();
    }
}
