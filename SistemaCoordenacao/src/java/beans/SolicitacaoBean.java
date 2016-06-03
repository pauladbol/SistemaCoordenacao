/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import persistencia.DisciplinaDAO;
import persistencia.DocumentoDAO;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@ViewScoped
public class SolicitacaoBean {
    private List<Solicitacao> solicitacoes = new ArrayList<>();
    private SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    final private List<Disciplina> disciplinas;
    private Solicitacao novaSolicitacao = new Solicitacao();
    private Solicitacao solicitacao;
    private List<Documento> documentos = new ArrayList();
    
    public SolicitacaoBean() {
        this.disciplinas = disciplinaDAO.listar();
    }
    
    public void salvar() {
        solicitacaoDAO = new SolicitacaoDAO();
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
    
    public void doUpload(FileUploadEvent event) {
        UploadedFile arquivo = event.getFile();  
        
        Documento novoDocumento = new Documento();
        novoDocumento.setNome(arquivo.getFileName());
        novoDocumento.setTamanho(arquivo.getSize());
        //Pendente setSolicitacao
        documentos.add(novoDocumento);
        try {
            novoDocumento.setArquivo(IOUtils.toByteArray(arquivo.getInputstream()));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
    }

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
        novaSolicitacao.setDocumentos(documentos);
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
