package persistencia;

import modelo.Usuario;
import org.hibernate.Session;

public class UsuarioDAO {
    private Session sessao;
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Usuario carregar(int matricula) {
        return (Usuario) sessao.load(Usuario.class, matricula);
    }
    
    public void autentica(Usuario usuario) {
        
    }
    
}
