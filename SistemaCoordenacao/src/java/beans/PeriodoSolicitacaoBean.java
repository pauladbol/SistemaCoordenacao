package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.PeriodoSolicitacao;
import persistencia.PeriodoSolicitacaoDAO;

@ManagedBean(name="periodoBean")
@RequestScoped
public class PeriodoSolicitacaoBean {
    private PeriodoSolicitacaoDAO dao = new PeriodoSolicitacaoDAO();
    private PeriodoSolicitacao periodo = new PeriodoSolicitacao();
    
    public String criarPeriodo() throws ParseException{
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(new Date());
        Date dataAtual = sdf.parse(dataFormatada);
        
        if (periodo.getDataInicio().before(dataAtual)){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "A data de ínicio deve ser maior ou igual a data atual!", "");
            context.addMessage(null, msg);
            return "";
        }
        if (periodo.getDataFim().before(periodo.getDataInicio())){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "A data de fim não pode ser menor que a data de ínicio!", "");
            context.addMessage(null, msg);
            return "";
        }
        List<PeriodoSolicitacao> periodos = dao.listar();
        
        for(PeriodoSolicitacao p : periodos) {
            if ((periodo.getDataInicio().after(p.getDataInicio()) && periodo.getDataInicio().before(p.getDataFim())) || 
                    (periodo.getDataFim().after(p.getDataInicio()) && periodo.getDataFim().before(p.getDataFim())) ||
                    (periodo.getDataInicio().before(p.getDataInicio()) && periodo.getDataFim().after(p.getDataFim()))) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "Já existe periodo aberto para as datas informadas!", "");
                context.addMessage(null, msg);
                return "";
            }
        }
        periodo.setDataInicio(periodo.getDataInicio());
        periodo.setDataFim(periodo.getDataFim());
        
        
        dao.criar(periodo);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                    "O período de solicitação foi criado com sucesso!", "");
        context.addMessage(null, msg);
        this.periodo = new PeriodoSolicitacao();
        return "index";
    }

    public Date getDataInicio() {
        return periodo.getDataInicio();
    }

    public void setDataInicio(Date dataInicio) {
        this.periodo.setDataInicio(dataInicio);
    }

    public Date getDataFim() {
        return periodo.getDataFim();
    }

    public void setDataFim(Date dataFim) {
        this.periodo.setDataFim(dataFim);
    }
}
