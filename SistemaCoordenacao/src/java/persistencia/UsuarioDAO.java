package persistencia;

import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import modelo.Aluno;

public class UsuarioDAO {
    private Session sessao;
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
    }
    
    public Usuario carregar(int matricula) {
        return (Usuario) sessao.load(Usuario.class, matricula);
    }
    
    public Usuario autentica(int matricula, String tipoUsuario){
        
        return (Usuario) sessao.createCriteria(Usuario.class, "usuario")
                .createAlias("usuario."+tipoUsuario, tipoUsuario)
                .add(Restrictions.eq(tipoUsuario+".matricula", matricula))
                .add(Restrictions.eq("usuario.matricula", matricula))
                .uniqueResult();
    }
    
    public void terminaSessao(){
        sessao.close();
    }
    
}
