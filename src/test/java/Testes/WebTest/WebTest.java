package Testes.WebTest;

import SistemaHospital.App;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WebTest {

    static Javalin app;

    @BeforeAll
    static void setUp() {
        app = App.start(7071); // porta diferente pra não conflitar
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7071;
    }

    @AfterAll
    static void tearDown() {
        app.stop();
    }

    // ── Pacientes ─────────────────────────────────────────────────────

    @Test
    void deveRetornarPaginaDePacientes() {
        given()
                .when()
                .get("/pacientes")
                .then()
                .statusCode(200)
                .body(containsString("Pacientes"));
    }

    @Test
    void deveRetornarFormularioDeNovoPaciente() {
        given()
                .when()
                .get("/pacientes/novo")
                .then()
                .statusCode(200)
                .body(containsString("Novo Paciente"));
    }

    @Test
    void deveCadastrarPacienteERedirecionarParaLista() {
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("nome", "João Teste")
                .formParam("cpf", "999999999")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "25")
                .formParam("convenio", "SUS")
                .when()
                .post("/pacientes/novo")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveEditarPaciente() {
        // primeiro cadastra
        given()
                .formParam("nome", "João Editar")
                .formParam("cpf", "111222333")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "30")
                .formParam("convenio", "SUS")
                .when()
                .post("/pacientes/novo");

        // depois edita o paciente de código 1
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("nome", "João Editado")
                .formParam("cpf", "111222333")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "31")
                .formParam("convenio", "Unimed")
                .when()
                .post("/pacientes/1/editar")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveRemoverPaciente() {
        // primeiro cadastra
        given()
                .formParam("nome", "João Remover")
                .formParam("cpf", "444555666")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "25")
                .formParam("convenio", "SUS")
                .when()
                .post("/pacientes/novo");

        // depois remove
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .when()
                .post("/pacientes/1/remover")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    // ── Médicos ───────────────────────────────────────────────────────

    @Test
    void deveRetornarPaginaDeMedicos() {
        given()
                .when()
                .get("/medicos")
                .then()
                .statusCode(200)
                .body(containsString("Médicos"));
    }

    @Test
    void deveRetornarFormularioDeNovoMedico() {
        given()
                .when()
                .get("/medicos/novo")
                .then()
                .statusCode(200)
                .body(containsString("Novo Médico"));
    }

    @Test
    void deveCadastrarMedicoERedirecionarParaLista() {
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("nome", "João Teste")
                .formParam("cpf", "999999999")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "25")
                .formParam("especialidade", "geral")
                .formParam("turno", "MANHA")
                .formParam("salario", "5000")
                .when()
                .post("/medicos/novo")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveCadastrarMedico() {
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("nome", "Dr. Teste")
                .formParam("CPF", "777888999")
                .formParam("sexo", "MASCULINO")
                .formParam("especialidade", "Cardiologia")
                .formParam("turno", "manhã")
                .formParam("salario", "8000")
                .when()
                .post("/medicos/novo")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveEditarMedico() {
        // primeiro cadastra
        given()
                .formParam("nome", "Dr. Editar")
                .formParam("CPF", "111333555")
                .formParam("sexo", "MASCULINO")
                .formParam("especialidade", "Ortopedia")
                .formParam("turno", "tarde")
                .formParam("salario", "7000")
                .when()
                .post("/medicos/novo");

        // depois edita
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("nome", "Dr. Editado")
                .formParam("CPF", "111333555")
                .formParam("sexo", "MASCULINO")
                .formParam("especialidade", "Neurologia")
                .formParam("turno", "manhã")
                .formParam("salario", "9000")
                .when()
                .post("/medicos/1/editar")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveRemoverMedico() {
        // primeiro cadastra
        given()
                .formParam("nome", "Dr. Remover")
                .formParam("CPF", "222444666")
                .formParam("sexo", "FEMININO")
                .formParam("especialidade", "Pediatria")
                .formParam("turno", "noite")
                .formParam("salario", "6000")
                .when()
                .post("/medicos/novo");

        // depois remove
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .when()
                .post("/medicos/1/remover")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    // ── Consultas ─────────────────────────────────────────────────────

    @Test
    void deveRetornarPaginaDeConsultas() {
        given()
                .when()
                .get("/consultas")
                .then()
                .statusCode(200)
                .body(containsString("Consultas"));
    }

    @Test
    void deveCadastrarConsulta() {
        // cadastra paciente e médico primeiro
        given()
                .formParam("nome", "Paciente Consulta")
                .formParam("cpf", "333666999")
                .formParam("sexo", "FEMININO")
                .formParam("idade", "40")
                .formParam("convenio", "SUS")
                .when()
                .post("/pacientes/novo");

        given()
                .formParam("nome", "Dr. Consulta")
                .formParam("CPF", "555777888")
                .formParam("sexo", "MASCULINO")
                .formParam("especialidade", "Clínico Geral")
                .formParam("turno", "manhã")
                .formParam("salario", "5000")
                .when()
                .post("/medicos/novo");

        // cadastra a consulta
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .formParam("codP", "1")
                .formParam("codM", "1")
                .formParam("data", "2026-06-01")
                .formParam("diagnostico", "Rotina")
                .formParam("preco", "200")
                .when()
                .post("/consultas/nova")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void deveCancelarConsulta() {
        // cadastra paciente, médico e consulta primeiro
        given()
                .formParam("nome", "Paciente Cancelar")
                .formParam("cpf", "123456789")
                .formParam("sexo", "MASCULINO")
                .formParam("idade", "35")
                .formParam("convenio", "Unimed")
                .when()
                .post("/pacientes/novo");

        given()
                .formParam("nome", "Dr. Cancelar")
                .formParam("CPF", "987654321")
                .formParam("sexo", "FEMININO")
                .formParam("especialidade", "Dermatologia")
                .formParam("turno", "tarde")
                .formParam("salario", "7500")
                .when()
                .post("/medicos/novo");

        given()
                .formParam("codP", "1")
                .formParam("codM", "1")
                .formParam("data", "2026-07-01")
                .formParam("diagnostico", "Consulta de rotina")
                .formParam("preco", "150")
                .when()
                .post("/consultas/nova");

        // cancela a consulta
        given()
                .redirects().follow(true)
                .redirects().max(10)
                .when()
                .post("/consultas/1/cancelar")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }

    // ── Relatórios ────────────────────────────────────────────────────

    @Test
    void deveRetornarPaginaDeRelatorios() {
        given()
                .when()
                .get("/relatorios")
                .then()
                .statusCode(200)
                .body(containsString("Relatórios"));
    }

    @Test
    void deveRetornar404ParaRotaInexistente() {
        given()
                .when()
                .get("/rota-que-nao-existe")
                .then()
                .statusCode(404);
    }
}