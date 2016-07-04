package beans;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import org.hibernate.Session;
import persistencia.UsuarioDAO;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
    Usuario usuario;
    private String matricula;
    
    public String logar(){
        UsuarioDAO dao = new UsuarioDAO();
        
        this.usuario = dao.logar(getMatricula());
        
        if(this.usuario==null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Matricula nao encontrada", "Erro de Login")
            );
            return null;
        }
        
        this.usuario.setLogado(true);
        return "index";
    }
    
    
    
    
    public String logout(){
        UsuarioDAO dao = new UsuarioDAO();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.usuario.setLogado(false);
        this.usuario = null;
        dao.terminaSessao();
        return "login.xhtml";
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
