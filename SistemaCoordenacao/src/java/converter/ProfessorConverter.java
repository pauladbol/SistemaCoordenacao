/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import beans.SolicitacaoBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Professor;



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
        Professor professor = bean.findProfessorByName(value);
        return professor;
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        String label = null;
        if (value instanceof Professor) {
            label = value.toString();
        }
        return label;
    }

}