package com.example.apis;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/hello2")
public class GreetingResource2 {

    @Inject
    DataSource dataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST 2 (Updated) and tested with Postman, YUUHUU! nueva prueba";
    }

    @GET
    @Path("/test-db")
    @Produces(MediaType.TEXT_PLAIN)
    public String testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                return "Conexión exitosa a la base de datos";
            } else {
                return "La conexión a la base de datos falló";
            }
        } catch (SQLException e) {
            return "Error al conectar con la base de datos: " + e.getMessage();
        }
    }
}