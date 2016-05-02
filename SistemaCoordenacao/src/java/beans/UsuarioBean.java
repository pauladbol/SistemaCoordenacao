package beans;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import persistencia.UsuarioDAO;

/**
 *
 * @author brunof
 */

@ManagedBean(name="beanUsuario")
@SessionScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    UsuarioDAO dao = new UsuarioDAO();
    String telaPosLogin = "";
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public Usuario carrega(){
        return dao.carregar(usuario.getMatricula());
    }
    
    public String defineTelaPosLogin(){
        return telaPosLogin;
    }
    
    public void autentica(){
        usuario = dao.autentica(usuario.getMatricula(), usuario.getTipo());
        if(usuario == null){
            usuario = new Usuario();
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Matricula nao encontrada", "Erro de Login")
            );
            telaPosLogin = "loginFailed.xhtml";
        }else{
            switch (usuario.getTipo()){
                case "Aluno":
                    telaPosLogin = "criarSolicitacao.xhtml";
                    break;
                case "Professor":
                    telaPosLogin = "periodoSolicitacao.xhtml";
                    break;
                    /** Quando Coordenador e CRE forem implementados
                case "Coordenador":
                    telaPosLogin = "";
                    break;
                case "CRE":
                    telaPosLogin = "";
                    break;
                    */
                default:
                    telaPosLogin = "loginFailed.xhtml";
                    break;
                    
            }
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Matricula nao encontrada", "Erro de Login"));
        }
    }
    
    
    @PreDestroy
    public void fechaSessao(){
        dao.terminaSessao();
    }
    
}