/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class Login {
    String tipo;
    int matricula;
    String telaPosLogin;
    
    
    
    public void loga(){
        switch(tipo){
            case "Aluno":
                AlunoBean aDao = new AlunoBean();
                if(aDao.autentica(matricula)){
                    telaPosLogin = "criarSolicitacao.xhtml";
                }else{telaPosLogin = "loginFailed.xhtml";}
                break;
            case "Professor":
                ProfessorBean pDao = new ProfessorBean();
                if(pDao.autentica(matricula)){
                    telaPosLogin = "periodoSolicitacao.xhtml";
                }else{telaPosLogin = "loginFailed.xhtml";}
                break;
            case "CRE":
                CREBean cdao = new CREBean();
                if(cdao.autentica(matricula)){
                    telaPosLogin = "periodoSolicitacao.xhtml";
                }else{telaPosLogin = "loginFailed.xhtml";}
                break;
            default:
                break;
        }
        FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bem vindo ao Sistema de Solicitações!", ""));
    }
    
    public String defineTelaPosLogin(){
        return telaPosLogin;
    }
    
}
