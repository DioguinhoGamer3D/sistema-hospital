package BancoDeDados.AulaPratica01;

import BancoDeDados.AulaPratica01.Exceptions.EntidadeJaExiste;
import BancoDeDados.AulaPratica01.Exceptions.EntidadeNaoExiste;

import java.time.LocalDate;
import java.util.*;

public class GerenciaHospital implements Hospital {

    private Map<Integer, Pacientes> pacientes;
    private Map<Integer, Medicos> medicos;
    private Map<Integer, Consultas> consultas;
    private Set<String> cpfsBanidos;

    public GerenciaHospital() {
        this.pacientes = new HashMap<>();
        this.medicos = new HashMap<>();
        this.consultas = new HashMap<>();
        this.cpfsBanidos = new HashSet<>();
    }

    @Override
    public Pacientes cadastrarPaciente(String nome, String CPF, Enum sexo, int idade, String convenio)
            throws EntidadeJaExiste {
        if (cpfsBanidos.contains(CPF)) {
            throw new RuntimeException("CPF banido!");
        }
        for (Pacientes p : pacientes.values()) {
            if (p.getCPF().equals(CPF)) {
                throw new EntidadeJaExiste("Paciente", CPF);
            }
        }
        Pacientes novo = new Pacientes(nome, CPF, sexo, idade, convenio);
        pacientes.put(novo.getCodP(), novo);
        return novo;
    }

    @Override
    public Pacientes pesquisarPaciente(int codP) throws EntidadeNaoExiste{
        if (!pacientes.containsKey(codP)) {
            throw new EntidadeNaoExiste("Paciente ", codP);
        }else{
            return pacientes.get(codP);
        }
    }

    @Override
    public void removerPaciente(int codP) throws EntidadeNaoExiste {
        if (!pacientes.containsKey(codP)) {
            throw new EntidadeNaoExiste("Paciente", codP);
        } else {
            pacientes.remove(codP);
        }
    }

    @Override
    public Medicos cadastrarMedico(String nome, String CPF, Enum sexo, String especialidade, String turno, double salario)
            throws EntidadeJaExiste {
        for (Medicos m : medicos.values()) {
            if (m.getCPF().equals(CPF)) {
                throw new EntidadeJaExiste("Medico", CPF);
            }
        }
        Medicos novo = new Medicos(nome, CPF, sexo, especialidade, turno, salario);
        medicos.put(novo.getCodM(), novo);
        return novo;
    }

    public Medicos pesquisarMedico(int codM) throws EntidadeNaoExiste{
        if(!medicos.containsKey(codM)){
            throw new EntidadeNaoExiste("Medico", codM);
        } else {
            return medicos.get(codM);
        }
    }

    @Override
    public void removerMedico(int codM) throws EntidadeNaoExiste {
        if (!medicos.containsKey(codM)) {
            throw new EntidadeNaoExiste("Medico", codM);
        } else {
            medicos.remove(codM);
        }
    }

    @Override
    public Consultas cadastrarConsulta(Pacientes p, Medicos m, LocalDate data, String diagnostico, double preco)
            throws EntidadeNaoExiste {
        Pacientes paciente = pacientes.get(p.getCodP());
        if (paciente == null) {
            throw new EntidadeNaoExiste("Paciente", p.getCodP());
        }
        Medicos medico = medicos.get(m.getCodM());
        if (medico == null) {
            throw new EntidadeNaoExiste("Medico", m.getCodM());
        }
        Consultas nova = new Consultas(paciente, medico, data, diagnostico, preco);
        consultas.put(nova.getCodC(), nova);
        return nova;
    }

    public Consultas pesquisarConsulta(int codC) throws EntidadeNaoExiste{
        if(!consultas.containsKey(codC)){
            throw new EntidadeNaoExiste("Consulta", codC);
        } else{
            return consultas.get(codC);
        }
    }
    @Override
    public void removerConsulta(int codC) throws EntidadeNaoExiste {
        if (!consultas.containsKey(codC)) {
            throw new EntidadeNaoExiste("Consulta", codC);
        } else {
            consultas.remove(codC);
        }
    }

    @Override
    public void naoDeNemAgua(String CPF) {
        cpfsBanidos.add(CPF);
    }

    public List<Pacientes> todosPacientes(){
        return new ArrayList<>(pacientes.values());
    }
    public List<Medicos> todosMedicos(){
        return new ArrayList<>(medicos.values());
    }
    public  List<Consultas> todasConsultas(){
        return new ArrayList<>(consultas.values());
    }
}