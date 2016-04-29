/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Curso;
import org.hibernate.Session;

public class CursoDAO {
    private Session sessao;
    
    public CursoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Curso carregar(int id) {
        return (Curso) sessao.load(Curso.class, id);
    }
}
