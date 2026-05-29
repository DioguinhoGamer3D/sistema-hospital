package SistemaHospital.Gerencias;

import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.EntidadeJaExiste;
import SistemaHospital.Exceptions.EntidadeNaoExiste;

import java.time.LocalDate;
import java.util.*;

public class GerenciaHospital implements Hospital {

    private Map<Integer, Paciente> pacientes;
    private Map<Integer, Medico> medicos;
    private Map<Integer, Consulta> consultas;
    private Set<String> cpfsBanidos;

    public GerenciaHospital() {
        this.pacientes = new HashMap<>();
        this.medicos = new HashMap<>();
        this.consultas = new HashMap<>();
        this.cpfsBanidos = new HashSet<>();
    }

    @Override
    public Paciente cadastrarPaciente(String nome, String CPF, Sexo sexo, int idade, String convenio)
            throws EntidadeJaExiste {
        if (cpfsBanidos.contains(CPF)) {
            throw new RuntimeException("CPF banido!");
        }
        for (Paciente p : pacientes.values()) {
            if (p.getCPF().equals(CPF)) {
                throw new EntidadeJaExiste("Paciente", CPF);
            }
        }
        Paciente novo = new Paciente(nome, CPF, sexo, idade, convenio);
        pacientes.put(novo.getCodP(), novo);
        return novo;
    }

    @Override
    public Paciente pesquisarPaciente(int codP) throws EntidadeNaoExiste{
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
    public Medico cadastrarMedico(String nome, String CPF, Sexo sexo, String especialidade, String turno, double salario)
            throws EntidadeJaExiste {
        for (Medico m : medicos.values()) {
            if (m.getCPF().equals(CPF)) {
                throw new EntidadeJaExiste("Medico", CPF);
            }
        }
        Medico novo = new Medico(nome, CPF, sexo, especialidade, turno, salario);
        medicos.put(novo.getCodM(), novo);
        return novo;
    }

    public Medico pesquisarMedico(int codM) throws EntidadeNaoExiste{
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
    public Consulta cadastrarConsulta(Paciente p, Medico m, LocalDate data, String diagnostico, double preco)
            throws EntidadeNaoExiste {
        Paciente paciente = pacientes.get(p.getCodP());
        if (paciente == null) {
            throw new EntidadeNaoExiste("Paciente", p.getCodP());
        }
        Medico medico = medicos.get(m.getCodM());
        if (medico == null) {
            throw new EntidadeNaoExiste("Medico", m.getCodM());
        }
        if (consultaJaExiste(paciente, medico, data)) {
            throw new EntidadeJaExiste("Consulta", data);
        }
        Consulta nova = new Consulta(paciente, medico, data, diagnostico, preco);
        consultas.put(nova.getCodC(), nova);
        return nova;
    }

    public Consulta pesquisarConsulta(int codC) throws EntidadeNaoExiste{
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

    public List<Paciente> todosPacientes(){
        return new ArrayList<>(pacientes.values());
    }
    public List<Medico> todosMedicos(){
        return new ArrayList<>(medicos.values());
    }
    public  List<Consulta> todasConsultas(){
        return new ArrayList<>(consultas.values());
    }

    public List<Consulta> consultasPorPaciente(int codP) throws EntidadeNaoExiste{
        List<Consulta> consultaPaciente = new ArrayList<>();
        for(Consulta c: consultas.values()) {
            if (c.getPaciente().getCodP() == codP) {
                consultaPaciente.add(c);
            }
        } if (consultaPaciente.isEmpty()){
            throw new EntidadeNaoExiste("Consultas", codP);
        }
        return consultaPaciente;
    }

    public List<Consulta> consultasPorMedico(int codM) throws EntidadeNaoExiste{
        List<Consulta> consultaMedico = new ArrayList<>();
        for(Consulta c: consultas.values()){
            if(c.getMedico().getCodM() == codM){
                consultaMedico.add(c);
            }
        } if (consultaMedico.isEmpty()){
            throw new EntidadeNaoExiste("Consultas", codM);
        }
        return consultaMedico;
    }

    public List<Consulta> consultasPorData(LocalDate data) throws EntidadeNaoExiste{
        List<Consulta> consultaData = new ArrayList<>();
        for (Consulta c: consultas.values()){
            if (c.getData().equals(data)){
                consultaData.add(c);
            }
        } if (consultaData.isEmpty()){
            throw new EntidadeNaoExiste("Consulta", data);
        }
        return consultaData;
    }
    public Paciente buscarPacientePorCpf(String cpf) throws EntidadeNaoExiste{
        for (Paciente p: pacientes.values()){
            if (p.getCPF().equals(cpf)){
                return p;
            }
        }
        throw new EntidadeNaoExiste("Paciente", cpf);
    }

    public boolean consultaJaExiste(Paciente p, Medico m, LocalDate data){
        for (Consulta c: consultas.values()){
            if (c.getPaciente().equals(p) && c.getMedico().equals(m) && c.getData().equals(data)){
                return true;
            }
        }
        return false;
    }

    public boolean medicoDisponivel(Medico m, LocalDate data) throws EntidadeNaoExiste{
        if (medicos.get(m.getCodM()) == m){
            throw new EntidadeNaoExiste("Medico", m.getCodM());
            }
        for(Consulta c: consultas.values()){
                if(c.getMedico().equals(m) && c.getData().equals(data)){
                    return false;
                }
            }
        return true;
    }

    public boolean pacientePodeConsultar(String cpf) throws EntidadeNaoExiste{
        buscarPacientePorCpf(cpf);
        return !cpfsBanidos.contains(cpf);
    }

    public double calcularFaturamento(){
        double totalFaturamento = 0;
        for (Consulta c: consultas.values()){
            totalFaturamento+= c.getPreco();
        }
        return totalFaturamento;
    }

    public double faturamentoPorMedico(int codM) throws EntidadeNaoExiste{
        if (!medicos.containsKey(codM)){
            throw new EntidadeNaoExiste("Medico", codM);
        }
        double total = 0;
        for (Consulta c : consultas.values()){
            if (c.getMedico().getCodM() == codM){
                total += c.getPreco();
                }
        }
        return total;
    }

    public List<Medico> medicoComMaisConsultas() {
        Map<Medico, Integer> contagem = new HashMap<>();
        for (Consulta c : consultas.values()) {
            Medico m = c.getMedico();
            contagem.put(m, contagem.getOrDefault(m, 0) + 1);
        }
        int max = 0;
        for (Medico m : contagem.keySet()) {
            if (contagem.get(m) > max) {
                max = contagem.get(m);
            }
        }
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : contagem.keySet()) {
            if (contagem.get(m) == max) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<Paciente> pacienteMaisFrequente() {
        Map<Paciente, Integer> contagem = new HashMap<>();
        for (Consulta c : consultas.values()) {
            Paciente p = c.getPaciente();
            contagem.put(p, contagem.getOrDefault(p, 0) + 1);
        }
        int max = 0;
        for (Paciente p : contagem.keySet()) {
            if (contagem.get(p) > max) {
                max = contagem.get(p);
            }
        }
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente p : contagem.keySet()) {
            if (contagem.get(p) == max) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    public List<Medico> buscarPorEspecialidade(String especialidade){
        List<Medico> medicoEsecialidade = new ArrayList<>();
        for (Medico m : medicos.values()){
            if (m.getEspecialidade().equals(especialidade)){
                medicoEsecialidade.add(m);
            }
        }
        return medicoEsecialidade;
    }

    public List<Consulta> consultasPorPeriodo(LocalDate inicio, LocalDate fim){
        List<Consulta> consultaData = new ArrayList<>();
        for (Consulta c: consultas.values()){
            if (!c.getData().isAfter(inicio) && !c.getData().isBefore(fim)){
                consultaData.add(c);
            }
        }
        return consultaData;
    }
    public void cancelarConsulta(int codC) throws EntidadeNaoExiste {
        if (!consultas.containsKey(codC)) {
            throw new EntidadeNaoExiste("Consulta", codC);
        }
        consultas.remove(codC);
    }
    public void atualizarPaciente(int codP, Optional<String> nome,
                                  Optional<String> CPF, Optional<Sexo> sexo, OptionalInt idade,
                                  Optional<String> convenio) throws EntidadeNaoExiste{
        Paciente p = pacientes.get(codP);
        if (p == null){
            throw new EntidadeNaoExiste("Paciente ", codP);
        }
        if (nome.isPresent()) {
            p.setNome(nome.get());
        }
        if (CPF.isPresent()) {
            p.setCPF(CPF.get());
        }
        if (sexo.isPresent()) {
            p.setSexo(sexo.get());
        }
        if (idade.isPresent()) {
            p.setIdade(idade.getAsInt());
        }
        if (convenio.isPresent()) {
            p.setConvenio(convenio.get());
        }
    }
    public void atualizarMedico(int codM, Optional<String> nome, Optional<String> CPF,
                                Optional<Sexo> sexo, Optional<String> especialidade,
                                Optional<String> turno, OptionalDouble salario) throws EntidadeNaoExiste{
        Medico m = medicos.get(codM);
        if (m == null){
            throw new EntidadeNaoExiste("Medico ", codM);
        }
        if (nome.isPresent()) {
            m.setNome(nome.get());
        }
        if (CPF.isPresent()) {
            m.setCPF(CPF.get());
        }
        if (sexo.isPresent()) {
            m.setSexo(sexo.get());
        }
        if (especialidade.isPresent()) {
            m.setEspecialidade(especialidade.get());
        }
        if (turno.isPresent()) {
            m.setTurno(turno.get());
        }
        if (salario.isPresent()) {
            m.setSalario(salario.getAsDouble());
        }
    }
}