package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Curso;
import persistencia.CursoDAO;

@ManagedBean(name="cursoBean")
@RequestScoped
public class CursoBean {
    Curso curso = new Curso();
    CursoDAO dao = new CursoDAO();

    public Curso getProfessor() {
        return curso;
    }

    public void setProfessor(Curso curso) {
        this.curso = curso;
    }
    
    public Curso carrega(){
        return dao.carregar(curso.getId());
    }
}
