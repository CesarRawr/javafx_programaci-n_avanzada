package com.pos.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice", schema = "tienda")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;
    public String hora;

    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hora", nullable = false, length = 80)
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    public List<LineEntity> lines = new ArrayList<LineEntity>();

    public List<LineEntity> getLines() {
        return this.lines;
    }

    public void setLines(List<LineEntity> lines) {
        this.lines = lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceEntity that = (InvoiceEntity) o;

        if (id != that.id) return false;
        if (hora != null ? !hora.equals(that.hora) : that.hora != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        return result;
    }
}
