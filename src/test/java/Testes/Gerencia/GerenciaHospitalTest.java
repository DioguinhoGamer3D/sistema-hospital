package Testes.Gerencia;

import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.EntidadeJaExiste;
import SistemaHospital.Exceptions.EntidadeNaoExiste;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GerenciaHospitalTest {

    GerenciaHospital sistema;
    Paciente paciente;
    Medico medico;

    @BeforeEach
    void setUp() {
        sistema  = new GerenciaHospital();
        paciente = sistema.cadastrarPaciente("João", "111", Sexo.MASCULINO, 30, "SUS");
        medico   = sistema.cadastrarMedico("Dra. Ana", "222", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0);
    }

    // ── Médicos ───────────────────────────────────────────────────────

    @Test
    void deveCadastrarMedico() {
        assertEquals(medico, sistema.pesquisarMedico(medico.getCodM()));
    }

    @Test
    void deveLancarExcecaoAoCadastrarMedicoComCpfDuplicado() {
        assertThrows(EntidadeJaExiste.class, () ->
                sistema.cadastrarMedico("Dr. Carlos", "222", Sexo.MASCULINO, "Ortopedia", "tarde", 7000.0)
        );
    }

    @Test
    void deveRemoverMedico() {
        sistema.removerMedico(medico.getCodM());
        assertThrows(EntidadeNaoExiste.class, () ->
                sistema.pesquisarMedico(medico.getCodM())
        );
    }

    @Test
    void deveLancarExcecaoAoPesquisarMedicoInexistente() {
        assertThrows(EntidadeNaoExiste.class, () ->
                sistema.pesquisarMedico(999)
        );
    }

    @Test
    void deveBuscarMedicoPorEspecialidade() {
        var resultado = sistema.buscarPorEspecialidade("Cardiologia");
        assertFalse(resultado.isEmpty());
        assertEquals("Dra. Ana", resultado.get(0).getNome());
    }

    @Test
    void deveRetornarListaVaziaParaEspecialidadeInexistente() {
        var resultado = sistema.buscarPorEspecialidade("Dermatologia");
        assertTrue(resultado.isEmpty());
    }

    // ── Consultas ─────────────────────────────────────────────────────

    @Test
    void deveCadastrarConsulta() {
        Consulta c = sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        assertEquals(c, sistema.pesquisarConsulta(c.getCodC()));
    }

    @Test
    void deveCancelarConsulta() {
        Consulta c = sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        sistema.cancelarConsulta(c.getCodC());
        assertThrows(EntidadeNaoExiste.class, () ->
                sistema.pesquisarConsulta(c.getCodC())
        );
    }

    @Test
    void deveBuscarConsultasPorPaciente() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        var consultas = sistema.consultasPorPaciente(paciente.getCodP());
        assertEquals(1, consultas.size());
    }

    @Test
    void deveBuscarConsultasPorMedico() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        var consultas = sistema.consultasPorMedico(medico.getCodM());
        assertEquals(1, consultas.size());
    }

    @Test
    void deveBuscarConsultasPorData() {
        LocalDate data = LocalDate.of(2025, 1, 15);
        sistema.cadastrarConsulta(paciente, medico, data, "Rotina", 200.0);
        var consultas = sistema.consultasPorData(data);
        assertEquals(1, consultas.size());
    }

    @Test
    void naoDeveCadastrarConsultaDuplicada() {
        LocalDate data = LocalDate.now();
        sistema.cadastrarConsulta(paciente, medico, data, "Rotina", 200.0);
        assertThrows(RuntimeException.class, () ->
                sistema.cadastrarConsulta(paciente, medico, data, "Rotina 2", 200.0)
        );
    }

    // ── Relatórios ────────────────────────────────────────────────────

    @Test
    void deveCalcularFaturamento() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        assertEquals(200.0, sistema.calcularFaturamento());
    }

    @Test
    void deveRetornarFaturamentoZeroSemConsultas() {
        assertEquals(0.0, sistema.calcularFaturamento());
    }

    @Test
    void deveRetornarMedicoComMaisConsultas() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        var top = sistema.medicoComMaisConsultas();
        assertFalse(top.isEmpty());
        assertEquals(medico.getCodM(), top.get(0).getCodM());
    }

    @Test
    void deveRetornarPacienteMaisFrequente() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        var top = sistema.pacienteMaisFrequente();
        assertFalse(top.isEmpty());
        assertEquals(paciente.getCodP(), top.get(0).getCodP());
    }

    @Test
    void deveCalcularFaturamentoPorMedico() {
        sistema.cadastrarConsulta(paciente, medico, LocalDate.now(), "Rotina", 300.0);
        assertEquals(300.0, sistema.faturamentoPorMedico(medico.getCodM()));
    }
}