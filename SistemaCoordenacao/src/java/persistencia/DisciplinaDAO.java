/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Disciplina;
import org.hibernate.Session;

public class DisciplinaDAO {
    private Session sessao;
    
    public DisciplinaDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Disciplina carregar(int id) {
        return (Disciplina) sessao.load(Disciplina.class, id);
    }
    
    public List<Disciplina> listar() {
        return sessao.createCriteria(Disciplina.class).list();
    } 
    
    public void salvar(Disciplina disciplina) {
        sessao.saveOrUpdate(disciplina);
    }
}
