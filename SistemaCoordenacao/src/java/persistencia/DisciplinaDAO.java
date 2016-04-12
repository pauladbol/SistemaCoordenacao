/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Disciplina;
import org.hibernate.Session;

public class DisciplinaDAO {
    private Session sessao;
    
    public DisciplinaDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
    }
    
    public Disciplina carregar(int id) {
        return (Disciplina) sessao.load(Disciplina.class, id);
    }
}
