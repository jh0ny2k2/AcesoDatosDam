// IMPORTAMOS LA EXTENSION JAVA FILE
import java.io.File;
import java.io.FileReader;

// INICIAMOS LA CLASE JAVAAPLICATION3
public class Ejercicio3 {

    //CREAMOS EL MÉTODO MAIN DE LA CLASE
    public static void main(String[] args)
    {
        // DEFINIMOS LA RUTA DE LOS ARCHIVOS"
        String ruta = "C:/Users/Jh0ny2k2/Desktop/DAM/ACESO A DATOS/tema1/Ejercicio 3/prueba/1"; // ARCHIVO
        //String ruta = "C:/ruta a la carpeta/tema1/media"; // DIRECCTORIO

        // SI SE PROPORCIONA UN ARGUMENTO, LO USAMOS COMO RUTA
        if (args.length > 0) ruta = args[0];
        
        // CREAMOS UN OBJETO FILE CON LA RUTA
        File fich = new File(ruta);
        
        // COMPROBAMOS SI EL ARCHIVO O DIRECTORIO EXISTE
        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio: " + ruta);
        }
        else {
            // CONFIGURAMOS UN TEXTO BASE
            String text = "";

            // COMPROBAMOS SI ES UN ARCHIVO
            if (fich.isFile()) {
                text += ruta + " es un fichero,";

                // COMPROBAMOS SI EL ARCHIVO SE PUEDE LEER
                if (fich.canRead()) {
                    text += " Se puede leer,";
                } else {
                    text += " No se puede leer,";
                }

                // COMPROBAMOS SI EL ARCHIVO SE PUEDE ESCRIBIR
                if (fich.canWrite()) {
                    text += " Se puede escribir,";
                } else {
                    text += " No se puede escribir,";
                }

                // COMPROBAMOS SI EL ARCHIVO ES EJECUTABLE
                if (fich.canExecute()) {
                    text += " Es ejecutable,";
                } else {
                    text += " No es ejecutable,";
                }

                // OBTENEMOS LA ÚLTIMA FECHA DE MODIFICACIÓN
                text += " Última modificación: " + fich.lastModified();

                // TEXTO DENTRO DEL ARCHIVO
                int numeroVocales = contarVocales(fich);

                text += "el numero de vocales es: " + numeroVocales;


                // PRINTEAMOS EL TEXTO FINAL
                System.out.println(text);
            }


            // EN EL CASO QUE SEA UN DIRECTORIO
            else {

                // PRINTEAMOS QUE ES UN DIRECTORIO
                System.out.println(ruta + " es un directorio. Contenidos:");

                //  REALIZAMOS UNA LISTA DE LOS FILES EXISTENTES EN EL DIRECTORIO
                File[] ficheros = fich.listFiles();

                // COMPROBAMOS SI EXISTE EL FICHERO
                if (ficheros != null) {

                    // HACEMOS UN FOR PARA RECORRER TODOS LOS FICHEROS 
                    for (File f : ficheros) {

                        // PRINTEAMOS LOS DATOS DE LOS FICHEROS EXISTENTES
                        System.out.println(f.lastModified() + " - " + f.getName() + " - " + f.getTotalSpace() + " - " + f.toString());
                    }

                } else { // EN EL CASO DE QUE NO SE PUEDE ACCEDER AL DIRECTORIO

                    // PRINTEAMOS QUE NO SE PUEDE ACCEDER 
                    System.out.println("No se pudo acceder al contenido del directorio.");
                }
            }
        }   
        
        
    }  
    public static int contarVocales(File fichero) {
        int contador = 0;

        try (FileReader fil = new FileReader(fichero);) {
            int letra;

            while ((letra = fil.read()) != -1) {
                char c = (char) letra;
                if (esVocal(c)) {
                    contador ++;
                }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return contador;
    }


    public static boolean esVocal(char letra) {
        return "aeiouAEIOUáéóúíÁÉÍÓÚ".indexOf(letra) != -1;
    }
    
    
}