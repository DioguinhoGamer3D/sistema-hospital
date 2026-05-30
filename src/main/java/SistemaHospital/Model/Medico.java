package SistemaHospital.Model;

import java.util.Objects;

public class Medico {
    private static int contador = 1;

    private int codM;
    private String nome;
    private String CPF;
    private Enum sexo;
    private String especialidade;
    private String turno;
    private double salario;

    public Medico(String nome, String CPF, Enum sexo, String especialidade, String turno, double salario) {
        this.codM = contador++;
        this.nome = nome;
        this.CPF = CPF;
        this.sexo = sexo;
        this.especialidade = especialidade;
        this.turno = turno;
        this.salario = salario;
    }

    public int getCodM() {
        return codM;
    }

    public void setCodM(int codM) {
        this.codM = codM;
    }

    public String getCPF() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Enum getSexo() {
        return sexo;
    }

    public void setSexo(Enum sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Medico:" +
                "Código:" + codM +
                ", Nome: " + nome + '\'' +
                ", Especialidade: " + especialidade + '\'' +
                ", Turno:" + turno + '\'' +
                ", Salário: " + salario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return codM == medico.codM && Objects.equals(CPF, medico.CPF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codM, CPF);
    }
}
