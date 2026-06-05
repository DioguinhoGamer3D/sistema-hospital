package Testes.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Uteis.Enum.Sexo;
import SistemaHospital.Model.Medico;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Uteis.Exceptions.RecursoNaoEncontrado;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicoRepositoryTest {

    private MedicoRepository repo;

    @BeforeAll
    static void configurarBanco() {
        System.setProperty("ENV_FILE", ".envtest");
    }

    @BeforeEach
    void setUp() throws Exception {
        repo = new MedicoRepository();
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
            stmt.execute("DELETE FROM medicos");
            stmt.execute("ALTER SEQUENCE medicos_cod_m_seq RESTART WITH 1");
        }
    }

    @Test
    void salvar_armazenaERecupera() {
        Medico m = new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0);
        repo.salvar(m);

        Medico encontrado = repo.buscarPorCod(m.getCodM());
        assertNotNull(encontrado);
        assertEquals("Dr. Ana", encontrado.getNome());
        assertEquals("Cardiologia", encontrado.getEspecialidade());
    }

    @Test
    void buscarTodos_retornaTodosOsSalvos() {
        repo.salvar(new Medico("Dr. Ana",    "111.111.111-11", Sexo.FEMININO,  "Cardiologia", "manhã", 8000.0));
        repo.salvar(new Medico("Dr. Carlos", "222.222.222-22", Sexo.MASCULINO, "Ortopedia",   "tarde", 7000.0));

        assertEquals(2, repo.buscarTodos().size());
    }

    @Test
    void buscarPorCod_lancaExcecaoQuandoNaoExiste() {
        assertThrows(RecursoNaoEncontrado.class, () -> repo.buscarPorCod(999));
    }

    @Test
    void salvar_lancaExcecaoComCpfDuplicado() {
        repo.salvar(new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0));
        assertThrows(RuntimeException.class, () ->
                repo.salvar(new Medico("Dr. Pedro", "111.111.111-11", Sexo.MASCULINO, "Ortopedia", "tarde", 7000.0))
        );
    }

    @Test
    void atualizar_modificaDadosNoBanco() {
        Medico m = new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0);
        repo.salvar(m);

        m.setNome("Dr. Ana Atualizada");
        m.setEspecialidade("Neurologia");
        repo.atualizar(m);

        Medico atualizado = repo.buscarPorCod(m.getCodM());
        assertEquals("Dr. Ana Atualizada", atualizado.getNome());
        assertEquals("Neurologia", atualizado.getEspecialidade());
    }

    @Test
    void remover_apagaDoBanco() {
        Medico m = new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0);
        repo.salvar(m);

        repo.remover(m.getCodM());
        assertThrows(RecursoNaoEncontrado.class, () -> repo.buscarPorCod(m.getCodM()));
    }

    @Test
    void buscarPorEspecialidade_encontraComEspecialidadeParcial() {
        repo.salvar(new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0));

        List<Medico> lista = repo.buscarPorEspecialidade("Cardio");
        assertFalse(lista.isEmpty());
        assertEquals("Dr. Ana", lista.get(0).getNome());
    }

    @Test
    void buscarPorTurno_encontraComTurnoParcial() {
        repo.salvar(new Medico("Dr. Ana", "111.111.111-11", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0));

        List<Medico> lista = repo.buscarPorTurno("manhã");
        assertFalse(lista.isEmpty());
    }

    @Test
    void buscarPorNome_retornaListaVaziaQuandoNaoEncontra() {
        List<Medico> lista = repo.buscarPorNome("NomeQueNaoExiste");
        assertTrue(lista.isEmpty());
    }
}