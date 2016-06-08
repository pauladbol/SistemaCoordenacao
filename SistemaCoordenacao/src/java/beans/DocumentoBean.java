package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import modelo.Documento;
import org.primefaces.model.UploadedFile;
import persistencia.DocumentoDAO;
 
@ManagedBean(name="documentoBean")
public class DocumentoBean {
    private UploadedFile file;
    private Documento documento = new Documento();
    private DocumentoDAO dao = new DocumentoDAO();
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    
    public void upload() {
        if(file != null) {
            documento.setNome(file.getFileName());
            documento.setTamanho(file.getSize());
            byte[] arquivo = file.getContents();
            documento.setArquivo(arquivo);
            FacesMessage message = new FacesMessage("Arquivo ", file.getFileName() + " anexado");
            FacesContext.getCurrentInstance().addMessage(null, message);
            salvar();
        }
    }
    
    public void salvar() {
        dao.salvar(documento);
    }
}
