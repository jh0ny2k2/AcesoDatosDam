/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u3.ejercicio1dato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Statement;

/**
 *
 * @author Jhony
 */
public class U3Eejercicio2Datos {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/pruebadatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        // Ejemplo de uso de los nuevos métodos
        ArrayList<ArrayList<String>> licencias = new ArrayList<>();
        ArrayList<String> licencia1 = new ArrayList<>();
        licencia1.add("A1");
        licencia1.add("2024-01-01 00:00:00");
        licencia1.add("2026-01-01 00:00:00");
        licencias.add(licencia1);

        boolean success = insertLicencias("12345678A", "Calle Falsa 123", "28001", "Juan Pérez", licencias);
        System.out.println("Insertar usuario y licencias: " + (success ? "Éxito" : "Error"));

        boolean deleteSuccess = eliminarLicencias("12345678A");
        System.out.println("Eliminar licencias: " + (deleteSuccess ? "Éxito" : "Error"));
    }

    public static boolean insertLicencias(String dni, String direccion, String cp, String nombre, ArrayList<ArrayList<String>> licencias) {
        String insertUsuarioSQL = "INSERT INTO usuarios (dni, direccion, cp, nombre) VALUES (?, ?, ?, ?)";
        String insertLicenciaSQL = "INSERT INTO licencias (id, tipo, expedicion, caducidad) VALUES (?, ?, ?, ?)";
        boolean result = false;
        Connection connection = null; // Declarar la conexión aquí

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Iniciar la transacción

            // Insertar usuario
            try (PreparedStatement stmtUsuario = connection.prepareStatement(insertUsuarioSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, dni);
                stmtUsuario.setString(2, direccion);
                stmtUsuario.setString(3, cp);
                stmtUsuario.setString(4, nombre);
                stmtUsuario.executeUpdate();

                // Obtener el ID del usuario insertado
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1); // ID generado

                    // Insertar licencias
                    try (PreparedStatement stmtLicencia = connection.prepareStatement(insertLicenciaSQL)) {
                        for (ArrayList<String> licencia : licencias) {
                            stmtLicencia.setInt(1, userId);
                            stmtLicencia.setString(2, licencia.get(0));
                            stmtLicencia.setTimestamp(3, Timestamp.valueOf(licencia.get(1)));
                            stmtLicencia.setTimestamp(4, Timestamp.valueOf(licencia.get(2)));
                            stmtLicencia.executeUpdate();
                        }
                    }
                }
            }

            connection.commit(); // Confirmar transacción
            result = true;
        } catch (SQLException e) {
            System.out.println("Error en insertLicencias: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback(); // Hacer rollback en caso de error
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Cerrar la conexión
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return result;
    }

    public static boolean eliminarLicencias(String dni) {
        String deleteLicenciasSQL = "DELETE FROM licencias WHERE id = (SELECT id FROM usuarios WHERE dni = ?)";
        boolean result = false;
        Connection connection = null; // Declarar la conexión aquí

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Iniciar la transacción

            try (PreparedStatement stmt = connection.prepareStatement(deleteLicenciasSQL)) {
                stmt.setString(1, dni);
                stmt.executeUpdate();
            }

            connection.commit(); // Confirmar transacción
            result = true;
        } catch (SQLException e) {
            System.out.println("Error en eliminarLicencias: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback(); // Hacer rollback en caso de error
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Cerrar la conexión
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return result;
    }
}
