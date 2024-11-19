import java.io.*;

public class TextProcessor {

    public static void main(String[] args) {
        File inputFile = new File("input.txt");  // Fichero de entrada
        File tempFile = null;

        try {
            // Crear un fichero temporal
            tempFile = File.createTempFile("tempFile", ".txt");
            
            // FileReader y BufferedReader para leer el archivo original
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // FileWriter y BufferedWriter para escribir en el archivo temporal
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean afterPeriod = false;  // Para controlar si estamos después de un punto

            // Leer línea por línea
            while ((line = reader.readLine()) != null) {
                // 1 - Forzar mayúsculas después de un punto
                StringBuilder processedLine = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);

                    if (afterPeriod && Character.isAlphabetic(c)) {
                        c = Character.toUpperCase(c);
                        afterPeriod = false;  // Resetear después de poner en mayúscula
                    }

                    if (c == '.') {
                        afterPeriod = true;  // El siguiente carácter al punto debe ser mayúscula
                    }

                    processedLine.append(c);
                }

                // 2 - Eliminar dobles/triples/etc. espacios en blanco
                String cleanedLine = processedLine.toString().replaceAll("\\s{2,}", " ");

                // Escribir la línea procesada en el archivo temporal
                writer.write(cleanedLine);
                writer.newLine();
            }

            // Cerrar los lectores y escritores
            reader.close();
            writer.close();

            // Renombrar el archivo temporal al archivo original (opcional)
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
                System.out.println("El fichero ha sido procesado y reemplazado.");
            } else {
                System.out.println("Error al eliminar el archivo original.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
