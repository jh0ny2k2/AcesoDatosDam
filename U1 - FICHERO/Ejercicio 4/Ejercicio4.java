import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Ejercicio3 {

    public static void main(String[] args) {
        // Definimos la ruta de los archivos
        String ruta = "C:/Users/Jh0ny2k2/Desktop/DAM/ACESO A DATOS/tema1/Ejercicio 3/prueba/1";
        String textoBuscado = "ejemplo"; // Texto que queremos buscar

        // Si se proporciona un argumento, lo usamos como ruta
        if (args.length > 0) {
            ruta = args[0];
        }

        // Si se proporciona un segundo argumento, lo usamos como texto buscado
        if (args.length > 1) {
            textoBuscado = args[1];
        }

        // Creamos un objeto File con la ruta
        File fich = new File(ruta);

        // Comprobamos si el archivo o directorio existe
        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio: " + ruta);
        } else {
            // Comprobamos si es un archivo
            if (fich.isFile()) {
                procesarArchivo(fich, textoBuscado);
            }
            // En el caso que sea un directorio
            else {
                // Imprimimos que es un directorio
                System.out.println(ruta + " es un directorio. Contenidos:");

                // Realizamos una lista de los archivos en el directorio
                File[] ficheros = fich.listFiles();

                // Comprobamos si existen archivos
                if (ficheros != null) {
                    // Recorremos todos los archivos
                    for (File f : ficheros) {
                        if (f.isFile()) {
                            procesarArchivo(f, textoBuscado);
                        }
                    }
                } else {
                    System.out.println("No se pudo acceder al contenido del directorio.");
                }
            }
        }
    }

    // Método para procesar el archivo: cuenta vocales y busca el texto
    public static void procesarArchivo(File fichero, String textoBuscado) {
        try {
            // Contar vocales
            int numeroVocales = contarVocales(fichero);
            System.out.println("Archivo: " + fichero.getName() + " - Número de vocales: " + numeroVocales);

            // Buscar texto y mostrar coincidencias
            buscarTextoEnArchivo(fichero, textoBuscado);

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + fichero.getName());
            e.printStackTrace();
        }
    }

    // Método para contar vocales en el archivo
    public static int contarVocales(File fichero) throws IOException {
        int contador = 0;

        try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
            int letra;
            while ((letra = br.read()) != -1) {
                char c = (char) letra;
                if (esVocal(c)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Método para buscar el texto dentro del archivo
    public static void buscarTextoEnArchivo(File fichero, String textoBuscado) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            int numeroLinea = 0;

            // Leer línea por línea
            while ((linea = br.readLine()) != null) {
                numeroLinea++;

                // Buscar el texto dentro de la línea
                int posicion = linea.indexOf(textoBuscado);
                while (posicion != -1) {
                    System.out.println("Texto encontrado en " + fichero.getName() +
                            " - Línea: " + numeroLinea + ", Columna: " + (posicion + 1));
                    posicion = linea.indexOf(textoBuscado, posicion + 1);
                }
            }
        }
    }

    // Método para verificar si un carácter es una vocal
    public static boolean esVocal(char letra) {
        return "aeiouAEIOUáéíóúÁÉÍÓÚ".indexOf(letra) != -1;
    }
}
