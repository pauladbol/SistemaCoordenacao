/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Disciplina;
import persistencia.DisciplinaDAO;

@ManagedBean(name="disciplinaBean")
@RequestScoped
public class DisciplinaBean {
    Disciplina disciplina = new Disciplina();
    DisciplinaDAO dao = new DisciplinaDAO();
    private List<Disciplina> listaDisciplinas;

    public DisciplinaBean() {
        listaDisciplinas = dao.listar();
    }
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    public Disciplina carrega(){
        return dao.carregar(disciplina.getId());
    }
    
    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }
}
