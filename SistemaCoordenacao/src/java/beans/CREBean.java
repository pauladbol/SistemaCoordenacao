/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import modelo.CRE;
import persistencia.CREDao;

/**
 *
 * @author brunoscheltzke
 */
public class CREBean {
    CREDao dao = new CREDao();
    CRE cre = new CRE();
    
    public boolean autentica(int matricula){
        cre = dao.autentica(matricula);
        
        if(cre==null){
            return false;
        }
        return true;
    }
    
    
}
