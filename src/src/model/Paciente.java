//nuestra clase model.Paciente contiene los datos del pasiente para su calculo y registro
package model;

public class Paciente {
    private String nombre;
    private int edad;
    private double peso;
    private double estatura;
    private double imc;
    private String interpretacion;




    public Paciente(String nombre, int edad, double peso, double estatura) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.estatura = estatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }
    public String getInterpretacion() {
        return interpretacion;
    }

    public void setInterpretacion(String interpretacion) {
        this.interpretacion = interpretacion;
    }


    // define el tipo de peso en el paciente
    public void calcularPeso() {
        if (this.imc < 18.5) {
            this.interpretacion = "Bajo peso";
        } else if (this.imc >= 18.5 && this.imc <= 24.9) {
            this.interpretacion = "Normal";
        } else if (this.imc >= 25 && this.imc <= 29.9) {
            this.interpretacion = "Sobrepeso";
        } else {
            this.interpretacion = "Obesidad";
        }
    }

    // se crea un metodo que solo calcula el imc -> abstracción
    public void calcularImc() {
        this.imc = this.peso / (this.estatura * this.estatura);

        calcularPeso();
    }


}


