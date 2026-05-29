package SistemaHospital;

import SistemaHospital.Controller.ConsultaController;
import SistemaHospital.Controller.PacienteController;
import SistemaHospital.Controller.MedicoController;
import SistemaHospital.Controller.RelatorioController;
import SistemaHospital.Gerencias.GerenciaHospital;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        start(7070);
    }

    public static Javalin start(int porta) {
        GerenciaHospital hospital = new GerenciaHospital();

        PacienteController  pacienteController  = new PacienteController(hospital);
        MedicoController    medicoController    = new MedicoController(hospital);
        ConsultaController  consultaController  = new ConsultaController(hospital);
        RelatorioController relatorioController = new RelatorioController(hospital);

        Javalin app = Javalin.create();

        app.get("/pacientes",               pacienteController::listar);
        app.get("/pacientes/novo",          pacienteController::formNovo);
        app.post("/pacientes/novo",         pacienteController::cadastrar);
        app.post("/pacientes/{cod}/remover",pacienteController::remover);
        app.get ("/pacientes/{cod}/editar", pacienteController::formEditar);
        app.post("/pacientes/{cod}/editar", pacienteController::atualizar);

        app.get("/medicos",                medicoController::listar);
        app.get("/medicos/novo",           medicoController::formNovo);
        app.post("/medicos/novo",          medicoController::cadastrar);
        app.post("/medicos/{cod}/remover", medicoController::remover);
        app.get ("/medicos/{cod}/editar",  medicoController::formEditar);
        app.post("/medicos/{cod}/editar",  medicoController::atualizar);

        app.get ("/consultas",                consultaController::listar);
        app.get ("/consultas/nova",           consultaController::formNova);
        app.post("/consultas/nova",           consultaController::cadastrar);
        app.post("/consultas/{cod}/cancelar", consultaController::cancelar);

        app.get("/relatorios", relatorioController::index);

        return app.start(porta);
    }
}