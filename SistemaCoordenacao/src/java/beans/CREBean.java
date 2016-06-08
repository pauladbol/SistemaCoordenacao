package beans;

import modelo.CRE;
import persistencia.CREDao;

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
