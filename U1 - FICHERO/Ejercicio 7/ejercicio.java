import java.io.*;

public class ejercicio {
    public static void convertFile(String inputFilePath, String inputEncoding, String outputFilePath, String outputEncoding) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // Crear un BufferedReader para leer el archivo de entrada
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), inputEncoding));

            // Crear un BufferedWriter para escribir el archivo de salida
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), outputEncoding));

            String line;
            // Leer línea por línea del archivo de entrada y escribir en el archivo de salida
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();  // Escribir una nueva línea en el archivo de salida
            }

            System.out.println("Archivo convertido exitosamente.");

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            try {
                // Cerrar los recursos si están abiertos
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Validar que se han pasado los 4 parámetros necesarios
        if (args.length != 4) {
            System.out.println("Uso: java FileConverter <inputFilePath> <inputEncoding> <outputFilePath> <outputEncoding>");
            System.out.println("Encodings permitidos: ASCII, UTF-8, UTF-16, ISO-8859-1");
            return;
        }

        String inputFilePath = args[0];
        String inputEncoding = args[1];
        String outputFilePath = args[2];
        String outputEncoding = args[3];

        // Validar que los encodings sean correctos
        if (!isValidEncoding(inputEncoding) || !isValidEncoding(outputEncoding)) {
            System.out.println("Encodings permitidos: ASCII, UTF-8, UTF-16, ISO-8859-1");
            return;
        }

        // Ejecutar la conversión de archivos
        convertFile(inputFilePath, inputEncoding, outputFilePath, outputEncoding);
    }

    // Método para validar si el encoding es uno de los permitidos
    private static boolean isValidEncoding(String encoding) {
        return encoding.equalsIgnoreCase("ASCII")
                || encoding.equalsIgnoreCase("UTF-8")
                || encoding.equalsIgnoreCase("UTF-16")
                || encoding.equalsIgnoreCase("ISO-8859-1");
    }
}
