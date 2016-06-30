package beans;

import java.util.List;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import persistencia.UsuarioDAO;

@ManagedBean(name="beanUsuario")
@SessionScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    UsuarioDAO dao = new UsuarioDAO();
    String telaPosLogin = "";
    private String matricula;
    
    public UsuarioBean(){}
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public String defineTelaPosLogin(){
        return telaPosLogin;
    }
    
    public String loga(){
        usuario = dao.logar(getMatricula());
        
        if(usuario==null){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Matricula nao encontrada", "Erro de Login")
            );
            return null;
        }else{
            return "login-sucesso";
        }
    }
    
    public String logout(){
        usuario = null;
        dao.terminaSessao();
        return "login.xhtml";
    }
    
    public List<Usuario> listaProfessores(){
        return dao.listaProfessores();
    }
    
    /*public void autentica(){
        usuario = dao.logar(usuario.getMatricula(), usuario.getTipo());
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
                    
                case "Funcionario":
                    telaPosLogin = "periodoSolicitacao.xhtml";
                    break;
                case "CRE":
                    telaPosLogin = "";
                    break;
                    
                default:
                    telaPosLogin = "loginFailed.xhtml";
                    break;
                    
            }
            
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bem vindo ao Sistema de Solicitações!", ""));
        }
    }*/
    
    
    @PreDestroy
    public void fechaSessao(){
        dao.terminaSessao();
    }

    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
}