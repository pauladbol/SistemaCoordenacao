package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name="solicitacao")
public class Solicitacao implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String protocolo;
    private String tipo;
    private String estado;
    private String observacao;
    private String justificativa; 
    @ManyToOne
    @JoinColumn(name="disciplina")
    private Disciplina disciplina;
    @ManyToOne
    @JoinColumn(name="usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="professor")
    private Usuario professor;
    
    @OneToMany
    private List<Documento> documentos;
    
    @ManyToOne
    @JoinColumn(name="coordenador")
    private Usuario coordenador;
    
    @Column(name="data_prova")
    @Type(type="date")
    private Date dataProva;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }     

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Usuario getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Usuario coordenador) {
        this.coordenador = coordenador;
    }

    /**
     * @return the dataProva
     */
    public Date getDataProva() {
        return dataProva;
    }

    /**
     * @param dataProva the dataProva to set
     */
    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
    }
    
}
