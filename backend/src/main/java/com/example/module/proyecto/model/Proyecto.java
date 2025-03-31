package com.example.module.proyecto.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "proyecto")
public class Proyecto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long codigoProyecto;

    public UUID uuid;
    public String nombre;
    public LocalDateTime fechaCreacion;
    public Boolean estado;

    // Relación uno a muchos con ProyectoDetalle
    @OneToMany(mappedBy = "proyecto")
    public List<ProyectoDetalle> detalles;

    // Constructor, getters y setters
    public Proyecto() {}
    public Proyecto(UUID uuid, String nombre, LocalDateTime fechaCreacion, Boolean estado) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Long getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(Long codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<ProyectoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ProyectoDetalle> detalles) {
        this.detalles = detalles;
    }
}