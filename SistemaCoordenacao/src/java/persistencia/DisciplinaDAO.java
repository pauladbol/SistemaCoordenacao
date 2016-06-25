package persistencia;

import java.util.List;
import modelo.Disciplina;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

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
    
    public void salvar(Disciplina disciplina) {
        sessao.saveOrUpdate(disciplina);
    }
}
