import java.io.File;
import java.util.Scanner;

public class Ejercicio2 {

    public static void main(String[] args) {

        // REALIZAMOS LA ESTRUCTURA
        try {
            // CREAMOS LOS DIRECTORIOS DE MADRE Y PADRE DE CADA ABUELO Y ABUELA
            new File("/abuelo/padre").mkdirs();
            new File("/abuelo/madre").mkdirs();
            new File("/abuela/padre").mkdirs();
            new File("/abuela/madre").mkdirs();

            // CREAMOS LOS FICHEROS PARA CADA MADRE Y PADRE DE CADA ABUELO Y ABUELA
            new File("/abuelo/padre/hijo1.txt").createNewFile();
            new File("/abuelo/padre/hija2.txt").createNewFile();
            new File("/abuelo/madre/hijo1.txt").createNewFile();
            new File("/abuelo/madre/hija2.txt").createNewFile();
            new File("/abuela/padre/hijo1.txt").createNewFile();
            new File("/abuela/padre/hija2.txt").createNewFile();
            new File("/abuela/madre/hijo1.txt").createNewFile();
            new File("/abuela/madre/hija2.txt").createNewFile();

        } catch (Exception e) {
            // MANDAMOS UNA EXCEPCIÓN POR SI DA ALGUN ERROR AL CREAR LO ANTERIOR
            System.out.println("No se ha podido crear los directorios y los ficheros");
        }

        File print = new File("/ejer");

        printear(print);

        // CREAMOS UN SCANNER PARA PEDIR POR TECLADO EL FICHERO QUE QUEREMOS BORRAR 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Dime el nombre del fichero a borrar: ");
        String fich = scanner.nextLine();
        scanner.close();

        // CREAMOS LA RUTA RAIZ DONDE QUEREMOS EMPEZAR LA BUSQUEDA DEL ARCHIVO
        File raiz = new File("/abuelo");

        borrarFichero(fich, raiz);

        printear(print);
        
    }

    public static boolean borrarFichero(String nombre, File directorio) {
        if (directorio.isDirectory()) {
            File[] ficheros = directorio.listFiles();
            if (ficheros != null) {
                for (File fichero : ficheros) {
                    if (fichero.isDirectory()) {
                            borrarFichero(nombre, fichero);
                    } else 
                        fichero.getName().equals(nombre);

                        if (fichero.delete()) {
                            System.out.println("Se ha eliminado el archivo con nombre: " + nombre);
                            return true;
                        } else {
                            System.out.println("No se ha podido borrar el fichero con nombre " + nombre);
                            
                    }
                }
            }
        }
        
        return false;
    } 


    public static void printear(File directorio) {
        // Verificar que el File es un directorio
        if (directorio.isDirectory()) {
            // Obtener la lista de archivos y subdirectorios
            File[] ficheros = directorio.listFiles();
            
            if (ficheros != null) {
                // Recorrer la lista de archivos y subdirectorios
                for (File fichero : ficheros) {
                    // Imprimir el nombre del archivo o directorio
                    System.out.println(fichero.getName());
                    
                    // Si es un directorio, llamar a printear recursivamente
                    if (fichero.isDirectory()) {
                        printear(fichero); // Llamada recursiva para subdirectorios
                    }
                }
            } else {
                System.out.println("El directorio está vacío o no se puede acceder.");
            }
        } else {
            System.out.println(directorio.getPath() + " no es un directorio.");
        }
    }
}
