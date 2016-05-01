/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Disciplina;
import modelo.Solicitacao;

@ManagedBean
@FacesConverter(value="disciplinaConverter")
public class DisciplinaConverter implements Converter {

    @PersistenceContext
    private transient EntityManager em;  

    public DisciplinaConverter(){
        System.out.println("Inside Converter");
    }
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        return em.find(Disciplina.class, new Integer(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Disciplina disciplina;
        disciplina = (Disciplina) value;
        return String.valueOf(disciplina.getId());

    }
//    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

   

}
