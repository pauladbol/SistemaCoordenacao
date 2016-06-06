package persistencia;

import java.util.List;
import modelo.CRE;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 10070077
 */
public class CREDao {
    private Session sessao;
    
    public CREDao(){
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        try {
            tx = sessao.beginTransaction();
            tx.commit();
        }
        catch (Exception e) {
        if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            //sessao.close();
        }
    }
    
    public CRE carregar(int id) {
        return (CRE) sessao.load(CRE.class, id);
    }
    
    public CRE autentica(int id){
        return (CRE) sessao.createCriteria(CRE.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
    
    public void terminaSessao(){
        sessao.close();
    }
    
}
