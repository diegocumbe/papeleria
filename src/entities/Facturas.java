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
@Table(name = "facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturas.findAll", query = "SELECT f FROM Facturas f")
    , @NamedQuery(name = "Facturas.findByIdFac", query = "SELECT f FROM Facturas f WHERE f.idFac = :idFac")
    , @NamedQuery(name = "Facturas.findByFecha", query = "SELECT f FROM Facturas f WHERE f.fecha = :fecha")
    , @NamedQuery(name = "Facturas.findByCliente", query = "SELECT f FROM Facturas f WHERE f.cliente = :cliente")
    , @NamedQuery(name = "Facturas.findByTotal", query = "SELECT f FROM Facturas f WHERE f.total = :total")})
public class Facturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFac")
    private Integer idFac;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @Column(name = "total")
    private String total;

    public Facturas() {
    }

    public Facturas(Integer idFac) {
        this.idFac = idFac;
    }

    public Facturas(Integer idFac, String fecha, int cliente, String total) {
        this.idFac = idFac;
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
    }

    public Integer getIdFac() {
        return idFac;
    }

    public void setIdFac(Integer idFac) {
        this.idFac = idFac;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFac != null ? idFac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturas)) {
            return false;
        }
        Facturas other = (Facturas) object;
        if ((this.idFac == null && other.idFac != null) || (this.idFac != null && !this.idFac.equals(other.idFac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Facturas[ idFac=" + idFac + " ]";
    }
    
}
