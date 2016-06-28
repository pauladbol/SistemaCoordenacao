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
    
    //the gambiarra
    //achar jeito melhor de fazer
    //ver se o metodo comentado funciona
    public List<Usuario> listaProfessoresByDisciplina(String disciplina){
        ArrayList<Integer> nome_professores;
        List<Usuario> professores = new ArrayList<>();
        
        //pega todos id de prof por disciplina
        nome_professores = (ArrayList<Integer>) sessao.createSQLQuery(
                "select professor from disciplina where nome = ':disciplina'")
                .list();
        
        //pra cada id, adiciona o usuario 
        for(int prof_id : nome_professores){
            professores.add((Usuario) sessao.createCriteria(Usuario.class)
                .add(Restrictions.eq("id", prof_id)));
        }
        return professores;
    }    
    
    @PreDestroy
    public void terminaSessao(){
        sessao.close();
    }
    
}