package SistemaHospital.Gerencias;

import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.EntidadeJaExiste;
import SistemaHospital.Exceptions.EntidadeNaoExiste;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public interface Hospital {
    Paciente cadastrarPaciente(String nome, String CPF, Sexo sexo, int idade, String convenio) throws EntidadeJaExiste;
    void removerPaciente(int codP) throws EntidadeNaoExiste;
    Medico cadastrarMedico(String nome, String CPF, Sexo sexo, String especialidade, String turno, double salario);
    void removerMedico(int codM) throws EntidadeNaoExiste;
    Consulta cadastrarConsulta(Paciente p, Medico m, LocalDate data, String diagnostico, double preco);
    void removerConsulta(int codC) throws EntidadeNaoExiste;
    void naoDeNemAgua(String CPF);
    Paciente pesquisarPaciente(int codP) throws EntidadeNaoExiste;
    Consulta pesquisarConsulta(int codC) throws EntidadeNaoExiste;
    Medico pesquisarMedico(int codM) throws EntidadeNaoExiste;
    List<Consulta> consultasPorPaciente(int codP)throws EntidadeNaoExiste;
    List<Consulta> consultasPorMedico(int codM)throws EntidadeNaoExiste;
    List<Consulta> consultasPorData(LocalDate data) throws EntidadeNaoExiste;
    Paciente buscarPacientePorCpf(String cpf) throws EntidadeNaoExiste;
    boolean consultaJaExiste(Paciente p, Medico m, LocalDate data);
    boolean medicoDisponivel(Medico m, LocalDate data) throws EntidadeNaoExiste;
    boolean pacientePodeConsultar(String cpf) throws EntidadeNaoExiste;
    double calcularFaturamento();
    double faturamentoPorMedico(int codM) throws EntidadeNaoExiste;
    List<Medico> medicoComMaisConsultas();
    List<Paciente> pacienteMaisFrequente();
    List<Medico> buscarPorEspecialidade(String especialidade);
    List<Consulta> consultasPorPeriodo(LocalDate inicio, LocalDate fim);
    void cancelarConsulta(int codC) throws EntidadeNaoExiste;
    void atualizarPaciente(int codP, Optional<String> nome,
                           Optional<String> CPF, Optional<Sexo> sexo, OptionalInt idade,
                           Optional<String> convenio) throws EntidadeNaoExiste;
    public void atualizarMedico(int codM, Optional<String> nome, Optional<String> CPF,
                                Optional<Sexo> sexo, Optional<String> especialidade,
                                Optional<String> turno, OptionalDouble salario) throws EntidadeNaoExiste;
}