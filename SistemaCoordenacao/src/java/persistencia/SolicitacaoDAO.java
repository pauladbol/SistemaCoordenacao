package persistencia;

import com.sun.xml.xsom.impl.RestrictionSimpleTypeImpl;
import java.util.List;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Solicitacao;
import modelo.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.DisjunctionFragment;

public class SolicitacaoDAO {
    final private Session sessao;
    
    public SolicitacaoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Solicitacao> listar(Usuario usuario) {
        return this.sessao.createCriteria(Solicitacao.class)
                .add(Restrictions.eq("usuario", usuario)).list();
    }
    
    public List<Solicitacao> listar(String... status){
        Criteria criteria = this.sessao.createCriteria(Solicitacao.class);
        Disjunction d = Restrictions.disjunction();
        for(String s : status){
            d.add(Restrictions.eq("estado", s));
        }
        criteria.add(d);
        return criteria.list();
    }
    
    public List<Solicitacao> listar(Usuario usuario, String... status){
        Criteria criteria = this.sessao.createCriteria(Solicitacao.class);
        criteria.add(Restrictions.eq("professor", usuario));
        Disjunction d = Restrictions.disjunction();
        for(String s : status){
            d.add(Restrictions.eq("estado", s));
        }
        criteria.add(d);
        return criteria.list();
    }
    
    public Solicitacao carregar(int id) {
        return (Solicitacao) this.sessao.load(Solicitacao.class, id);
    }
    
    public void salvar(Solicitacao solicitacao) {
        this.sessao.saveOrUpdate(solicitacao);
    }
    
    public int findUltimoId(){
        String sql = "SELECT id FROM Solicitacao ORDER BY id DESC";
        Query query = sessao.createQuery(sql);
        query.setMaxResults(1);
        if (query.uniqueResult() != null) {
            return (int) query.uniqueResult();
        }
        return 0;
    }
    
    public List<Disciplina> listarDisciplinas(Curso curso) {
        return this.sessao.createCriteria(Disciplina.class).
                add(Restrictions.eq("curso", curso)).list();
    }
    
    public static void main(String[] args) {
//        Session s = HibernateUtil.getSessionFactory().openSession();
//        s.beginTransaction();
//        
//        Disciplina d = (Disciplina) s.load(Disciplina.class, 2);
//        
//        Solicitacao soli = new Solicitacao();
//        soli.setDisciplina(d);
//        
//        s.save(soli);
//        
//        s.getTransaction().commit();
//        s.close();
//        System.out.println("fim!");
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        String sql = "SELECT id FROM Solicitacao ORDER BY id DESC";
        Query query = s.createQuery(sql);
        query.setMaxResults(1);
        int i = (int) query.uniqueResult();
        System.out.println(i);
    }
}
