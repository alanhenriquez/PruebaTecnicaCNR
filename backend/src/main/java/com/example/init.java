package com.example;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.module.proyecto.model.Proyecto;
import com.example.module.proyecto.model.ProyectoDetalle;
import com.example.module.proyecto.service.ProyectoDetalleService;
import com.example.module.proyecto.service.ProyectoService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;



@Path("/init")
public class init {



    //=============================================================================================
    // INJECTS ------------------------------------------------------------------------------------
    //=============================================================================================



    @Inject
    ProyectoService proyectoService;

    @Inject
    ProyectoDetalleService proyectoDetalleService;



    //=============================================================================================
    // MAIN ---------------------------------------------------------------------------------------
    //=============================================================================================

    
    /**
     * Endpoint para inicializar la aplicación.
     * Retorna una respuesta JSON estandarizada.
     */
    @POST
    public Response initialize() {
        InitResponse apiResponse = new InitResponse();

        try {
            // Inicializar proyectos
            proyectoService.inicializarDatosPorDefecto();
            Map<String, String> detailsInit = new HashMap<>();
            detailsInit.put("proyectos", "Proyectos inicializados correctamente");



            // Inicializar detalles de proyectos
            proyectoDetalleService.inicializarDatosPorDefecto();
            detailsInit.put("detalles_proyectos", "Detalles de proyectos inicializados correctamente");



            // Obtener información de la base de datos
            Map<String, Object> detailsDb = obtenerInformacionBaseDatos();



            // Configurar respuesta exitosa
            apiResponse.setStatus("success");
            apiResponse.setMessage("Inicialización completada exitosamente");
            apiResponse.setDetailsInit(detailsInit);
            apiResponse.setDetailsDb(detailsDb);



            // Configurar respuesta de éxito
            return Response.ok(apiResponse).build();
        } catch (Exception e) {
            // Configurar respuesta de error
            Map<String, String> detailsError = new HashMap<>();
            detailsError.put("error_message", e.getMessage());
            detailsError.put("stack_trace", e.toString());

            apiResponse.setStatus("error");
            apiResponse.setMessage("La inicialización falló");
            apiResponse.setDetailsError(detailsError);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponse).build();
        }
    }



    //=============================================================================================
    // HELPERS ------------------------------------------------------------------------------------
    //=============================================================================================



    @PersistenceContext
    private EntityManager entityManager;



    /**
     * Obtiene el tamaño de una tabla en bytes.
     */
    private long obtenerTamanioTabla(String nombreTabla) {
        String sql = "SELECT pg_total_relation_size(:nombreTabla)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("nombreTabla", nombreTabla);
        return ((Number) query.getSingleResult()).longValue();
    }

    /**
     * Método para obtener información sobre la base de datos.
     */
    private Map<String, Object> obtenerInformacionBaseDatos() {
        Map<String, Object> detailsDb = new HashMap<>();

        // Obtener información de las tablas
        List<Map<String, Object>> tablas = List.of(
                Map.of(
                        "nombre_tabla", "proyecto",
                        "registros", Proyecto.count(),
                        "size_bytes", obtenerTamanioTabla("proyecto")),
                Map.of(
                        "nombre_tabla", "proyecto_detalle",
                        "registros", ProyectoDetalle.count(),
                        "size_bytes", obtenerTamanioTabla("proyecto_detalle")));

        // Calcular el tamaño total de todas las tablas
        long totalSizeBytes = tablas.stream()
                .mapToLong(tabla -> (Long) tabla.get("size_bytes"))
                .sum();

        detailsDb.put("tablas", tablas);
        detailsDb.put("total_size_bytes", totalSizeBytes);

        return detailsDb;
    }



    //=============================================================================================
    // MODEL RESPONSE -----------------------------------------------------------------------------
    //=============================================================================================



    public class InitResponse {

        private String status; // "success" o "error"
        private String message; // Mensaje general
        private Map<String, String> detailsError; // Detalles de errores (nullable)
        private Map<String, String> detailsInit; // Detalles de inicialización (nullable)
        private Map<String, Object> detailsDb; // Detalles de la base de datos (nullable)
    
        // Getters y setters
        public String getStatus() {
            return status;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    
        public Map<String, String> getDetailsError() {
            return detailsError;
        }
    
        public void setDetailsError(Map<String, String> detailsError) {
            this.detailsError = detailsError;
        }
    
        public Map<String, String> getDetailsInit() {
            return detailsInit;
        }
    
        public void setDetailsInit(Map<String, String> detailsInit) {
            this.detailsInit = detailsInit;
        }
    
        public Map<String, Object> getDetailsDb() {
            return detailsDb;
        }
    
        public void setDetailsDb(Map<String, Object> detailsDb) {
            this.detailsDb = detailsDb;
        }
    }

}