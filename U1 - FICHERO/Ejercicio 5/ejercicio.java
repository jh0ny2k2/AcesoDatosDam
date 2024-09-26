import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ejercicio {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        

        while (true ) {
            System.out.println("MENÚ");
            System.out.println("1. Crear un archivo");
            System.out.println("2. Añadir texto al final del archivo");
            System.out.println("3. Añadir texto al principio del archivo");
            System.out.println("4. salir");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    crearArchivo();
                    break;
                case 2:
                    añadirFinal();
                    break;
                case 3:
                    añadirInicio();
                    break;
            
                default:
                    break;
            }
        }

    }

    public static void crearArchivo(){

        System.out.println("Ingrese el nombre del archivo");
        String nombre = sc.nextLine() + ".txt";
        System.out.println("Dime que quieres poner dentro del archivo");
        String contenido = sc.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombre))) {
            writer.write(contenido);
            System.out.println("Archivo creado con éxito: " + nombre);
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    public static void añadirInicio() {
        System.out.println("Ingrese el nombre del archivo (sin extensión):");
        String nombre = sc.nextLine() + ".txt";
        System.out.println("Escribe el texto que deseas incluir:");
        String contenido = sc.nextLine();

        File fichero = new File(nombre);
        if (!fichero.exists()){
            System.out.println("El archivo no existe");
        }

        StringBuilder contenidoExistente = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contenidoExistente.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Escribir el nuevo contenido
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichero))) {
            writer.write(contenido);
            writer.write(System.lineSeparator());
            writer.write(contenidoExistente.toString());
            System.out.println("Texto añadido al principio del archivo: " + nombre);
        } catch (IOException e) {
            System.out.println("Error al añadir al principio del archivo: " + e.getMessage());
        }
    }

    public static void añadirFinal() {
        System.out.println("Ingrese el nombre del archivo (sin extensión):");
        String nombre = sc.nextLine() + ".txt";
        System.out.println("Escribe el texto que deseas incluir:");
        String contenido = sc.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombre, true))){
            bw.write(contenido);
            bw.newLine();
            System.out.println("Texto implementado al principio con exito");
        } catch (Exception e) {
            System.out.println("No se ha podido inplementar el texto");
        }
    }
}
