package persistencia;

import javax.annotation.PreDestroy;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO {
    private Session sessao;
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
 
    }
    
    public Usuario carregar(int matricula) {
        return (Usuario) sessao.load(Usuario.class, matricula);
    }
    
    public Usuario autentica(int matricula, String tipo){
        return (Usuario) sessao.createCriteria(Usuario.class)
                .add(Restrictions.eq("matricula", matricula))
                .add(Restrictions.eq("tipo", tipo))
                .uniqueResult();
    }
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}