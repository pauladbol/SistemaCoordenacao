package persistencia;

import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO {
    private Session sessao;
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
    }
    
    public Usuario carregar(int matricula) {
        return (Usuario) sessao.load(Usuario.class, matricula);
    }
    
    public Usuario autentica(int matricula) {
        return (Usuario) sessao.createCriteria(Usuario.class)
                .add(Restrictions.eq("matricula", matricula))
                .uniqueResult();
    }
    
    public void terminaSessao(){
        sessao.close();
    }
    
}
