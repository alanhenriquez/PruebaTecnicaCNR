package com.example.services;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@ApplicationScoped
public class ServiceQuery {

    @Inject
    DataSource dataSource;

    /**
     * Ejecuta una consulta SQL y devuelve los resultados como una lista de mapas.
     *
     * @param query La consulta SQL a ejecutar.
     * @return Una lista de mapas que representan los resultados.
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Obtener los nombres de las columnas
            int columnCount = resultSet.getMetaData().getColumnCount();

            // Iterar sobre los resultados
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar la consulta: " + e.getMessage(), e);
        }
        return results;
    }
}