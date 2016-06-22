package persistencia;

import javax.annotation.PreDestroy;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO {
    private Session sessao;
    
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }
    
    public Usuario carregar(String matricula) {
        Transaction tx = sessao.beginTransaction();
        Usuario usuario;
        
        try{
            usuario = (Usuario) sessao.createCriteria(Usuario.class)
                    .add(Restrictions.eq("matricula", matricula))
                    .uniqueResult();
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
        
        return usuario;
        
    }
    
    public Usuario autentica(String matricula, String tipo){
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