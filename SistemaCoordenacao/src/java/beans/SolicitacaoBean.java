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
import modelo.MensagemEnum;
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
import persistencia.UsuarioDAO;
import service.EmailService;

@ManagedBean(name="solicitacaoBean")
@SessionScoped
public class SolicitacaoBean {
    private List<Solicitacao> solicitacoes = new ArrayList<>();
    private SolicitacaoDAO solicitacaoDAO;
    private DisciplinaDAO disciplinaDAO;
    private List<Disciplina> disciplinas;
    private Solicitacao novaSolicitacao = new Solicitacao();
    private Solicitacao solicitacao;
    private Documento documento = new Documento();
    private UploadedFile arquivo;
    private List<Documento> documentos = new ArrayList();
    private StreamedContent arquivoDownload;
    private final Usuario usuarioLogado;
    private UsuarioDAO userDao = new UsuarioDAO();
    private List<Usuario> professorDisciplina;
    
    public SolicitacaoBean() {
        this.disciplinaDAO = new DisciplinaDAO();
        this.disciplinas = disciplinasCursoAluno();
        this.usuarioLogado = getUsuarioLogado();
        this.professorDisciplina = new ArrayList<Usuario>();
    }
    
    public List<Disciplina> disciplinasCursoAluno(){
        if (getUsuarioLogado().getTipo().equalsIgnoreCase("Aluno")){
            return getUsuarioLogado().getCurso().getDisciplinas();
        } else {
            return disciplinaDAO.listar();
        }
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
    
    public void salvar(Solicitacao solicitacao) {
        solicitacaoDAO = new SolicitacaoDAO();
        try {
            solicitacaoDAO.salvar(solicitacao);            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            return;
        }
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
        for(Usuario professor : professorDisciplina) {
            if (professor. getNome().equals(name))
                return professor;
        }
        return null;
    }
    
    
    public List<Usuario> listaProfessoresByDisciplina(){
        this.professorDisciplina = getUserDao().listaProfessoresByDisciplina(solicitacao.getDisciplina().getId());
        return professorDisciplina;
    }
    
    public void criarSolicitacao() {
        if (this.arquivo.getSize() == 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "A solicitação deve ter algum arquivo anexo!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        
        this.novaSolicitacao.setProtocolo(geradorProtocolo());
        this.novaSolicitacao.setEstado(EstadoEnum.ENTREGUE.toString());
        this.novaSolicitacao.setUsuario(getUsuarioLogado());
        this.novaSolicitacao.setCoordenador(getUsuarioLogado().getCurso().getCoordenador());
        
        final SolicitacaoDAO dao = new SolicitacaoDAO();
        dao.salvar(this.novaSolicitacao);
        
        try {
             salvarDocumento();
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return;
        }
        
        EmailService.enviarEmail(MensagemEnum.ENTREGUE_ALUNO.toString(), novaSolicitacao.getUsuario().getEmail(), novaSolicitacao.getProtocolo());
        EmailService.enviarEmail(MensagemEnum.ENTREGUE_CRE.toString(), "cre123cre@gmail.com", novaSolicitacao.getProtocolo());
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação salva com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        this.novaSolicitacao = new Solicitacao();
    }
    
    public void salvarDocumento() throws IOException{
        this.documento.setNome(this.arquivo.getFileName());
        this.documento.setTamanho(this.arquivo.getSize());
        this.documento.setArquivo(IOUtils.toByteArray(this.arquivo.getInputstream()));
        this.documento.setSolicitacao(this.novaSolicitacao);

        DocumentoDAO documentoDAO = new DocumentoDAO();
        documentoDAO.salvar(this.documento);
    }
    
    public void indeferirSolicitacao(){
        solicitacao.setEstado(EstadoEnum.INDEFERIDO.toString());
        
        EmailService.enviarEmail(MensagemEnum.INDEFERIDO.toString(), "cre123cre@gmail.com", solicitacao.getProtocolo());
        salvar();
    }

    public String deferirSolicitacao(){
        solicitacao.setEstado(EstadoEnum.DEFERIDO.toString());
        EmailService.enviarEmail(MensagemEnum.DEFERIDO.toString(), "cre123cre@gmail.com", solicitacao.getProtocolo());
        salvar(this.solicitacao);
        return "consultaSolicitacao";
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
        
        EmailService.enviarEmail(MensagemEnum.PRE_ANALISE.toString(), solicitacao.getCoordenador().getEmail(), solicitacao.getProtocolo());
        return "consultaSolicitacao";
    }
    
    public void aceitarSolicitacao(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        EmailService.enviarEmail(MensagemEnum.ANALISE.toString(), solicitacao.getProfessor().getEmail(), solicitacao.getProtocolo());
        
        solicitacao.setEstado(EstadoEnum.ANALISE.toString());
        novaSolicitacao = solicitacao;
        salvar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                    "Professor selecionado com sucesso!", "");
            context.addMessage(null, msg);
    }
    
    public String aprovarSolicitacao(){
        solicitacao.setEstado(EstadoEnum.APROVADO.toString());

        EmailService.enviarEmail(MensagemEnum.APROVADO.toString(), solicitacao.getCoordenador().getEmail(), solicitacao.getProtocolo());
        salvar(this.solicitacao);
        return "consultaSolicitacao";
    }
    
    public void reprovarSolicitacao(){
        solicitacao.setEstado(EstadoEnum.REPROVADO.toString());
  
        EmailService.enviarEmail(MensagemEnum.REPROVADO.toString(), solicitacao.getCoordenador().getEmail(), solicitacao.getProtocolo());
        salvar(this.solicitacao);
    }
    
    public String marcaProvaSolicitacao() throws ParseException{
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(new Date());
        Date dataAtual = sdf.parse(dataFormatada);
        
        if (solicitacao.getDataProva().before(dataAtual)){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "A data da prova deve ser maior ou igual a data atual!", "");
            context.addMessage(null, msg);
            return "";
        }
        
        solicitacao.setEstado(EstadoEnum.PROVA.toString());
        novaSolicitacao = solicitacao;
        EmailService.enviarEmail(MensagemEnum.PROVA_ALUNO.toString(), solicitacao.getUsuario().getEmail(), solicitacao.getProtocolo());
        EmailService.enviarEmail(MensagemEnum.PROVA_PROFESSOR.toString(), solicitacao.getProfessor().getEmail(), solicitacao.getProtocolo());
        salvar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                    "A data da prova foi marcada com sucesso!", "");
            context.addMessage(null, msg);
        return "index";
    }
    
    public boolean renderNovaSolicitacao() throws ParseException{
        PeriodoSolicitacaoDAO periodoDao = new PeriodoSolicitacaoDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(new Date());
        Date dataAtual = sdf.parse(dataFormatada);
        PeriodoSolicitacao p = periodoDao.findPeriodoValido(dataAtual);
        
//        listaDisciplinas();
        
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
     
    public boolean renderAprovarSolicitacao(){
        return (usuarioLogado.getTipo().equalsIgnoreCase("Professor") && solicitacao.getEstado().equals("Aguardando Prova"));
    }
      
    public boolean renderDeferirSolicitacao(){
        return (usuarioLogado.isCoordenador() && solicitacao.getEstado().equals("Aprovado"));
    }
    
    public boolean renderObservacao() {
        return (usuarioLogado.isCoordenador() && (solicitacao.getEstado().equalsIgnoreCase("Em pré-análise")
                || solicitacao.getEstado().equalsIgnoreCase("Aprovado")));
    }
    
    public boolean renderJustificativa(){
        return (usuarioLogado.getTipo().equalsIgnoreCase("Aluno") && solicitacao.getEstado().equalsIgnoreCase("Indeferido"));
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
        this.solicitacoes = this.solicitacaoDAO.listar(this.usuarioLogado, "Em análise", "Aguardando Prova");
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

    /**
     * @return the userDao
     */
    public UsuarioDAO getUserDao() {
        return userDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(UsuarioDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * @return the professorDisciplina
     */
    public List<Usuario> getProfessorDisciplina() {
        return professorDisciplina;
    }

    /**
     * @param professorDisciplina the professorDisciplina to set
     */
    public void setProfessorDisciplina(List<Usuario> professorDisciplina) {
        this.professorDisciplina = professorDisciplina;
    }
}
