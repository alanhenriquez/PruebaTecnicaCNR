package com.example.module.proyecto.resource;

import com.example.module.proyecto.dto.ProyectoCombinadoDTO;
import com.example.module.proyecto.service.ProyectoCombinadoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/proyectos-combinados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProyectoCombinadoResource {

    @Inject
    ProyectoCombinadoService proyectoCombinadoService;

    /**
     * Endpoint para obtener todos los proyectos combinados.
     */
    @GET
    public Response obtenerTodosLosProyectosCombinados() {
        List<ProyectoCombinadoDTO> proyectos = proyectoCombinadoService.obtenerTodosLosProyectosCombinados();
        if (proyectos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No se encontraron proyectos combinados.")
                    .build();
        }
        return Response.ok(proyectos).build();
    }

    /**
     * Endpoint para obtener un proyecto combinado por su ID.
     */
    @GET
    @Path("/{id}")
    public Response obtenerProyectoCombinadoPorId(@PathParam("id") Long id) {
        Optional<ProyectoCombinadoDTO> optionalProyecto = proyectoCombinadoService.obtenerProyectoCombinadoPorId(id);
        if (optionalProyecto.isPresent()) {
            return Response.ok(optionalProyecto.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Proyecto combinado con ID " + id + " no encontrado.")
                .build();
    }

    /**
     * Endpoint para crear un nuevo proyecto combinado.
     */
    @POST
    public Response crearProyectoCombinado(ProyectoCombinadoDTO proyectoCombinadoDTO) {
        try {
            ProyectoCombinadoDTO proyecto = proyectoCombinadoService.crearProyectoCombinado(proyectoCombinadoDTO);
            return Response.status(Response.Status.CREATED).entity(proyecto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el proyecto combinado: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Endpoint para actualizar un proyecto combinado existente.
     */
    @PUT
    @Path("/{id}")
    public Response actualizarProyectoCombinado(@PathParam("id") Long id, ProyectoCombinadoDTO proyectoCombinadoDTO) {
        try {
            ProyectoCombinadoDTO proyecto = proyectoCombinadoService.actualizarProyectoCombinado(id, proyectoCombinadoDTO);
            return Response.ok(proyecto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error al actualizar el proyecto combinado: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Endpoint para eliminar un proyecto combinado existente.
     */
    @DELETE
    @Path("/{id}")
    public Response eliminarProyectoCombinado(@PathParam("id") Long id) {
        boolean eliminado = proyectoCombinadoService.eliminarProyectoCombinado(id);
        if (eliminado) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Proyecto combinado con ID " + id + " no encontrado.")
                .build();
    }
}