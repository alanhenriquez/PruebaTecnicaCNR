package com.example.module.proyecto.resource;

import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.service.ProyectoDetalleService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/proyecto-detalles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProyectoDetalleResource {

    @Inject
    ProyectoDetalleService proyectoDetalleService;

    /**
     * Endpoint para obtener todos los detalles de proyectos.
     */
    @GET
    public Response obtenerTodosLosDetalles() {
        List<ProyectoDetalleDTO> detalles = proyectoDetalleService.obtenerTodosLosDetalles();
        if (detalles.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No se encontraron detalles de proyecto.")
                           .build();
        }
        return Response.ok(detalles).build();
    }

    /**
     * Endpoint para obtener un detalle de proyecto por su ID.
     */
    @GET
    @Path("/{id}")
    public Response obtenerDetallePorId(@PathParam("id") Long id) {
        Optional<ProyectoDetalleDTO> optionalDetalle = proyectoDetalleService.obtenerDetallePorId(id);
        if (optionalDetalle.isPresent()) {
            return Response.ok(optionalDetalle.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Detalle de proyecto con ID " + id + " no encontrado.")
                       .build();
    }

    /**
     * Endpoint para crear un nuevo detalle de proyecto.
     */
    @POST
    public Response crearProyectoDetalle(ProyectoDetalleDTO proyectoDetalleDTO) {
        try {
            ProyectoDetalleDTO detalle = proyectoDetalleService.crearProyectoDetalle(proyectoDetalleDTO);
            return Response.status(Response.Status.CREATED).entity(detalle).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al crear el detalle de proyecto: " + e.getMessage())
                           .build();
        }
    }

    /**
     * Endpoint para actualizar un detalle de proyecto existente.
     */
    @PUT
    @Path("/{id}")
    public Response actualizarDetalle(@PathParam("id") Long id, ProyectoDetalleDTO proyectoDetalleDTO) {
        ProyectoDetalleDTO detalle = proyectoDetalleService.actualizarDetalle(id, proyectoDetalleDTO);
        if (detalle != null) {
            return Response.ok(detalle).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Detalle de proyecto con ID " + id + " no encontrado.")
                       .build();
    }

    /**
     * Endpoint para eliminar un detalle de proyecto existente.
     */
    @DELETE
    @Path("/{id}")
    public Response eliminarDetalle(@PathParam("id") Long id) {
        boolean eliminado = proyectoDetalleService.eliminarDetalle(id);
        if (eliminado) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Detalle de proyecto con ID " + id + " no encontrado.")
                       .build();
    }
}