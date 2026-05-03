package BancoDeDados.AulaPratica01;

import java.time.LocalDate;

public class Consulta {
    private static int contador = 1;

    private int codC;
    private Paciente paciente; // FK vira objeto
    private Medico medico;     // FK vira objeto
    private LocalDate data;
    private String diagnostico;
    private double preco;

    public Consulta(Paciente paciente, Medico medico, LocalDate data, String diagnostico, double preco) {
        this.codC = contador ++;
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.diagnostico = diagnostico;
        this.preco = preco;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public int getCodC(){
        return this.codC;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Consulta: Paciente: "+ paciente.getNome()+
                " , Médico: "+ medico.getNome()+" , Data: "
                + getData()+" , Preço: "+ getPreco()+", Diagnóstico: "
                + getDiagnostico()+".";
    }
}
