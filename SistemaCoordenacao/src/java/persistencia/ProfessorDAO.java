package persistencia;

import java.util.List;
import modelo.Usuario;
import org.hibernate.Session;

/**
 *
 * @author 10070077
 */
public class ProfessorDAO {
    private Session sessao;
    
    public ProfessorDAO(){
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
    }
    
    public List<Usuario>listarProfessores(String disciplina){
        //return sessao.createCriteria(Usuario.class).list();
        //where disciplina == disciplina no banco
        return sessao.createQuery("from Professores where disciplina = :disciplina").list();
    }
}
