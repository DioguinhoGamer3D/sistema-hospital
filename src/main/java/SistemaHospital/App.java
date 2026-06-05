package SistemaHospital;

import SistemaHospital.Controller.ConsultaController;
import SistemaHospital.Controller.PacienteController;
import SistemaHospital.Controller.MedicoController;
import SistemaHospital.Controller.RelatorioController;
import SistemaHospital.Repository.ConsultaRepository;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.Uteis.Exceptions.ErroDeServidor;
import SistemaHospital.Uteis.Exceptions.RecursoNaoEncontrado;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        start(7070);
    }

    public static Javalin start(int porta) {

        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository   medicoRepo   = new MedicoRepository();
        ConsultaRepository consultaRepo  = new ConsultaRepository(pacienteRepo, medicoRepo);


        MedicoController   medicoController = new MedicoController(medicoRepo);
        PacienteController pacienteController = new PacienteController(pacienteRepo);
        ConsultaController  consultaController  = new ConsultaController(consultaRepo, pacienteRepo, medicoRepo);
        RelatorioController relatorioController = new RelatorioController(pacienteRepo, medicoRepo, consultaRepo);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static", Location.CLASSPATH);
        });

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

        app.exception(RecursoNaoEncontrado.class, (e, ctx) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("mensagem", e.getMessage());
            ctx.status(404).html(ThymeleafConfig.render("erro404", model));
        });

        app.exception(ErroDeServidor.class, (e, ctx) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("mensagem", e.getMessage());
            ctx.status(500).html(ThymeleafConfig.render("erro500", model));
        });

        app.exception(Exception.class, (e, ctx) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("mensagem", "Ocorreu um erro inesperado. Tente novamente.");
            ctx.status(500).html(ThymeleafConfig.render("erro500", model));
        });

        return app.start(porta);
    }
}