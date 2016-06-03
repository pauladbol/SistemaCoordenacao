/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;
import modelo.Disciplina;
import modelo.Documento;
import modelo.EstadoEnum;
import modelo.Solicitacao;
import org.primefaces.model.UploadedFile;
import persistencia.DisciplinaDAO;
import persistencia.DocumentoDAO;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@ViewScoped
public class SolicitacaoBean {
    private List<Solicitacao> solicitacoes = new ArrayList<>();
//    final private DocumentoDAO docDAO = new DocumentoDAO();
    private SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    final private List<Disciplina> disciplinas;
    private Solicitacao novaSolicitacao = new Solicitacao();
    private Solicitacao solicitacao;
    private UploadedFile file;
    private Documento documento = new Documento();
    
    public SolicitacaoBean() {
        this.disciplinas = disciplinaDAO.listar();
    }
    
    public void salvar() {
        solicitacaoDAO = new SolicitacaoDAO();
        
//        upload();
//        docDAO.salvar(documento);
        try {
            solicitacaoDAO.salvar(novaSolicitacao);            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            return;
        }
        novaSolicitacao = new Solicitacao();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação salva com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public Solicitacao carrega(){
        return solicitacaoDAO.carregar(novaSolicitacao.getId());
    }
    
//    public void upload() {FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
//        if(file != null) {
//            documento.setNome(file.getFileName());
//            documento.setTamanho(file.getSize());
//            byte[] arquivo = file.getContents();
//            documento.setArquivo(arquivo);
//            documento.setSolicitacao(novaSolicitacao);
////            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
////            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
//    }
    
//23
    public Disciplina findDisciplinaByName(String name) {
        for(Disciplina disciplina : disciplinas) {
            if (disciplina.getNome().equals(name))
                return disciplina;
        }
        return null;
    }
    
    public void criarSolicitacao(){
        novaSolicitacao.setEstado(EstadoEnum.ENTREGUE.toString());
        novaSolicitacao.setProtocolo(geradorProtocolo());
        salvar();
    }
    
    private int geradorProtocolo() {
        solicitacaoDAO = new SolicitacaoDAO();
        int id = solicitacaoDAO.findUltimoId();
        
        Date data = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        
        String protocolo = "";
        protocolo += cal.get(Calendar.YEAR);
        protocolo += (cal.get(Calendar.MONTH) + 1);
        protocolo += cal.get(Calendar.DAY_OF_MONTH);
        protocolo += "000";
        protocolo += id;
        return Integer.parseInt(protocolo);
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

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }
    
    public Solicitacao getNovaSolicitacao() {
        return novaSolicitacao;
    }

    public void setNovaSolicitacao(Solicitacao novaSolicitacao) {
        this.novaSolicitacao = novaSolicitacao;
    }
}
