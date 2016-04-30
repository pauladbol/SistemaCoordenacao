package beans;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.PeriodoSolicitacao;
import persistencia.PeriodoSolicitacaoDAO;

@ManagedBean(name="periodoBean")
@RequestScoped
public class PeriodoSolicitacaoBean {
    private final PeriodoSolicitacaoDAO dao = new PeriodoSolicitacaoDAO();
    private final PeriodoSolicitacao periodo = new PeriodoSolicitacao();
    
   public void criarPeriodo(){
      dao.criar(periodo);
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
