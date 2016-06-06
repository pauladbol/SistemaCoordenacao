/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Disciplina;
import modelo.Documento;
import modelo.EstadoEnum;
import modelo.Professor;
import modelo.Solicitacao;
import modelo.Usuario;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import persistencia.DisciplinaDAO;
import persistencia.DocumentoDAO;
import persistencia.ProfessorDAO;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@SessionScoped
public class SolicitacaoBean {
    private List<Solicitacao> solicitacoes = new ArrayList<>();
    private SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    final private List<Disciplina> disciplinas;
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    final private List<Professor> professores;
    private Solicitacao novaSolicitacao = new Solicitacao();
    private Solicitacao solicitacao;
    private Documento documento = new Documento();
    private UploadedFile arquivo;
    private List<Documento> documentos = new ArrayList();
    private StreamedContent arquivoDownload;
    
    public SolicitacaoBean() {
        this.disciplinas = disciplinaDAO.listar();
        this.professores = professorDAO.listar();
        this.novaSolicitacao.setProtocolo(geradorProtocolo());
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

    public Disciplina findDisciplinaByName(String name) {
        for(Disciplina disciplina : disciplinas) {
            if (disciplina. getNome().equals(name))
                return disciplina;
        }
        return null;
    }
    
    public Professor findProfessorByName(String name) {
        for(Professor professor : professores) {
            if (professor.getNome().equals(name))
                return professor;
        }
        return null;
    }
    
    public void criarSolicitacao(){
        novaSolicitacao.setEstado(EstadoEnum.ENTREGUE.toString());
        DocumentoDAO documentoDAO = new DocumentoDAO();
        solicitacaoDAO = new SolicitacaoDAO();
        try {
            documento.setNome(arquivo.getFileName());
            documento.setTamanho(arquivo.getSize());
            documento.setArquivo(IOUtils.toByteArray(arquivo.getInputstream()));
            documento.setSolicitacao(novaSolicitacao);
            solicitacaoDAO.salvar(novaSolicitacao);
            documentoDAO.salvar(documento);            
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            return;
        }
        
        novaSolicitacao = new Solicitacao();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação salva com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void indeferirSolicitacao(){
        solicitacao.setEstado(EstadoEnum.INDEFERIDO.toString());
        salvar();
    }
    
    public String avaliarDocumentosSolicitacao(){
        solicitacao.setEstado(EstadoEnum.PRE_ANALISE.toString());
        solicitacaoDAO = new SolicitacaoDAO();
        try {
            solicitacaoDAO.salvar(solicitacao);            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            return "";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação salva com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        return "consultaSolicitacao";
    }
    
    public void aceitarSolicitacao(){
        solicitacao.setEstado(EstadoEnum.ANALISE.toString());
        salvar();
    }
    
    public void aprovarSolicitacao(){
        solicitacao.setEstado(EstadoEnum.APROVADO.toString());
        salvar();
    }
    
    public void reprovarSolicitacao(){
        solicitacao.setEstado(EstadoEnum.REPROVADO.toString());
        salvar();
    }
    
    public void marcaProvaSolicitacao(){
        solicitacao.setEstado(EstadoEnum.PROVA.toString());
        salvar();
    }
    
    private String geradorProtocolo() {
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
        return protocolo;
    }
    
    public String consultaSolicitacao(){
        this.solicitacaoDAO = new SolicitacaoDAO();
        this.solicitacoes = solicitacaoDAO.listar();
        return "consultaSolicitacao";
    }
    
    public String detalhesSolicitacao(Solicitacao solicitacao){
        this.solicitacao = solicitacao;
        DocumentoDAO documentoDAO = new DocumentoDAO();
        this.documento = documentoDAO.findBySolicitacao(solicitacao);
        ByteArrayInputStream input = new ByteArrayInputStream(this.documento.getArquivo());
        arquivoDownload = new DefaultStreamedContent(input, "", this.documento.getNome());
        return "detalheSolicitacao";
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

    public SolicitacaoDAO getSolicitacaoDAO() {
        return solicitacaoDAO;
    }

    public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
        this.solicitacaoDAO = solicitacaoDAO;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public DisciplinaDAO getDisciplinaDAO() {
        return disciplinaDAO;
    }

    public ProfessorDAO getProfessorDAO() {
        return professorDAO;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public StreamedContent getArquivoDownload() {
        return arquivoDownload;
    }
}
