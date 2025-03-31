package com.example.module.proyecto.resource;

import com.example.module.proyecto.dto.ProyectoCombinadoDTO;
import com.example.module.proyecto.service.ProyectoCombinadoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/proyectos-combinados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProyectoCombinadoResource {

    @Inject
    ProyectoCombinadoService proyectoCombinadoService;

    @GET
    public Response obtenerTodosLosProyectosCombinados() {
        List<ProyectoCombinadoDTO> proyectos = proyectoCombinadoService.obtenerTodosLosProyectosCombinados();
        if (proyectos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No se encontraron proyectos combinados.").build();
        }
        return Response.ok(proyectos).build();
    }
}