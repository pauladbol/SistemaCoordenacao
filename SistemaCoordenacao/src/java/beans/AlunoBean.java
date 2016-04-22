/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Aluno;
import persistencia.AlunoDAO;

@ManagedBean(name="alunoBean")
@RequestScoped
public class AlunoBean {
    Aluno aluno = new Aluno();
    AlunoDAO dao = new AlunoDAO();

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    public Aluno carrega(){
        return dao.carregar(aluno.getId());
    }
}
