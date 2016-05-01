package beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    
    public String criarPeriodo(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        Date data = new Date();
        if (!periodo.getDataInicio().after(data)){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "A data de ínicio deve ser maior que a data atual!", "");
            context.addMessage(null, msg);
            return "";
        }
        if (!periodo.getDataFim().after(periodo.getDataInicio())){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    "A data de fim não pode ser menor que a data de ínicio!", "");
            context.addMessage(null, msg);
            return "";
        }
        dao.criar(periodo);
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
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
