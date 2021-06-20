package com.pos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "line", schema = "tienda")
public class LineEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;
    public int cant;
    public int total;

    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cant", nullable = false)
    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    @Basic
    @Column(name = "total", nullable = false)
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @ManyToOne
    @JoinColumn(name = "invoice", nullable = false)
    public InvoiceEntity invoice;

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    @OneToOne
    @JoinColumn(name = "producto", nullable = false)
    public ProductoEntity producto;

    public ProductoEntity getProduct() {
        return this.producto;
    }

    public void setProduct(ProductoEntity producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "LineEntity{" +
                "id=" + id +
                ", cant=" + cant +
                ", total=" + total +
                ", invoice=" + invoice +
                ", producto=" + producto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LineEntity that = (LineEntity) o;

        if (id != that.id) return false;
        if (cant != that.cant) return false;
        if (total != that.total) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cant;
        result = 31 * result + total;
        return result;
    }
}
