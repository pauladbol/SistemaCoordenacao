package persistencia;

import java.util.List;
import javax.annotation.PreDestroy;
import modelo.Professor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 10070077
 */
public class ProfessorDAO {
    private Session sessao;
    
    public ProfessorDAO(){
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Professor>listarProfessores(String disciplina){
        //return sessao.createCriteria(Usuario.class).list();
        //where disciplina == disciplina no banco
        return sessao.createQuery("from Professores where disciplina = :disciplina").list();
    }
    
    public Professor carregar(int id) {
        return (Professor) sessao.load(Professor.class, id);
    }
    
    public Professor autentica(int matricula){
        return (Professor) sessao.createCriteria(Professor.class)
                .add(Restrictions.eq("matricula", matricula))
                .uniqueResult();
    }
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }

    public List<Professor> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
