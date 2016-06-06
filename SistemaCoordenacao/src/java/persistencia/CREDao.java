package persistencia;

import java.util.List;
import javax.annotation.PreDestroy;
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
    }
    
    public CRE carregar(int id) {
        return (CRE) sessao.load(CRE.class, id);
    }
    
    public CRE autentica(int id){
        return (CRE) sessao.createCriteria(CRE.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
    
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}
