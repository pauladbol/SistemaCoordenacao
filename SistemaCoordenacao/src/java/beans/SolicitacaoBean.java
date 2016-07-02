/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import javax.enterprise.inject.spi.Bean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Disciplina;
import modelo.Documento;
import modelo.EstadoEnum;
import modelo.PeriodoSolicitacao;
import modelo.Solicitacao;
import modelo.Usuario;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import persistencia.DisciplinaDAO;
import persistencia.DocumentoDAO;
import persistencia.PeriodoSolicitacaoDAO;
import persistencia.SolicitacaoDAO;

@ManagedBean(name="solicitacaoBean")
@SessionScoped
public class SolicitacaoBean {
    private List<Solicitacao> solicitacoes = new ArrayList<>();
    private SolicitacaoDAO solicitacaoDAO;
    private final DisciplinaDAO disciplinaDAO;
    final private List<Disciplina> disciplinas;
    private List<Usuario> professores;
    private Solicitacao novaSolicitacao = new Solicitacao();
    private Solicitacao solicitacao;
    private Documento documento = new Documento();
    private UploadedFile arquivo;
    private List<Documento> documentos = new ArrayList();
    private StreamedContent arquivoDownload;
    private final Usuario usuarioLogado;
    
    /*@ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;*/
    
    public SolicitacaoBean() {
        this.disciplinaDAO = new DisciplinaDAO();
        this.disciplinas = disciplinaDAO.listar();
        this.novaSolicitacao.setProtocolo(geradorProtocolo());
        this.usuarioLogado = getUsuarioLogado();
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
    
    public Usuario findProfessorByName(String name) {
        for(Usuario professor : professores) {
            if (professor.getNome().equals(name))
                return professor;
        }
        return null;
    }
    
    public void criarSolicitacao() {
        this.novaSolicitacao.setEstado(EstadoEnum.ENTREGUE.toString());
        this.novaSolicitacao.setUsuario(getUsuarioLogado());
        DocumentoDAO documentoDAO = new DocumentoDAO();
        
        try {
            this.documento.setNome(this.arquivo.getFileName());
            this.documento.setTamanho(this.arquivo.getSize());
            this.documento.setArquivo(IOUtils.toByteArray(this.arquivo.getInputstream()));
            this.solicitacaoDAO = new SolicitacaoDAO();
            this.solicitacaoDAO.salvar(this.novaSolicitacao);
            this.documento.setSolicitacao(this.novaSolicitacao);
            documentoDAO.salvar(this.documento); 
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return;
        }
        
        this.novaSolicitacao = new Solicitacao();
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
        consultaSolicitacaoCRE();
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
    
    public boolean renderNovaSolicitacao() throws ParseException{
        PeriodoSolicitacaoDAO periodoDao = new PeriodoSolicitacaoDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(new Date());
        Date dataAtual = sdf.parse(dataFormatada);
        PeriodoSolicitacao p = periodoDao.findPeriodoValido(dataAtual);
        
        return p != null && usuarioLogado.getTipo().equalsIgnoreCase("Aluno");
    }
    //botões detalhe solicitação
    public boolean renderEncaminharSolicitacao() {
        return (usuarioLogado.getTipo().equalsIgnoreCase("cre") && solicitacao.getEstado().equals("Entregue"));
    }
     
    public boolean renderListarProfessores() {
        return (usuarioLogado.isCoordenador() == true 
                && solicitacao.getEstado().equalsIgnoreCase("Em pré-análise"));
    }
    
    public boolean renderIndeferirSolicitacao() {
        return (usuarioLogado.isCoordenador() == true && (solicitacao.getEstado().equals("Em pré-análise")
                || solicitacao.getEstado().equalsIgnoreCase("Aprovado") || solicitacao.getEstado().equals("Reprovado")));
    }
    
    public boolean renderSelecionarDataProva() {
        return (usuarioLogado.getTipo().equalsIgnoreCase("Professor") && solicitacao.getEstado().equals("Em análise"));
    }
     
    public boolean renderReprovarSolicitacao() {
        return (usuarioLogado.getTipo().equalsIgnoreCase("Professor") && solicitacao.getEstado().equals("Em análise"));
    }
    
    public boolean renderAbrirPeriodoSolicitacao(){
        return (usuarioLogado.getTipo().equalsIgnoreCase("cre"));
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
        String tipoUsuario = this.usuarioLogado.getTipo();
        switch(tipoUsuario){
            case "Aluno":
                consultaSolicitacaoAluno();
                break;
            case "Professor":
                consultaSolicitacaoProfessor();
                if (this.usuarioLogado.isCoordenador())
                    consultaSolicitacaoCoordenador();
                break;
            case "cre":
                consultaSolicitacaoCRE();
                break;
            default:
                return null;
        }
        return "consultaSolicitacao";
    }
    
    private void consultaSolicitacaoCRE(){
        this.solicitacaoDAO = new SolicitacaoDAO();
        this.solicitacoes = this.solicitacaoDAO.listar("Entregue", "Deferido", "Indeferido");
    }
    
    private void consultaSolicitacaoAluno(){
        this.solicitacaoDAO = new SolicitacaoDAO();
        this.solicitacoes = this.solicitacaoDAO.listar(this.usuarioLogado);
    }
    
    private void consultaSolicitacaoProfessor(){
        this.solicitacaoDAO = new SolicitacaoDAO();
        this.solicitacoes = this.solicitacaoDAO.listar(this.usuarioLogado, "Em análise");
    }
    
    private void consultaSolicitacaoCoordenador(){
        this.solicitacaoDAO = new SolicitacaoDAO();
        List<Solicitacao> solicitacoesCoordenador = this.solicitacaoDAO.listar("Em pré-análise", "Aprovado", "Reprovado");
        for(Solicitacao s : solicitacoesCoordenador) {
            this.solicitacoes.add(s);
        }
    }
    
    public String detalhesSolicitacao(Solicitacao solicitacao){
        this.solicitacao = solicitacao;
        DocumentoDAO documentoDAO = new DocumentoDAO();
        this.documento = documentoDAO.findBySolicitacao(solicitacao);
        ByteArrayInputStream input = new ByteArrayInputStream(this.documento.getArquivo());
        arquivoDownload = new DefaultStreamedContent(input, "", this.documento.getNome());
        return "detalheSolicitacao";
    }
    
    private Usuario getUsuarioLogado(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean loginBean = (LoginBean) facesContext.getApplication()
            .createValueBinding("#{loginBean}").getValue(facesContext);
        return loginBean.getUsuario();
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
    
    public List<Usuario> getProfessores() {
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
