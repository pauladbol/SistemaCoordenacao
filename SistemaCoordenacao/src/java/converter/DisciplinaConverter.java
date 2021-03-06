package converter;

import beans.SolicitacaoBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Disciplina;



@FacesConverter("disciplinaConverter")
public class DisciplinaConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return null;
        }
        SolicitacaoBean bean = context.getApplication()
                .evaluateExpressionGet(context, "#{solicitacaoBean}",
                        SolicitacaoBean.class);
        Disciplina disciplina = bean.findDisciplinaByName(value);
        return disciplina;
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        String label = null;
        if (value instanceof Disciplina) {
            label = value.toString();
        }
        return label;
    }

}