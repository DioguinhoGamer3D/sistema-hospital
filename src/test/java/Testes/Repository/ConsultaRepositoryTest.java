package Testes.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Uteis.Enum.Sexo;
import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Repository.ConsultaRepository;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.Uteis.Exceptions.RecursoNaoEncontrado;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaRepositoryTest {

    private PacienteRepository pacienteRepo;
    private MedicoRepository   medicoRepo;
    private ConsultaRepository consultaRepo;
    private Paciente paciente;
    private Medico   medico;

    @BeforeAll
    static void configurarBanco() {
        System.setProperty("ENV_FILE", ".envtest");
    }

    @BeforeEach
    void setUp() throws Exception {
        pacienteRepo = new PacienteRepository();
        medicoRepo   = new MedicoRepository();
        consultaRepo = new ConsultaRepository(pacienteRepo, medicoRepo);
        limparTabelas();

        paciente = pacienteRepo.salvar(new Paciente("João", "111.111.111-11", Sexo.MASCULINO, 30, "SUS"));
        medico   = medicoRepo.salvar(new Medico("Dr. Ana", "222.222.222-22", Sexo.FEMININO, "Cardiologia", "manhã", 8000.0));
    }

    @AfterEach
    void tearDown() throws Exception {
        limparTabelas();
    }

    private void limparTabelas() throws Exception {
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM consultas");
            stmt.execute("DELETE FROM pacientes");
            stmt.execute("DELETE FROM medicos");
            stmt.execute("ALTER SEQUENCE consultas_cod_c_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE pacientes_cod_p_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE medicos_cod_m_seq RESTART WITH 1");
        }
    }

    @Test
    void salvar_armazenaERecupera() {
        Consulta c = new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        consultaRepo.salvar(c);

        Consulta encontrada = consultaRepo.buscarPorCod(c.getCodC());
        assertNotNull(encontrada);
        assertEquals("João",    encontrada.getPaciente().getNome());
        assertEquals("Dr. Ana", encontrada.getMedico().getNome());
        assertEquals("Rotina",  encontrada.getDiagnostico());
    }

    @Test
    void buscarTodas_retornaTodasAsSalvas() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(),           "Rotina",   200.0));
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now().plusDays(1), "Retorno", 150.0));

        assertEquals(2, consultaRepo.buscarTodas().size());
    }

    @Test
    void buscarPorCod_lancaExcecaoQuandoNaoExiste() {
        assertThrows(RecursoNaoEncontrado.class, () -> consultaRepo.buscarPorCod(999));
    }

    @Test
    void buscarPorPaciente_retornaConsultasDoPaciente() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0));

        List<Consulta> lista = consultaRepo.buscarPorPaciente(paciente.getCodP());
        assertEquals(1, lista.size());
        assertEquals("João", lista.get(0).getPaciente().getNome());
    }

    @Test
    void buscarPorMedico_retornaConsultasDoMedico() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0));

        List<Consulta> lista = consultaRepo.buscarPorMedico(medico.getCodM());
        assertEquals(1, lista.size());
        assertEquals("Dr. Ana", lista.get(0).getMedico().getNome());
    }

    @Test
    void buscarPorData_retornaConsultasDaData() {
        LocalDate data = LocalDate.of(2026, 1, 15);
        consultaRepo.salvar(new Consulta(paciente, medico, data, "Rotina", 200.0));

        List<Consulta> lista = consultaRepo.buscarPorData(data);
        assertEquals(1, lista.size());
    }

    @Test
    void atualizar_modificaDadosNoBanco() {
        Consulta c = new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        consultaRepo.salvar(c);

        c.setDiagnostico("Retorno");
        c.setPreco(150.0);
        consultaRepo.atualizar(c);

        Consulta atualizada = consultaRepo.buscarPorCod(c.getCodC());
        assertEquals("Retorno", atualizada.getDiagnostico());
        assertEquals(150.0,     atualizada.getPreco());
    }

    @Test
    void remover_apagaDoBanco() {
        Consulta c = new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0);
        consultaRepo.salvar(c);

        consultaRepo.remover(c.getCodC());
        assertThrows(RecursoNaoEncontrado.class, () -> consultaRepo.buscarPorCod(c.getCodC()));
    }

    @Test
    void calcularFaturamento_somaPrecosDasConsultas() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(),             "Rotina",  200.0));
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now().plusDays(1), "Retorno", 300.0));

        assertEquals(500.0, consultaRepo.calcularFaturamento());
    }

    @Test
    void calcularFaturamento_retornaZeroSemConsultas() {
        assertEquals(0.0, consultaRepo.calcularFaturamento());
    }

    @Test
    void medicoComMaisConsultas_retornaMedicoCorreto() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0));

        List<Medico> top = consultaRepo.medicoComMaisConsultas();
        assertFalse(top.isEmpty());
        assertEquals("Dr. Ana", top.get(0).getNome());
    }

    @Test
    void pacienteMaisFrequente_retornaPacienteCorreto() {
        consultaRepo.salvar(new Consulta(paciente, medico, LocalDate.now(), "Rotina", 200.0));

        List<Paciente> top = consultaRepo.pacienteMaisFrequente();
        assertFalse(top.isEmpty());
        assertEquals("João", top.get(0).getNome());
    }
}