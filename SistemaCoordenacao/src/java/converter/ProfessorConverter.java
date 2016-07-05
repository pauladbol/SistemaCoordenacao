package converter;

import beans.SolicitacaoBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Disciplina;
import modelo.Usuario;



@FacesConverter("professorConverter")
public class ProfessorConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return null;
        }
        SolicitacaoBean bean = context.getApplication()
                .evaluateExpressionGet(context, "#{solicitacaoBean}",
                        SolicitacaoBean.class);
        Usuario professor = bean.findProfessorByName(value);
        return professor;
    }
 
    
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        String label = null;
        if (value instanceof Object) {
            label = value.toString();
        }
        return label;
    }

}