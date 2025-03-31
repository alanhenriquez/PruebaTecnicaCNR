package com.example.module.proyecto.resource;

import com.example.module.proyecto.dto.ProyectoDetalleDTO;
import com.example.module.proyecto.model.ProyectoDetalle;
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
     *
     * @return Lista de detalles de proyectos.
     */
    @GET
    public Response obtenerTodosLosDetalles() {
        List<ProyectoDetalle> detalles = proyectoDetalleService.obtenerTodosLosDetalles();
        if (detalles.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No se encontraron detalles de proyecto.")
                           .build();
        }
        return Response.ok(detalles).build();
    }

    /**
     * Endpoint para obtener un detalle de proyecto por su ID.
     *
     * @param id ID del detalle de proyecto.
     * @return Detalle de proyecto encontrado o respuesta 404 si no existe.
     */
    @GET
    @Path("/{id}")
    public Response obtenerDetallePorId(@PathParam("id") Long id) {
        Optional<ProyectoDetalle> optionalDetalle = proyectoDetalleService.obtenerDetallePorId(id);
        if (optionalDetalle.isPresent()) {
            return Response.ok(optionalDetalle.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Detalle de proyecto con ID " + id + " no encontrado.")
                       .build();
    }

    /**
     * Endpoint para crear un nuevo detalle de proyecto.
     *
     * @param proyectoDetalleDTO Datos del detalle de proyecto a crear.
     * @return Respuesta 201 Created con el detalle de proyecto creado.
     */
    @POST
    public Response crearProyectoDetalle(ProyectoDetalleDTO proyectoDetalleDTO) {
        try {
            ProyectoDetalle detalle = proyectoDetalleService.crearProyectoDetalle(proyectoDetalleDTO);
            return Response.status(Response.Status.CREATED).entity(detalle).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al crear el detalle de proyecto: " + e.getMessage())
                           .build();
        }
    }

    /**
     * Endpoint para actualizar un detalle de proyecto existente.
     *
     * @param id                  ID del detalle de proyecto a actualizar.
     * @param proyectoDetalleDTO  Datos actualizados del detalle de proyecto.
     * @return Respuesta 200 OK con el detalle de proyecto actualizado o 404 si no existe.
     */
    @PUT
    @Path("/{id}")
    public Response actualizarDetalle(@PathParam("id") Long id, ProyectoDetalleDTO proyectoDetalleDTO) {
        ProyectoDetalle detalle = proyectoDetalleService.actualizarDetalle(id, proyectoDetalleDTO);
        if (detalle != null) {
            return Response.ok(detalle).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Detalle de proyecto con ID "  + id + " no encontrado.")
                       .build();
    }

    /**
     * Endpoint para eliminar un detalle de proyecto existente.
     *
     * @param id ID del detalle de proyecto a eliminar.
     * @return Respuesta 204 No Content si se eliminó correctamente o 404 si no existe.
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