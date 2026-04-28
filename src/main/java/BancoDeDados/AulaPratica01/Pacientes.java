package BancoDeDados.AulaPratica01;

import java.util.Objects;

public class Pacientes {
    private static int contador = 1;

    private final int codP;
    private String nome;
    private String CPF;
    private Enum sexo;
    private int idade;
    private String convenio;

    public Pacientes(String nome, String CPF, Enum sexo, int idade, String convenio){
        this.codP = contador++;
        this.nome = nome;
        this.CPF = CPF;
        this.sexo = sexo;
        this.idade = idade;
        this.convenio = convenio;
    }

    public void setNome(String novoNome){
        this.nome = novoNome;
    }
    public void setCPF(String novoCpf){
        this.CPF = novoCpf;
    }
    public void setSexo(Enum novoSexo){
        this.sexo = novoSexo;
    }
    public void setIdade(int novaIdade){
        this.idade = novaIdade;
    }
    public void setConvenio(String novoConvenio){
        this.convenio = novoConvenio;
    }
    public int getCodP(){
        return this.codP;
    }
    public String getNome(){
        return this.nome;
    }
    public String getCPF(){
        return this.CPF;
    }
    public Enum getSexo(){
        return this.sexo;
    }
    public int getIdade(){
        return this.idade;
    }
    public String getConvenio(){
        return this.convenio;
    }

    @Override
    public String toString() {
        return "Paciente: " +
                "Código: " + codP +
                ", Nome: " + nome + '\'' +
                ", CPF: " + CPF + '\'' +
                ", Sexo: " + sexo + '\'' +
                ", Idade: " + idade +
                ", Convênio: " + convenio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pacientes pacientes = (Pacientes) o;
        return codP == pacientes.codP && Objects.equals(CPF, pacientes.CPF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codP, CPF);
    }
}
