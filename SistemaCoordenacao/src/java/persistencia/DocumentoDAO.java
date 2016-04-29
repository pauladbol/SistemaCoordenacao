/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Documento;
import org.hibernate.Session;

public class DocumentoDAO {
    private Session sessao;
    
    public DocumentoDAO() {
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Documento carregar(int id) {
        return (Documento) sessao.load(Documento.class, id);
    }
    
    public void salvar(Documento documento) {
        sessao.saveOrUpdate(documento);
    }
}
