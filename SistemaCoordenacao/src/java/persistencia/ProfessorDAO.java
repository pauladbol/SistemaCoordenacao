/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Professor;
import org.hibernate.Session;

public class ProfessorDAO {
    private Session sessao;
    
    public ProfessorDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
    }
    
    public Professor carregar(int id) {
        return (Professor) sessao.load(Professor.class, id);
    }
}