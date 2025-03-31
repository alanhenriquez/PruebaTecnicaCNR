package com.example.module.proyecto.service;

import com.example.module.proyecto.dto.ProyectoDTO;
import com.example.module.proyecto.model.Proyecto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProyectoService {

    /**
     * Crea un nuevo proyecto y lo persiste en la base de datos.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public Proyecto crearProyecto(ProyectoDTO proyectoDTO) {
        Proyecto proyecto = new Proyecto();
        proyecto.setUuid(UUID.randomUUID());
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setFechaCreacion(LocalDateTime.now());
        proyecto.setEstado(proyectoDTO.getEstado());
        proyecto.persist();
        return proyecto;
    }

    /**
     * Obtiene todos los proyectos almacenados en la base de datos.
     */
    public List<Proyecto> obtenerTodosLosProyectos() {
        return Proyecto.listAll();
    }

    /**
     * Obtiene un proyecto por su ID.
     */
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return Proyecto.findByIdOptional(id);
    }

    /**
     * Actualiza un proyecto existente.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public Proyecto actualizarProyecto(Long id, ProyectoDTO proyectoDTO) {
        Optional<Proyecto> optionalProyecto = Proyecto.findByIdOptional(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setNombre(proyectoDTO.getNombre());
            proyecto.setEstado(proyectoDTO.getEstado());
            return proyecto;
        }
        return null;
    }

    /**
     * Elimina un proyecto por su ID.
     * Este método es transaccional para garantizar la integridad de los datos.
     */
    @Transactional
    public boolean eliminarProyecto(Long id) {
        Optional<Proyecto> optionalProyecto = Proyecto.findByIdOptional(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.delete();
            return true;
        }
        return false;
    }

    /**
     * Inicializa datos predeterminados en la base de datos si no hay proyectos.
     * Este método es transaccional para garantizar que todos los proyectos predeterminados
     * se creen correctamente o ninguno se cree en caso de error.
     */
    @Transactional
    public void inicializarDatosPorDefecto() {
        long count = Proyecto.count();
        if (count == 0) {
            // Crear proyectos predeterminados
            ProyectoDTO proyecto1 = new ProyectoDTO(null, UUID.randomUUID(), "Proyecto Inicial 1", LocalDateTime.now(), true);
            ProyectoDTO proyecto2 = new ProyectoDTO(null, UUID.randomUUID(), "Proyecto Inicial 2", LocalDateTime.now(), true);

            crearProyecto(proyecto1);
            crearProyecto(proyecto2);
        }
    }
}