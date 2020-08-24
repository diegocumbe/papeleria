/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entities.Facturaarticulo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *PapeleriaPU
 * @author Diego Cumbe
 */
public class FacturaarticuloJpaController implements Serializable {

    public FacturaarticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public FacturaarticuloJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PapeleriaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturaarticulo facturaarticulo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(facturaarticulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturaarticulo facturaarticulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            facturaarticulo = em.merge(facturaarticulo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaarticulo.getIdFacArt();
                if (findFacturaarticulo(id) == null) {
                    throw new NonexistentEntityException("The facturaarticulo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturaarticulo facturaarticulo;
            try {
                facturaarticulo = em.getReference(Facturaarticulo.class, id);
                facturaarticulo.getIdFacArt();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaarticulo with id " + id + " no longer exists.", enfe);
            }
            em.remove(facturaarticulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturaarticulo> findFacturaarticuloEntities() {
        return findFacturaarticuloEntities(true, -1, -1);
    }

    public List<Facturaarticulo> findFacturaarticuloEntities(int maxResults, int firstResult) {
        return findFacturaarticuloEntities(false, maxResults, firstResult);
    }

    private List<Facturaarticulo> findFacturaarticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturaarticulo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Facturaarticulo findFacturaarticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturaarticulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaarticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturaarticulo> rt = cq.from(Facturaarticulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
