/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import modelo.Disciplina;
import persistencia.DisciplinaDAO;

public class DisciplinaBean {
    Disciplina disciplina = new Disciplina();
    DisciplinaDAO dao = new DisciplinaDAO();

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
