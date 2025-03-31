package com.example.module.proyecto.service;

import com.example.module.proyecto.dto.ProyectoCombinadoDTO;
import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.model.Proyecto;
import com.example.module.proyecto.model.ProyectoDetalle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProyectoCombinadoService {

    // Obtener todos los proyectos combinados
    @Transactional
    public List<ProyectoCombinadoDTO> obtenerTodosLosProyectosCombinados() {
        List<Proyecto> proyectos = Proyecto.listAll();
        return proyectos.stream()
                .map(this::convertirAProyectoCombinadoDTO)
                .collect(Collectors.toList());
    }

    // Obtener un proyecto combinado por ID
        @Transactional
        public Optional<ProyectoCombinadoDTO> obtenerProyectoCombinadoPorId(Long id) {
        return Proyecto.findByIdOptional(id)
                .map(entity -> (Proyecto) entity) // Convertir PanacheEntityBase a Proyecto
                .map(this::convertirAProyectoCombinadoDTO);
        }

    // Crear un nuevo proyecto combinado
    @Transactional
    public ProyectoCombinadoDTO crearProyectoCombinado(ProyectoCombinadoDTO proyectoCombinadoDTO) {
        Proyecto proyecto = new Proyecto();
        proyecto.setUuid(proyectoCombinadoDTO.getUuid());
        proyecto.setNombre(proyectoCombinadoDTO.getNombre());
        proyecto.setFechaCreacion(proyectoCombinadoDTO.getFechaCreacion());
        proyecto.setEstado(proyectoCombinadoDTO.getEstado());
        proyecto.persist();

        // Asociar detalles al proyecto
        if (proyectoCombinadoDTO.getDetalles() != null) {
            proyectoCombinadoDTO.getDetalles().forEach(detalleDTO -> {
                ProyectoDetalle detalle = new ProyectoDetalle();
                detalle.setDescripcion(detalleDTO.getDescripcion());
                detalle.setArea(detalleDTO.getArea());
                detalle.setEstado(detalleDTO.getEstado());
                detalle.setProyecto(proyecto);
                detalle.persist();
            });
        }

        return convertirAProyectoCombinadoDTO(proyecto);
    }

    // Actualizar un proyecto combinado existente
    @Transactional
    public ProyectoCombinadoDTO actualizarProyectoCombinado(Long id, ProyectoCombinadoDTO proyectoCombinadoDTO) {
        Proyecto proyecto = Proyecto.findById(id);
        if (proyecto == null) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + id);
        }

        proyecto.setNombre(proyectoCombinadoDTO.getNombre());
        proyecto.setEstado(proyectoCombinadoDTO.getEstado());

        // Actualizar detalles asociados
        if (proyectoCombinadoDTO.getDetalles() != null) {
            proyecto.getDetalles().clear(); // Limpiar detalles antiguos
            proyectoCombinadoDTO.getDetalles().forEach(detalleDTO -> {
                ProyectoDetalle detalle = new ProyectoDetalle();
                detalle.setDescripcion(detalleDTO.getDescripcion());
                detalle.setArea(detalleDTO.getArea());
                detalle.setEstado(detalleDTO.getEstado());
                detalle.setProyecto(proyecto);
                detalle.persist();
            });
        }

        return convertirAProyectoCombinadoDTO(proyecto);
    }

    // Eliminar un proyecto combinado por ID
    @Transactional
    public boolean eliminarProyectoCombinado(Long id) {
        Proyecto proyecto = Proyecto.findById(id);
        if (proyecto == null) {
            return false;
        }
        proyecto.delete();
        return true;
    }

    // Método auxiliar para convertir un Proyecto a ProyectoCombinadoDTO
    private ProyectoCombinadoDTO convertirAProyectoCombinadoDTO(Proyecto proyecto) {
        List<ProyectoDetalleDTO> detallesDTO = proyecto.getDetalles().stream()
                .map(detalle -> new ProyectoDetalleDTO(
                        detalle.getCodigoProyectoDetalle(),
                        detalle.getDescripcion(),
                        detalle.getArea(),
                        detalle.getEstado(),
                        detalle.getProyecto().getCodigoProyecto()))
                .collect(Collectors.toList());
    
        return new ProyectoCombinadoDTO(
                proyecto.getCodigoProyecto(),
                proyecto.getUuid(),
                proyecto.getNombre(),
                proyecto.getFechaCreacion(),
                proyecto.getEstado(),
                detallesDTO);
    }
}