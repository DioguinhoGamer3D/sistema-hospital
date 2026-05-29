package Testes.Gerencia;

import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.EntidadeJaExiste;
import SistemaHospital.Exceptions.EntidadeNaoExiste;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.Model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {

    GerenciaHospital sistema;

    @BeforeEach
    void setUp() {
        this.sistema = new GerenciaHospital();
    }

    @Test
    void deveEstarVazio() {
        assertTrue(sistema.todosPacientes().isEmpty());
        assertThrows(EntidadeNaoExiste.class, () -> sistema.pesquisarPaciente(1));
    }

    @Test
    void deveCadastrarPaciente() {
        Paciente p1 = sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS");
        assertEquals(sistema.pesquisarPaciente(1), p1);
    }

    @Test
    void deveCadastrarDoisPacientesComCodigosDiferentes() {
        Paciente p1 = sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS");
        Paciente p2 = sistema.cadastrarPaciente("Maria", "222", Sexo.FEMININO, 22, "SUS");
        assertNotEquals(p1.getCodP(), p2.getCodP());
    }

    @Test
    void deveVerificarOTamanho() {
        Paciente p1 = sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS");
        Paciente p2 = sistema.cadastrarPaciente("Maria", "222", Sexo.FEMININO, 22, "SUS");
        assertEquals(2,sistema.todosPacientes().size());
    }

    @Test
    void deveLancarExcecaoAoCadastrarCpfDuplicado() {
        sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS");
        assertThrows(EntidadeJaExiste.class, () ->
                sistema.cadastrarPaciente("Maria Jose", "111", Sexo.FEMININO, 25, "SUS")
        );
    }

    @Test
    void deveLancarExcecaoAoPesquisarPacienteInexistente() {
        assertThrows(EntidadeNaoExiste.class, () ->
                sistema.pesquisarPaciente(999)
        );
    }

    @Test
    void deveImpedirCadastroDeCpfBanido() {
        sistema.naoDeNemAgua("111");
        assertThrows(RuntimeException.class, () ->
                sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS")
        );
    }

    @Test
    void deveRemoverPaciente() {
        Paciente p = sistema.cadastrarPaciente("Joao", "111", Sexo.MASCULINO, 20, "SUS");
        sistema.removerPaciente(p.getCodP());
        assertThrows(EntidadeNaoExiste.class, () ->
                sistema.pesquisarPaciente(p.getCodP())
        );
    }
}
