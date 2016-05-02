package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;
import persistencia.UsuarioDAO;

/**
 *
 * @author brunof
 */

@ManagedBean
@SessionScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    UsuarioDAO dao = new UsuarioDAO();
    
    public void setUsuario(Usuario usuario){
        //git test
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public Usuario carrega(){
        return dao.carregar(usuario.getMatricula());
    }
    
    public void autentica(){
        
    }
}
