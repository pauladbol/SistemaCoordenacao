package beans;

import modelo.Aluno;
import persistencia.AlunoDAO;

/**
 *
 * @author brunoscheltzke
 */
public class AlunoBean {
    AlunoDAO dao = new AlunoDAO();
    Aluno aluno = new Aluno();
    
    public boolean autentica(int matricula){
        aluno = dao.autentica(matricula);
        
        if(aluno==null){
            return false;
        }
        return true;
    }
    
    
    
}
