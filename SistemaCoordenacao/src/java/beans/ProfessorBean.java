package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Professor;
import persistencia.ProfessorDAO;

/**
 *
 * @author 10070077
 */

@ManagedBean(name="beanProfessor")
@SessionScoped
public class ProfessorBean {
    private List<Professor> lista_professores;
    Professor professor = new Professor();
    ProfessorDAO professorDAO = new ProfessorDAO();
    
    public List<Professor> listaProfessores(){
        //lista_professores = professorDAO.listarProfessores(null)
        return lista_professores;
    }
    
    public boolean autentica(int matricula){
        professor = professorDAO.autentica(matricula);
        
        if(professor==null){
            return false;
        }
        return true;
    }
}
