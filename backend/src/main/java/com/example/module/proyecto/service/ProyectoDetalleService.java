package com.example.module.proyecto.service;

import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.model.Proyecto;
import com.example.module.proyecto.model.ProyectoDetalle;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProyectoDetalleService {

    /**
     * Crea un nuevo detalle de proyecto y lo asocia con un proyecto existente.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public ProyectoDetalleDTO crearProyectoDetalle(ProyectoDetalleDTO proyectoDetalleDTO) {
        ProyectoDetalle detalle = new ProyectoDetalle();
        detalle.setDescripcion(proyectoDetalleDTO.getDescripcion());
        detalle.setArea(proyectoDetalleDTO.getArea());
        detalle.setEstado(proyectoDetalleDTO.getEstado());

        // Asociar con el Proyecto
        Optional<Proyecto> optionalProyecto = Proyecto.findByIdOptional(proyectoDetalleDTO.getCodigoProyecto());
        if (optionalProyecto.isPresent()) {
            detalle.setProyecto(optionalProyecto.get());
        } else {
            throw new RuntimeException("Proyecto no encontrado con ID: " + proyectoDetalleDTO.getCodigoProyecto());
        }

        detalle.persist(); // Persiste el detalle en la base de datos
        return convertirAProyectoDetalleDTO(detalle);
    }

    /**
     * Obtiene todos los detalles de proyectos almacenados en la base de datos.
     */
    public List<ProyectoDetalleDTO> obtenerTodosLosDetalles() {
        return ProyectoDetalle.listAll().stream()
                .map(proyectoDetalle -> convertirAProyectoDetalleDTO((ProyectoDetalle) proyectoDetalle)) // Casting explícito
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un detalle de proyecto por su ID.
     */
    public Optional<ProyectoDetalleDTO> obtenerDetallePorId(Long id) {
        return ProyectoDetalle.findByIdOptional(id)
                .map(proyectoDetalle -> convertirAProyectoDetalleDTO((ProyectoDetalle) proyectoDetalle)); // Casting explícito
    }

    /**
     * Actualiza un detalle de proyecto existente.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public ProyectoDetalleDTO actualizarDetalle(Long id, ProyectoDetalleDTO proyectoDetalleDTO) {
        Optional<ProyectoDetalle> optionalDetalle = ProyectoDetalle.findByIdOptional(id);
        if (optionalDetalle.isPresent()) {
            ProyectoDetalle detalle = optionalDetalle.get();
            detalle.setDescripcion(proyectoDetalleDTO.getDescripcion());
            detalle.setArea(proyectoDetalleDTO.getArea());
            detalle.setEstado(proyectoDetalleDTO.getEstado());

            // Actualizar referencia al Proyecto
            Optional<Proyecto> optionalProyecto = Proyecto.findByIdOptional(proyectoDetalleDTO.getCodigoProyecto());
            if (optionalProyecto.isPresent()) {
                detalle.setProyecto(optionalProyecto.get());
            } else {
                throw new RuntimeException("Proyecto no encontrado con ID: " + proyectoDetalleDTO.getCodigoProyecto());
            }

            return convertirAProyectoDetalleDTO(detalle);
        }
        return null;
    }

    /**
     * Elimina un detalle de proyecto por su ID.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public boolean eliminarDetalle(Long id) {
        Optional<ProyectoDetalle> optionalDetalle = ProyectoDetalle.findByIdOptional(id);
        if (optionalDetalle.isPresent()) {
            ProyectoDetalle detalle = optionalDetalle.get();
            detalle.delete();
            return true;
        }
        return false;
    }

    /**
     * Inicializa datos predeterminados en la base de datos si no hay detalles de proyectos.
     * Este método es transaccional para garantizar que todos los detalles predeterminados
     * se creen correctamente o ninguno se cree en caso de error.
     */
    @Transactional
    public void inicializarDatosPorDefecto() {
        long count = ProyectoDetalle.count();
        if (count == 0) {
            // Buscar un proyecto existente para asociar los detalles
            Optional<Proyecto> optionalProyecto = Proyecto.find("nombre", "Proyecto Inicial 1").firstResultOptional();
            if (optionalProyecto.isPresent()) {
                Proyecto proyecto = optionalProyecto.get();

                // Crear detalles predeterminados
                ProyectoDetalleDTO detalle1 = new ProyectoDetalleDTO(null, "Detalle Inicial 1", 100, true, proyecto.getCodigoProyecto());
                ProyectoDetalleDTO detalle2 = new ProyectoDetalleDTO(null, "Detalle Inicial 2", 200, true, proyecto.getCodigoProyecto());

                crearProyectoDetalle(detalle1);
                crearProyectoDetalle(detalle2);
            } else {
                throw new RuntimeException("No se encontró el proyecto 'Proyecto Inicial 1' para inicializar detalles.");
            }
        }
    }

    /**
     * Convierte una entidad ProyectoDetalle a un DTO ProyectoDetalleDTO.
     */
    private ProyectoDetalleDTO convertirAProyectoDetalleDTO(ProyectoDetalle detalle) {
        return new ProyectoDetalleDTO(
                detalle.getCodigoProyectoDetalle(),
                detalle.getDescripcion(),
                detalle.getArea(),
                detalle.getEstado(),
                detalle.getProyecto().getCodigoProyecto() // Solo incluir el ID del proyecto
        );
    }
}