package persistencia;

import javax.annotation.PreDestroy;
import modelo.CRE;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CREDao {
    private Session sessao;
    
    public CREDao(){
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public CRE carregar(int id) {
        return (CRE) sessao.load(CRE.class, id);
    }
    
    public CRE autentica(int matricula){
        return (CRE) sessao.createCriteria(CRE.class)
                .add(Restrictions.eq("matricula", matricula))
                .uniqueResult();
    }
    
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}
