package SistemaHospital;

import SistemaHospital.Controller.ConsultaController;
import SistemaHospital.Controller.PacienteController;
import SistemaHospital.Controller.MedicoController;
import SistemaHospital.Controller.RelatorioController;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.Repository.ConsultaRepository;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Repository.PacienteRepository;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        start(7070);
    }

    public static Javalin start(int porta) {
        GerenciaHospital hospital = new GerenciaHospital();

        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository   medicoRepo   = new MedicoRepository();
        ConsultaRepository consultaRepo  = new ConsultaRepository(pacienteRepo, medicoRepo);


        MedicoController   medicoController = new MedicoController(medicoRepo);
        PacienteController pacienteController = new PacienteController(pacienteRepo);
        ConsultaController  consultaController  = new ConsultaController(consultaRepo, pacienteRepo, medicoRepo);
        RelatorioController relatorioController = new RelatorioController(pacienteRepo, medicoRepo, consultaRepo);
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
        app.get("/consultas/{cod}/editar",  consultaController::formEditar);
        app.post("/consultas/{cod}/editar",  consultaController::atualizar);

        app.get("/relatorios", relatorioController::index);

        return app.start(porta);
    }
}