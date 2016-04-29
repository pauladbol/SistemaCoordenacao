/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Aluno;
import org.hibernate.Session;

public class AlunoDAO {
    private Session sessao;
    
    public AlunoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Aluno carregar(int id) {
        return (Aluno) sessao.load(Aluno.class, id);
    }
}
