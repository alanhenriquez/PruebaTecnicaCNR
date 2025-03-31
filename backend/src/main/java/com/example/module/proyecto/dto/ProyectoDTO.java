package com.example.module.proyecto.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProyectoDTO {

    private Long codigoProyecto;
    private UUID uuid;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private Boolean estado;

    // Constructor, getters y setters
    public ProyectoDTO() {}

    public ProyectoDTO(Long codigoProyecto, UUID uuid, String nombre, LocalDateTime fechaCreacion, Boolean estado) {
        this.codigoProyecto = codigoProyecto;
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
}