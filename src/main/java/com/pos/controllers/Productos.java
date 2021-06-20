package com.pos.controllers;

import com.pos.models.InvoiceEntity;
import com.pos.models.ProductoEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class Productos {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    EntityManager em = factory.createEntityManager();

    public void insertProduct(ProductoEntity product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }
    
    public void deleteProduct(ProductoEntity pr) {
        ProductoEntity p = em.find(ProductoEntity.class, pr.getId());

        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public void updateProduct(ProductoEntity product) {
        ProductoEntity p = em.find(ProductoEntity.class, product.getId());

        em.getTransaction().begin();

        p.setNombre(product.getNombre());
        p.setPrecio(product.getPrecio());
        p.setStock(product.getStock());
        p.setType(product.getType());

        em.getTransaction().commit();
    }

    public List<ProductoEntity> getAll() {
        Query q = em.createQuery("select p from ProductoEntity p");
        return q.getResultList();
    }

    public List getAbarrotes() {
    	Query q = em.createQuery("select p from ProductoEntity p where p.type = 'abarrotes'");
    	return q.getResultList();
    }

    public List getBebidas() {
        Query q = em.createQuery("select p from ProductoEntity p where p.type = 'bebidas'");
        return q.getResultList();
    }

    public List getBotanas() {
        Query q = em.createQuery("select p from ProductoEntity p where p.type = 'botanas'");
        return q.getResultList();
    }

    public void insertInvoice(InvoiceEntity invoice) {
        em.getTransaction().begin();
        em.persist(invoice);
        em.getTransaction().commit();
    }

    public void refreshProducts(List<ProductoEntity> list){
        for(int i = 0; i < list.size(); i++) {
            em.refresh(list.get(i));
        }
    }
}