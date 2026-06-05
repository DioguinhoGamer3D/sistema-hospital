package Testes.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Uteis.Enum.Sexo;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.Uteis.Exceptions.RecursoNaoEncontrado;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteRepositoryTest {

    private PacienteRepository repo;

    @BeforeAll
    static void configurarBanco() {
        System.setProperty("ENV_FILE", ".envtest");
    }

    @BeforeEach
    void setUp() throws Exception {
        repo = new PacienteRepository();
        limparTabela();
    }

    @AfterEach
    void tearDown() throws Exception {
        limparTabela();
    }

    private void limparTabela() throws Exception {
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM consultas");
            stmt.execute("DELETE FROM pacientes");
            stmt.execute("ALTER SEQUENCE pacientes_cod_p_seq RESTART WITH 1");
        }
    }

    @Test
    void salvar_armazenaERecupera() {
        Paciente p = new Paciente("João", "111.111.111-11", Sexo.MASCULINO, 30, "SUS");
        repo.salvar(p);

        Paciente encontrado = repo.buscarPorCod(p.getCodP());
        assertNotNull(encontrado);
        assertEquals("João", encontrado.getNome());
        assertEquals("111.111.111-11", encontrado.getCPF());
    }

    @Test
    void buscarTodos_retornaTodosOsSalvos() {
        repo.salvar(new Paciente("Ana",    "111.111.111-11", Sexo.FEMININO,   25, "SUS"));
        repo.salvar(new Paciente("Carlos", "222.222.222-22", Sexo.MASCULINO,  40, "Unimed"));

        List<Paciente> lista = repo.buscarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void buscarPorCod_lancaExcecaoQuandoNaoExiste() {
        assertThrows(RecursoNaoEncontrado.class, () -> repo.buscarPorCod(999));
    }

    @Test
    void buscarPorCpf_encontraQuandoExiste() {
        repo.salvar(new Paciente("Maria", "333.333.333-33", Sexo.FEMININO, 35, "Bradesco"));

        Paciente encontrado = repo.buscarPorCpf("333.333.333-33");
        assertNotNull(encontrado);
        assertEquals("Maria", encontrado.getNome());
    }

    @Test
    void buscarPorCpf_lancaExcecaoQuandoNaoExiste() {
        assertThrows(RecursoNaoEncontrado.class, () -> repo.buscarPorCpf("000.000.000-00"));
    }

    @Test
    void salvar_lancaExcecaoComCpfDuplicado() {
        repo.salvar(new Paciente("João", "111.111.111-11", Sexo.MASCULINO, 30, "SUS"));
        assertThrows(RuntimeException.class, () ->
                repo.salvar(new Paciente("Pedro", "111.111.111-11", Sexo.MASCULINO, 25, "SUS"))
        );
    }

    @Test
    void atualizar_modificaDadosNoBanco() {
        Paciente p = new Paciente("João", "111.111.111-11", Sexo.MASCULINO, 30, "SUS");
        repo.salvar(p);

        p.setNome("João Atualizado");
        p.setIdade(31);
        repo.atualizar(p);

        Paciente atualizado = repo.buscarPorCod(p.getCodP());
        assertEquals("João Atualizado", atualizado.getNome());
        assertEquals(31, atualizado.getIdade());
    }

    @Test
    void remover_apagaDoBanco() {
        Paciente p = new Paciente("João", "111.111.111-11", Sexo.MASCULINO, 30, "SUS");
        repo.salvar(p);

        repo.remover(p.getCodP());
        assertThrows(RecursoNaoEncontrado.class, () -> repo.buscarPorCod(p.getCodP()));
    }

    @Test
    void buscarPorNome_encontraComNomeParcial() {
        repo.salvar(new Paciente("João Silva", "111.111.111-11", Sexo.MASCULINO, 30, "SUS"));

        List<Paciente> lista = repo.buscarPorNome("João");
        assertFalse(lista.isEmpty());
        assertEquals("João Silva", lista.get(0).getNome());
    }

    @Test
    void buscarPorConvenio_encontraComConvenioParcial() {
        repo.salvar(new Paciente("Ana", "111.111.111-11", Sexo.FEMININO, 25, "Unimed"));

        List<Paciente> lista = repo.buscarPorConvenio("Uni");
        assertFalse(lista.isEmpty());
        assertEquals("Ana", lista.get(0).getNome());
    }

    @Test
    void buscarPorNome_retornaListaVaziaQuandoNaoEncontra() {
        List<Paciente> lista = repo.buscarPorNome("NomeQueNaoExiste");
        assertTrue(lista.isEmpty());
    }
}