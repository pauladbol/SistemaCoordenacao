package persistencia;

import java.util.List;
import javax.annotation.PreDestroy;
import modelo.Aluno;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 10070077
 */
public class AlunoDAO {
    private Session sessao;
    
    public AlunoDAO(){
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Aluno carregar(int id) {
        return (Aluno) sessao.load(Aluno.class, id);
    }
    
    public Aluno autentica(int matricula){
        /*
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
*/
        return (Aluno) sessao.createCriteria(Aluno.class)
                .add(Restrictions.eq("matricula", matricula))
                .uniqueResult();
    }
    
    
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}
