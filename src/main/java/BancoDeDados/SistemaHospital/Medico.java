package BancoDeDados.SistemaHospital;

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

    @Override
    public String toString() {
        return "Medico:" +
                "Código:" + codM +
                ", Nome: " + nome + '\'' +
                ", Especialidade: " + especialidade + '\'' +
                ", Turno:" + turno + '\'' +
                ", Salário: " + salario;
    }
}
