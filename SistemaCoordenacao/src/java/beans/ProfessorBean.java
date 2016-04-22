/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Professor;
import persistencia.ProfessorDAO;

@ManagedBean(name="professorBean")
@RequestScoped
public class ProfessorBean {
    Professor professor = new Professor();
    ProfessorDAO dao = new ProfessorDAO();

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    public Professor carrega(){
        return dao.carregar(professor.getId());
    }
}
