package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
//

        try {
            entityManager.getTransaction().begin();

            Factura f1 = new Factura();
            f1.setNumero(12);
            f1.setFecha("10/09/2024");

            Domicilio dom = new Domicilio("San Martin", 1222);
            Cliente cliente = new Cliente("Juana", "Muñoz", 15245778);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            f1.setCliente(cliente);

            Categoria perecederos = new Categoria("perecederos");
            Categoria lacteos = new Categoria("lacteos");
            Categoria limpieza = new Categoria("limpieza");

            Articulo art1 = new Articulo(200, "Yogurt ser natural", 20);
            Articulo art2 = new Articulo(300, "Detergente Cif", 30);

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = new DetalleFactura();

            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalle().add(det1);

            f1.getDetalles().add(det1);

            det1.setFactura(f1);

            DetalleFactura det2 = new DetalleFactura();

            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(30);

            art2.getDetalle().add(det2);

            f1.getDetalles().add(det2);

            det2.setFactura(f1);

            f1.setTotal(70);

            entityManager.persist(f1);

            //Factura factura = entityManager.find(Factura.class, 1L);
            //factura.setNumero(85);

            //entityManager.merge(factura);

            entityManager.flush();

            entityManager.getTransaction().commit();

        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        entityManagerFactory.close();
    }
}