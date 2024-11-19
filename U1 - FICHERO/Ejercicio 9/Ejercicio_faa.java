import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ejercicio_faa {

    private File f;
    private List<String> campos;
    private List<Integer> camposLength; // Longitudes de los campos
    private long longReg;       // Bytes por registro
    private long numReg = 0;    // Número de registros dentro del fichero

    // Constructor
    Ejercicio_faa(String path, List<String> campos, List<Integer> camposLength) throws IOException {
        this.campos = campos;
        this.camposLength = camposLength;

        this.f = new File(path);
        this.longReg = 0;

        // Calcular la longitud total de un registro
        for (Integer campo : camposLength) {
            this.longReg += campo;
        }

        if (f.exists()) {
            this.numReg = f.length() / this.longReg;
        }
    }

    // Obtener el número de registros en el archivo
    public long getNumReg() {
        return numReg;
    }

    // Método para insertar un nuevo registro en la última posición
    public void insertar(Map<String, String> reg) throws IOException {
        insertar(reg, this.numReg++);
    }

    // Método para insertar un registro en una posición específica
    public void insertar(Map<String, String> reg, long pos) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {

            // Posicionar el puntero en la posición correcta para escribir
            rndFile.seek(pos * this.longReg);

            int total = campos.size();
            for (int i = 0; i < total; i++) {
                String nomCampo = campos.get(i);      // Nombre del campo
                Integer longCampo = camposLength.get(i); // Longitud del campo
                String valorCampo = reg.get(nomCampo);  // Valor del campo

                if (valorCampo == null) {
                    valorCampo = ""; // Si el valor es nulo, lo inicializamos a cadena vacía
                }

                // Formatear el valor para que tenga la longitud fija del campo
                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo);

                rndFile.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo); // Escribir en el archivo
            }

        } catch (Exception ex) {
            System.out.println("Error al insertar: " + ex.getMessage());
        }
    }

    // Método para leer un registro en una posición específica
    public Map<String, String> leer(long pos) throws IOException {
        Map<String, String> reg = new HashMap<>();

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {

            // Posicionarse para leer el registro en la posición dada
            rndFile.seek(pos * this.longReg);

            int total = campos.size();
            for (int i = 0; i < total; i++) {
                String nomCampo = campos.get(i);
                Integer longCampo = camposLength.get(i);

                byte[] buffer = new byte[longCampo];
                rndFile.read(buffer);

                // Convertir los bytes a String y quitar espacios en blanco
                String valorCampo = new String(buffer, "UTF-8").trim();

                reg.put(nomCampo, valorCampo);
            }

        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        return reg;
    }

    // Método para modificar un registro en una posición específica
    public void modificar(Map<String, String> reg, long pos) throws IOException {
        insertar(reg, pos);
    }

    // 1) selectCampo: Devuelve el valor de una columna específica de un registro
    public String selectCampo(int numRegistro, String nomColumna) throws IOException {
        Map<String, String> reg = leer(numRegistro);
        return reg.getOrDefault(nomColumna, null); // Devolvemos el valor o null si no existe
    }

    // 2) selectColumna: Devuelve una lista con todos los valores de una columna
    public List<String> selectColumna(String nomColumna) throws IOException {
        List<String> columna = new ArrayList<>();
        for (long i = 0; i < numReg; i++) {
            String valor = selectCampo((int) i, nomColumna);
            columna.add(valor);
        }
        return columna;
    }

    // 3) selectRowList: Devuelve una lista con los valores de un registro completo
    public List<String> selectRowList(int numRegistro) throws IOException {
        Map<String, String> reg = leer(numRegistro);
        return new ArrayList<>(reg.values());
    }

    // 4) selectRowMap: Devuelve un HashMap con los valores de un registro completo
    public Map<String, String> selectRowMap(int numRegistro) throws IOException {
        return leer(numRegistro);
    }

    // 5.1) update: Modifica un registro con todos los valores en el Map
    public void update(int row, Map<String, String> camposNuevos) throws IOException {
        insertar(camposNuevos, row);
    }

    // 5.2) update: Modifica un solo campo de un registro
    public void update(int row, String campo, String valor) throws IOException {
        Map<String, String> reg = leer(row);
        reg.put(campo, valor);
        insertar(reg, row);
    }

    // 6) delete: Limpia los datos de un registro
    public void delete(int row) throws IOException {
        Map<String, String> regVacio = new HashMap<>();
        for (String campo : campos) {
            regVacio.put(campo, ""); // Se llena el registro con valores vacíos
        }
        insertar(regVacio, row); // Inserta el registro vacío
    }

    public static void main(String[] args) {
        List<String> campos = new ArrayList<>();
        List<Integer> camposLength = new ArrayList<>();

        campos.add("DNI");
        campos.add("NOMBRE");
        campos.add("DIRECCION");
        campos.add("CP");

        camposLength.add(9);
        camposLength.add(32);
        camposLength.add(32);
        camposLength.add(5);

        try {
            Ejercicio_faa faa = new Ejercicio_faa("file_binario_2.dat", campos, camposLength);

            Map<String, String> reg = new HashMap<>();

            // PRIMER REGISTRO
            reg.put("DNI", "11111111A");
            reg.put("NOMBRE", "Nombre Apellido 1");
            reg.put("DIRECCION", "Calle 1, Nº 7");
            reg.put("CP", "54321");
            faa.insertar(reg, 0); // Insertamos en la posición 0

            // SEGUNDO REGISTRO
            reg.clear();
            reg.put("DNI", "22222222B");
            reg.put("NOMBRE", "Nombre Apellido 2");
            reg.put("DIRECCION", "Calle 2, Nº 8");
            reg.put("CP", "12345");
            faa.insertar(reg, 1); // Insertamos en la posición 1

            // SELECCIÓN DE CAMPOS Y FILAS
            String campoSeleccionado = faa.selectCampo(0, "NOMBRE");
            System.out.println("Campo NOMBRE del registro 0: " + campoSeleccionado);

            List<String> columnaSeleccionada = faa.selectColumna("NOMBRE");
            System.out.println("Todos los nombres: " + columnaSeleccionada);

            List<String> filaSeleccionada = faa.selectRowList(1);
            System.out.println("Fila completa (lista) del registro 1: " + filaSeleccionada);

            Map<String, String> filaSeleccionadaMap = faa.selectRowMap(1);
            System.out.println("Fila completa (map) del registro 1: " + filaSeleccionadaMap);

            // MODIFICACIÓN Y ELIMINACIÓN
            faa.update(1, "NOMBRE", "Nuevo Nombre Apellido 2");
            System.out.println("Fila modificada: " + faa.selectRowMap(1));

            faa.delete(1);
            System.out.println("Fila eliminada: " + faa.selectRowMap(1));

        } catch (IOException e) {
            System.out.print("Error: " + e.getMessage());
        }
    }
}
