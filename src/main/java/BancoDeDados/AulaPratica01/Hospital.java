package BancoDeDados.AulaPratica01;

import BancoDeDados.AulaPratica01.Enum.Sexo;
import BancoDeDados.AulaPratica01.Exceptions.EntidadeJaExiste;
import BancoDeDados.AulaPratica01.Exceptions.EntidadeNaoExiste;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface Hospital {
    Pacientes cadastrarPaciente(String nome, String CPF, Sexo sexo, int idade, String convenio) throws EntidadeJaExiste;
    void removerPaciente(int codP) throws EntidadeNaoExiste;
    Medicos cadastrarMedico(String nome, String CPF, Sexo sexo, String especialidade, String turno, double salario);
    void removerMedico(int codM) throws EntidadeNaoExiste;
    Consultas cadastrarConsulta(Pacientes p, Medicos m, LocalDate data, String diagnostico, double preco);
    void removerConsulta(int codC) throws EntidadeNaoExiste;
    void naoDeNemAgua(String CPF);
    Pacientes pesquisarPaciente(int codP) throws EntidadeNaoExiste;
    Consultas pesquisarConsulta(int codC) throws EntidadeNaoExiste;
    Medicos pesquisarMedico(int codM) throws EntidadeNaoExiste;
    List<Consultas> consultasPorPaciente(int codP)throws EntidadeNaoExiste;
    List<Consultas> consultasPorMedico(int codM)throws EntidadeNaoExiste;
    List<Consultas> consultasPorData(LocalDate data) throws EntidadeNaoExiste;
    Pacientes buscarPacientePorCpf(String cpf) throws EntidadeNaoExiste;
    boolean consultaJaExiste(Pacientes p, Medicos m, LocalDate data);
    boolean medicoDisponivel(Medicos m, LocalDate data) throws EntidadeNaoExiste;
    boolean pacientePodeConsultar(String cpf) throws EntidadeNaoExiste;
    double calcularFaturamento();
    double faturamentoPorMedico(int codM) throws EntidadeNaoExiste;
    Medicos medicoComMaisConsultas();
    Pacientes pacienteMaisFrequente();
    List<Medicos> buscarPorEspecialidade(String especialidade);
    List<Consultas> consultasPorPeriodo(LocalDate inicio, LocalDate fim);
    void cancelarConsulta(int codC) throws EntidadeNaoExiste;
    void atualizarPaciente(int codP, Optional<String> nome,
                           Optional<String> CPF, Optional<Sexo> sexo, OptionalInt idade,
                           Optional<String> convenio) throws EntidadeNaoExiste;
}