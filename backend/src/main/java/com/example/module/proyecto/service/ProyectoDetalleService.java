package com.example.module.proyecto.service;

import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.model.Proyecto;
import com.example.module.proyecto.model.ProyectoDetalle;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProyectoDetalleService {

    /**
     * Crea un nuevo detalle de proyecto y lo asocia con un proyecto existente.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public ProyectoDetalle crearProyectoDetalle(ProyectoDetalleDTO proyectoDetalleDTO) {
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
        return detalle;
    }

    /**
     * Obtiene todos los detalles de proyectos almacenados en la base de datos.
     */
    public List<ProyectoDetalle> obtenerTodosLosDetalles() {
        return ProyectoDetalle.listAll();
    }

    /**
     * Obtiene un detalle de proyecto por su ID.
     */
    public Optional<ProyectoDetalle> obtenerDetallePorId(Long id) {
        return ProyectoDetalle.findByIdOptional(id);
    }

    /**
     * Actualiza un detalle de proyecto existente.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public ProyectoDetalle actualizarDetalle(Long id, ProyectoDetalleDTO proyectoDetalleDTO) {
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

            return detalle;
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
}