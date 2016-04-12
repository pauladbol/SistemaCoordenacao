package beans;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;
import persistencia.UsuarioDAO;

/**
 *
 * @author brunof
 */

@ManagedBean(name="beanUsuario")
@RequestScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    UsuarioDAO dao = new UsuarioDAO();
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public Usuario carrega(){
        return dao.carregar(usuario.getMatricula());
    }
    
    public void autentica(){
        usuario = dao.autentica(usuario.getMatricula());
    }
    
    @PreDestroy
    public void fechaSessao(){
        dao.terminaSessao();
    }
    
}
