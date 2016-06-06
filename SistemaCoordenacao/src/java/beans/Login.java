/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Aluno;
import persistencia.AlunoDAO;
import persistencia.CREDao;
import persistencia.ProfessorDAO;

/**
 *
 * @author brunoscheltzke
 */
@ManagedBean(name="beanLogin")
@SessionScoped
public class Login {
    int id;
    private String tipo;
    private int matricula;
    private String telaPosLogin;
    
    
    
    public void loga(){
        switch(getTipo()){
            case "Aluno":
                AlunoBean aDao = new AlunoBean();
                if(aDao.autentica(getMatricula())){
                    setTelaPosLogin("criarSolicitacao.xhtml");
                }else{setTelaPosLogin("loginFailed.xhtml");}
                break;
            case "Professor":
                ProfessorBean pDao = new ProfessorBean();
                if(pDao.autentica(getMatricula())){
                    setTelaPosLogin("periodoSolicitacao.xhtml");
                }else{setTelaPosLogin("loginFailed.xhtml");}
                break;
            case "CRE":
                CREBean cdao = new CREBean();
                if(cdao.autentica(getMatricula())){
                    setTelaPosLogin("periodoSolicitacao.xhtml");
                }else{setTelaPosLogin("loginFailed.xhtml");}
                break;
            default:
                break;
        }
        
        if(telaPosLogin.equals("loginFailed.xhtml")){
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Matricula nao encontrada", "Erro de Login")
            );
        }else
        FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bem vindo ao Sistema de Solicitações!", ""));
    }
    
    public String defineTelaPosLogin(){
        return getTelaPosLogin();
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }
    
    

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the matricula
     */
    public int getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the telaPosLogin
     */
    public String getTelaPosLogin() {
        return telaPosLogin;
    }

    /**
     * @param telaPosLogin the telaPosLogin to set
     */
    public void setTelaPosLogin(String telaPosLogin) {
        this.telaPosLogin = telaPosLogin;
    }
    
}
