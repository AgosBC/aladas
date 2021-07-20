package ar.com.ada.api.aladas.entities;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
public class Persona {

    private String nombre;

    @Column(name = "tipo_documento_id")
    private String tipoDocumentoId;

    private String documento;

    @Column(name = "fecha_nacimento")
    private Date fechaNacimento;

    @Column(name = "pais_id")
    private Integer paisId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(String tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimento() {
        return fechaNacimento;
    }

    public void setFechaNacimento(Date fechaNacimento) {
        this.fechaNacimento = fechaNacimento;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    
    
}
