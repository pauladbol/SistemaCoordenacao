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
import modelo.Disciplina;
import modelo.Documento;
import modelo.Solicitacao;
import org.primefaces.model.UploadedFile;
import persistencia.DocumentoDAO;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@RequestScoped
public class SolicitacaoBean {
    Solicitacao solicitacao = new Solicitacao();
    SolicitacaoDAO dao = new SolicitacaoDAO();
    private UploadedFile file;
    private Documento documento = new Documento();
    private DocumentoDAO daodoc = new DocumentoDAO();
    
    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    
    public void salvar() {
        upload();
        daodoc.salvar(documento);
        dao.salvar(solicitacao);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação salva com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public Solicitacao carrega(){
        return dao.carregar(solicitacao.getId());
    }
    
     public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    
    public void upload() {FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
        if(file != null) {
            documento.setNome(file.getFileName());
            documento.setTamanho(file.getSize());
            byte[] arquivo = file.getContents();
            documento.setArquivo(arquivo);
            documento.setSolicitacao(solicitacao);
//            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
