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
@Table(name = "facturaarticulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturaarticulo.findAll", query = "SELECT f FROM Facturaarticulo f")
    , @NamedQuery(name = "Facturaarticulo.findByIdFacArt", query = "SELECT f FROM Facturaarticulo f WHERE f.idFacArt = :idFacArt")
    , @NamedQuery(name = "Facturaarticulo.findByCliente", query = "SELECT f FROM Facturaarticulo f WHERE f.cliente = :cliente")
    , @NamedQuery(name = "Facturaarticulo.findByArticulo", query = "SELECT f FROM Facturaarticulo f WHERE f.articulo = :articulo")
    , @NamedQuery(name = "Facturaarticulo.findByCantidad", query = "SELECT f FROM Facturaarticulo f WHERE f.cantidad = :cantidad")})
public class Facturaarticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFacArt")
    private Integer idFacArt;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @Column(name = "articulo")
    private int articulo;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;

    public Facturaarticulo() {
    }

    public Facturaarticulo(Integer idFacArt) {
        this.idFacArt = idFacArt;
    }

    public Facturaarticulo(Integer idFacArt, int cliente, int articulo, int cantidad) {
        this.idFacArt = idFacArt;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    public Integer getIdFacArt() {
        return idFacArt;
    }

    public void setIdFacArt(Integer idFacArt) {
        this.idFacArt = idFacArt;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getArticulo() {
        return articulo;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacArt != null ? idFacArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturaarticulo)) {
            return false;
        }
        Facturaarticulo other = (Facturaarticulo) object;
        if ((this.idFacArt == null && other.idFacArt != null) || (this.idFacArt != null && !this.idFacArt.equals(other.idFacArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Facturaarticulo[ idFacArt=" + idFacArt + " ]";
    }
    
}
