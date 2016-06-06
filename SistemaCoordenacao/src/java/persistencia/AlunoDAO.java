package persistencia;

import java.util.List;
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
    
    public Aluno carregar(int id) {
        return (Aluno) sessao.load(Aluno.class, id);
    }
    
    public Aluno autentica(int id){
        return (Aluno) sessao.createCriteria(Aluno.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
    
    public void terminaSessao(){
        sessao.close();
    }
    
}
