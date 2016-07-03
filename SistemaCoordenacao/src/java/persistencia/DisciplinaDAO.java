package persistencia;

import java.util.List;
import modelo.Curso;
import modelo.Disciplina;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class DisciplinaDAO {
    private Session sessao;
    
    public DisciplinaDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Disciplina carregar(int id) {
        return (Disciplina) sessao.load(Disciplina.class, id);
    }
    
    public List<Disciplina> listar() {
        Query query = sessao.createQuery("FROM Disciplina");
        List<Disciplina> disciplinas = query.list();
        return disciplinas;
    } 
    
     public List<Disciplina> listarDisciplinas(Curso curso) {
        return this.sessao.createCriteria(Disciplina.class).
                add(Restrictions.eq("cursos", curso)).list();
    }
     
    public void salvar(Disciplina disciplina) {
        sessao.saveOrUpdate(disciplina);
    }
}
