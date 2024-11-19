/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u3.ejercicio1dato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
/**
 *
 * @author Jhony
 */
public class U3Ejercicio3Datos {
    
    // Definición de constantes para la conexión
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/pruebadatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        U3Ejercicio3Datos datos = new U3Ejercicio3Datos();

        // Ejemplo de inserción de un usuario y varias licencias
        ArrayList<ArrayList<String>> licencias = new ArrayList<>();
        ArrayList<String> licencia1 = new ArrayList<>();
        licencia1.add("A1");
        licencia1.add("2024-10-26 10:00:00");
        licencia1.add("2025-10-26 10:00:00");
        licencias.add(licencia1);
        
        boolean success = datos.insertLicencias("12345678A", "Calle Falsa 123", "28000", "Juan Pérez", licencias);
        System.out.println("Inserción de usuario y licencias: " + (success ? "Exitosa" : "Fallida"));

        // Ejemplo de eliminación de licencias por DNI
        boolean deleted = datos.eliminarLicencias("12345678A");
        System.out.println("Eliminación de licencias: " + (deleted ? "Exitosa" : "Fallida"));
    }

    public boolean insertLicencias(String dni, String direccion, String cp, String nombre, ArrayList<ArrayList<String>> licencias) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Iniciar transacción

            // Insertar usuario
            String insertUsuarioSQL = "INSERT INTO usuarios (dni, direccion, cp, nombre) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertUsuarioSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, dni);
                stmt.setString(2, direccion);
                stmt.setString(3, cp);
                stmt.setString(4, nombre);
                stmt.executeUpdate();

                // Obtener el ID del nuevo usuario
                ResultSet rs = stmt.getGeneratedKeys();
                int usuarioId = 0;
                if (rs.next()) {
                    usuarioId = rs.getInt(1);
                }

                // Insertar licencias
                String insertLicenciaSQL = "INSERT INTO licencias (ID, TIPO, EXPEDICION, CADUCIDAD) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmtLicencia = connection.prepareStatement(insertLicenciaSQL)) {
                    for (ArrayList<String> licencia : licencias) {
                        stmtLicencia.setInt(1, usuarioId);
                        stmtLicencia.setString(2, licencia.get(0));
                        stmtLicencia.setTimestamp(3, Timestamp.valueOf(licencia.get(1)));
                        stmtLicencia.setTimestamp(4, Timestamp.valueOf(licencia.get(2)));
                        stmtLicencia.executeUpdate();
                    }
                }
            }
            connection.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            System.out.println("Error en insertLicencias: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Hacer rollback en caso de error
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Cerrar conexión
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public boolean eliminarLicencias(String dni) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Iniciar transacción

            // Obtener ID del usuario
            String selectUsuarioSQL = "SELECT id FROM usuarios WHERE dni = ?";
            int usuarioId = 0;
            try (PreparedStatement stmt = connection.prepareStatement(selectUsuarioSQL)) {
                stmt.setString(1, dni);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    usuarioId = rs.getInt("id");
                }
            }

            // Eliminar licencias
            String deleteLicenciaSQL = "DELETE FROM licencias WHERE ID = ?";
            try (PreparedStatement stmtLicencia = connection.prepareStatement(deleteLicenciaSQL)) {
                stmtLicencia.setInt(1, usuarioId);
                stmtLicencia.executeUpdate();
            }

            // Eliminar usuario
            String deleteUsuarioSQL = "DELETE FROM usuarios WHERE dni = ?";
            try (PreparedStatement stmtUsuario = connection.prepareStatement(deleteUsuarioSQL)) {
                stmtUsuario.setString(1, dni);
                stmtUsuario.executeUpdate();
            }

            connection.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            System.out.println("Error en eliminarLicencias: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Hacer rollback en caso de error
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Cerrar conexión
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
