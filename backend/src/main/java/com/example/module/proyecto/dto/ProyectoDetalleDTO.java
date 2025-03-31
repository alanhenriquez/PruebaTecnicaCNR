package com.example.module.proyecto.dto;

public class ProyectoDetalleDTO {

    private Long codigoProyectoDetalle;
    private String descripcion;
    private Integer area;
    private Boolean estado;
    private Long codigoProyecto; // Referencia al Proyecto

    // Constructor, getters y setters
    public ProyectoDetalleDTO() {}

    public ProyectoDetalleDTO(Long codigoProyectoDetalle, String descripcion, Integer area, Boolean estado, Long codigoProyecto) {
        this.codigoProyectoDetalle = codigoProyectoDetalle;
        this.descripcion = descripcion;
        this.area = area;
        this.estado = estado;
        this.codigoProyecto = codigoProyecto;
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

    public Long getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(Long codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }
}