// aca se calcula el IMC entre otros
package service;
import model.Paciente;
import repository.PacienteRepository;
import java.util.List;

public class PacienteService {

    private PacienteRepository repositorio;

    public PacienteService() {
        this.repositorio = new PacienteRepository();
    }

    public Paciente registrarPaciente(String nombre, int edad, double peso, double estatura) {
        Paciente nuevoPaciente = new Paciente(nombre, edad, peso, estatura);
        nuevoPaciente.calcularImc();
        repositorio.guardar(nuevoPaciente);
        return nuevoPaciente;
    }



    // para tener la lista los pacientes
    public List<Paciente> listarPacientes() {
        return repositorio.obtenerTodos();
    }

    // elimina
    public void eliminarPaciente(int indice) {
        List<Paciente> lista = repositorio.obtenerTodos();
        if (indice >= 0 && indice < lista.size()) {
            lista.remove(indice); // Quitamos el paciente de la lista
            repositorio.reescribirArchivo(lista); // Guardamos la lista nueva
        }
    }

    // actualiza datos o quita paciente por uno nuevo
    public void actualizarPaciente(int indice, String nombre, int edad, double peso, double estatura) {
        List<Paciente> lista = repositorio.obtenerTodos();
        if (indice >= 0 && indice < lista.size()) {
            // Creamos el paciente con los datos corregidos
            Paciente pacienteActualizado = new Paciente(nombre, edad, peso, estatura);
            pacienteActualizado.calcularImc();

            lista.set(indice, pacienteActualizado);


            repositorio.reescribirArchivo(lista);
        }
    }
}