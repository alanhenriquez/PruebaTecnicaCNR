package com.example.module.proyecto.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "proyecto_detalle")
public class ProyectoDetalle extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long codigoProyectoDetalle;

    public String descripcion;
    public Integer area;
    public Boolean estado;

    // Relación muchos a uno con Proyecto
    @ManyToOne
    @JoinColumn(name = "codigo_proyecto")
    public Proyecto proyecto;

    // Constructor, getters y setters
    public ProyectoDetalle() {}
    public ProyectoDetalle(String descripcion, Integer area, Boolean estado, Proyecto proyecto) {
        this.descripcion = descripcion;
        this.area = area;
        this.estado = estado;
        this.proyecto = proyecto;
    }

    public Long getCodigoProyectoDetalle() {
        return codigoProyectoDetalle;
    }

    public void setCodigoProyectoDetalle(Long codigoProyectoDetalle) {
        this.codigoProyectoDetalle = codigoProyectoDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}