/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego Cumbe
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByIdCli", query = "SELECT c FROM Clientes c WHERE c.idCli = :idCli")
    , @NamedQuery(name = "Clientes.findByNombres", query = "SELECT c FROM Clientes c WHERE c.nombres = :nombres")
    , @NamedQuery(name = "Clientes.findByApellidos", query = "SELECT c FROM Clientes c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Clientes.findByIdentificacion", query = "SELECT c FROM Clientes c WHERE c.identificacion = :identificacion")
    , @NamedQuery(name = "Clientes.findByCorreo", query = "SELECT c FROM Clientes c WHERE c.correo = :correo")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Clientes.findByCiudad", query = "SELECT c FROM Clientes c WHERE c.ciudad = :ciudad")
    , @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCli")
    private Integer idCli;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;

    public Clientes() {
    }

    public Clientes(Integer idCli) {
        this.idCli = idCli;
    }

    public Clientes(Integer idCli, String nombres, String apellidos, String identificacion, String correo, String telefono, String ciudad, String direccion) {
        this.idCli = idCli;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public Integer getIdCli() {
        return idCli;
    }

    public void setIdCli(Integer idCli) {
        this.idCli = idCli;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCli != null ? idCli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idCli == null && other.idCli != null) || (this.idCli != null && !this.idCli.equals(other.idCli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clientes[ idCli=" + idCli + " ]";
    }
    
}
