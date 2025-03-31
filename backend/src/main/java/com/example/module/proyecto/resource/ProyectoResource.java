package com.example.module.proyecto.resource;

import com.example.module.proyecto.dto.ProyectoDTO;
import com.example.module.proyecto.service.ProyectoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/proyectos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProyectoResource {

    @Inject
    ProyectoService proyectoService;

    @GET
    public Response obtenerTodosLosProyectos() {
        List<ProyectoDTO> proyectos = proyectoService.obtenerTodosLosProyectos();
        if (proyectos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No se encontraron proyectos.")
                    .build();
        }
        return Response.ok(proyectos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerProyectoPorId(@PathParam("id") Long id) {
        Optional<ProyectoDTO> optionalProyecto = proyectoService.obtenerProyectoPorId(id);
        if (optionalProyecto.isPresent()) {
            return Response.ok(optionalProyecto.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Proyecto con ID " + id + " no encontrado.")
                .build();
    }

    @POST
    public Response crearProyecto(ProyectoDTO proyectoDTO) {
        try {
            ProyectoDTO proyecto = proyectoService.crearProyecto(proyectoDTO);
            return Response.status(Response.Status.CREATED).entity(proyecto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el proyecto: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarProyecto(@PathParam("id") Long id, ProyectoDTO proyectoDTO) {
        ProyectoDTO proyecto = proyectoService.actualizarProyecto(id, proyectoDTO);
        if (proyecto != null) {
            return Response.ok(proyecto).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Proyecto con ID " + id + " no encontrado.")
                       .build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarProyecto(@PathParam("id") Long id) {
        boolean eliminado = proyectoService.eliminarProyecto(id);
        if (eliminado) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Proyecto con ID " + id + " no encontrado.")
                       .build();
    }
}