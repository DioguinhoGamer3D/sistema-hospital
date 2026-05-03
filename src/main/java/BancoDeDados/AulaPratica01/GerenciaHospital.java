package BancoDeDados.AulaPratica01;

import BancoDeDados.AulaPratica01.Enum.Sexo;
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
    public Pacientes cadastrarPaciente(String nome, String CPF, Sexo sexo, int idade, String convenio)
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
    public Medicos cadastrarMedico(String nome, String CPF, Sexo sexo, String especialidade, String turno, double salario)
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

    public List<Consultas> consultasPorPaciente(int codP)throws EntidadeNaoExiste{
        List<Consultas> consultasPaciente = new ArrayList<>();
        for(Consultas c: consultas.values()) {
            if (c.getPaciente().getCodP() == codP) {
                consultasPaciente.add(c);
            }
        } if (consultasPaciente.isEmpty()){
            throw new EntidadeNaoExiste("Consultas", codP);
        }
        return consultasPaciente;
    }

    public List<Consultas> consultasPorMedico(int codM)throws EntidadeNaoExiste{
        List<Consultas> consultasMedico = new ArrayList<>();
        for(Consultas c: consultas.values()){
            if(c.getMedico().getCodM() == codM){
                consultasMedico.add(c);
            }
        } if (consultasMedico.isEmpty()){
            throw new EntidadeNaoExiste("Consultas", codM);
        }
        return consultasMedico;
    }

    public List<Consultas> consultasPorData(LocalDate data) throws EntidadeNaoExiste{
        List<Consultas> consultasData = new ArrayList<>();
        for (Consultas c: consultas.values()){
            if (c.getData().equals(data)){
                consultasData.add(c);
            }
        } if (consultasData.isEmpty()){
            throw new EntidadeNaoExiste("Consulta", data);
        }
        return consultasData;
    }
    public Pacientes buscarPacientePorCpf(String cpf) throws EntidadeNaoExiste{
        for (Pacientes p: pacientes.values()){
            if (p.getCPF().equals(cpf)){
                return p;
            }
        }
        throw new EntidadeNaoExiste("Paciente", cpf);
    }

    public boolean consultaJaExiste(Pacientes p, Medicos m, LocalDate data){
        for (Consultas c: consultas.values()){
            if (c.getPaciente().equals(p) && c.getMedico().equals(m) && c.getData().equals(data)){
                return true;
            }
        }
        return false;
    }
    public boolean medicoDisponivel(Medicos m, LocalDate data){
        for(Consultas c: consultas.values()){
            if (c.getMedico().equals(m) && c.getData().equals(data)){
                return false;
            }
        }
        return true;
    }
    public boolean pacientePodeConsultar(String cpf){
        return !cpfsBanidos.contains(cpf);
    }

    public double calcularFaturamento(){
        double totalFaturamento = 0;
        for (Consultas c: consultas.values()){
            totalFaturamento+= c.getPreco();
        }
        return totalFaturamento;
    }

    public double faturamentoPorMedico(int codM) throws EntidadeNaoExiste{
        double totalMedico = 0;
        for (Consultas c : consultas.values()){
            if (c.getMedico().equals(medicos.get(codM))){
                totalMedico += c.getPreco();
            }
        }
        return totalMedico;
    }

    public Medicos medicoComMaisConsultas(){
        Map<Medicos, Integer> contagem = new HashMap<>();
        for (Consultas c : consultas.values()){
            Medicos m = c.getMedico();
            contagem.put(m, contagem.getOrDefault(m, 0) + 1);
        }
        Medicos maisConsulta = null;
        int max = 0;

        for (Map.Entry<Medicos, Integer> entry : contagem.entrySet()){
            if (entry.getValue() > max){
                max = entry.getValue();
                maisConsulta = entry.getKey();
            }
        }
        return maisConsulta;
    }

    public Pacientes pacienteMaisFrequente(){
        Map<Pacientes, Integer> contagem = new HashMap<>();
        for (Consultas c : consultas.values()){
            Pacientes p = c.getPaciente();
            contagem.put(p, contagem.getOrDefault(p, 0)+ 1);
        }
        Pacientes maisConsulta = null;
        int max = 0;

        for (Map.Entry<Pacientes, Integer> entry : contagem.entrySet()){
            if (entry.getValue() > max){
                max = entry.getValue();
                maisConsulta = entry.getKey();
            }
        }
        return maisConsulta;
    }
    public List<Medicos> buscarPorEspecialidade(String especialidade){
        List<Medicos> medicosEsecialidade = new ArrayList<>();
        for (Medicos m : medicos.values()){
            if (m.getEspecialidade().equals(especialidade)){
                medicosEsecialidade.add(m);
            }
        }
        return medicosEsecialidade;
    }

    public List<Consultas> consultasPorPeriodo(LocalDate inicio, LocalDate fim){
        List<Consultas> consultasData = new ArrayList<>();
        for (Consultas c: consultas.values()){
            if (c.getData().isAfter(inicio) && c.getData().isBefore(fim)){
                consultasData.add(c);
            }
        }
        return consultasData;
    }
    public void cancelarConsulta(int codC) throws EntidadeNaoExiste{
        for (Consultas c : consultas.values()){
            if (c.getCodC() != codC){
                throw new EntidadeNaoExiste("Consulta", codC);
            } else {
                consultas.remove(codC, c);
            }
        }
    }
    public void atualizarPaciente(int codP, Optional<String> nome,
                                  Optional<String> CPF, Optional<Sexo> sexo, OptionalInt idade,
                                  Optional<String> convenio) throws EntidadeNaoExiste{
        Pacientes p = pacientes.get(codP);
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
}