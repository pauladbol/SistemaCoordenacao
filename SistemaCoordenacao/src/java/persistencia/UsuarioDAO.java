package persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO {
    private Session sessao;
    Usuario usuario;
    
    
    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }
    
    public Usuario logar(String matricula) {
        Transaction tx = sessao.beginTransaction();
        
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
    
    public List<Usuario> listaProfessores(){
        List<Usuario> listaProf = (List<Usuario>) (Usuario) sessao.createCriteria(Usuario.class).list();
        return listaProf;
    }
    
    public List<Usuario> listaCoordenadores(){
        return (List<Usuario>) (Usuario) sessao.createCriteria(Usuario.class)
                .add(Restrictions.eq("coordenador", true))
                .list();
    }
    
    public List<Usuario> listaProfessoresByDisciplina(int disciplina){
        return sessao.createSQLQuery("select * from usuario where id in (select disciplina_professor.id_professor from disciplina_professor where disciplina_professor.id_Disciplina = :disciplina);")
                .list();
        }  
    
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}