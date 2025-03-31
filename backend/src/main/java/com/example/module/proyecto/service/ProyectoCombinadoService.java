package com.example.module.proyecto.service;

import com.example.module.proyecto.dto.ProyectoCombinadoDTO;
import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.model.Proyecto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProyectoCombinadoService {

    @Transactional
    public List<ProyectoCombinadoDTO> obtenerTodosLosProyectosCombinados() {
        List<Proyecto> proyectos = Proyecto.listAll();
        return proyectos.stream()
                .map(this::convertirAProyectoCombinadoDTO)
                .collect(Collectors.toList());
    }

    private ProyectoCombinadoDTO convertirAProyectoCombinadoDTO(Proyecto proyecto) {
        List<ProyectoDetalleDTO> detallesDTO = proyecto.getDetalles().stream()
                .map(detalle -> new ProyectoDetalleDTO(
                        detalle.getCodigoProyectoDetalle(),
                        detalle.getDescripcion(),
                        detalle.getArea(),
                        detalle.getEstado(),
                        proyecto.getCodigoProyecto() // Agregar el código del proyecto aquí
                ))
                .collect(Collectors.toList());
    
        return new ProyectoCombinadoDTO(
                proyecto.getCodigoProyecto(),
                proyecto.getUuid(),
                proyecto.getNombre(),
                proyecto.getFechaCreacion(),
                proyecto.getEstado(),
                detallesDTO
        );
    }
}