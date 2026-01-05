//aca guardaremos los datos de los pacientes y poder hacer el CRUD
package repository;
import model.Paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {


    private static final String ARCHIVO = "pacientes.txt";

    // aca se guarda
    public void guardar(Paciente paciente) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            fw.write(formatearPaciente(paciente));
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // aca se lee los datos
    public List<Paciente> obtenerTodos() {
        List<Paciente> lista = new ArrayList<>();
        File file = new File(ARCHIVO);

        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separamos los datos por punto y coma
                String[] datos = linea.split(";");
                if (datos.length >= 6) {
                    // Reconstruimos el objeto Paciente
                    String nombre = datos[0];
                    int edad = Integer.parseInt(datos[1]);
                    double peso = Double.parseDouble(datos[2]);
                    double estatura = Double.parseDouble(datos[3]);


                    Paciente p = new Paciente(nombre, edad, peso, estatura);
                    p.calcularImc(); // se aseguran los datos
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return lista;
    }

    // aca se borra los datos o actualizan
    public void reescribirArchivo(List<Paciente> listaPacientes) {
        try (FileWriter fw = new FileWriter(ARCHIVO, false)) { // 'false' significa sobrescribir, no añadir
            for (Paciente p : listaPacientes) {
                fw.write(formatearPaciente(p));
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir: " + e.getMessage());
        }
    }

    // Ayuda a no repetir código de formato
    private String formatearPaciente(Paciente p) {
        return p.getNombre() + ";" +
                p.getEdad() + ";" +
                p.getPeso() + ";" +
                p.getEstatura() + ";" +
                p.getImc() + ";" +
                p.getInterpretacion() + "\n";
    }
}





