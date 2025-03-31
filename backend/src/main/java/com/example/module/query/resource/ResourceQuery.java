package com.example.module.query.resource;



import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import com.example.module.query.service.ServiceQuery;



@Path("/api/query")
@Produces("application/json")
@Consumes("application/json")
public class ResourceQuery {



    @Inject
    ServiceQuery queryService;

    /**
     * Endpoint para ejecutar consultas SQL personalizadas.
     *
     * @param query La consulta SQL enviada en el cuerpo de la solicitud.
     * @return Los resultados de la consulta en formato JSON.
     */
    @POST
    public Response executeQuery(Map<String, String> request) {
        String query = request.get("query");
        if (query == null || query.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("La consulta SQL no puede estar vacía.")
                           .build();
        }

        try {
            List<Map<String, Object>> results = queryService.executeQuery(query);
            return Response.ok(results).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al ejecutar la consulta: " + e.getMessage())
                           .build();
        }
    }
}