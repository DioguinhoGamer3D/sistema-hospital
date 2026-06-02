package SistemaHospital.Controller;

import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Repository.ConsultaRepository;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ConsultaController {

    private final ConsultaRepository  consultaRepo;
    private final PacienteRepository  pacienteRepo;
    private final MedicoRepository    medicoRepo;

    public ConsultaController(ConsultaRepository consultaRepo,
                              PacienteRepository pacienteRepo,
                              MedicoRepository   medicoRepo) {
        this.consultaRepo = consultaRepo;
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo   = medicoRepo;
    }

    public void listar(Context ctx) {
        String tipo  = ctx.queryParam("tipo");
        String valor = ctx.queryParam("valor");
        Map<String, Object> model = new HashMap<>();

        if (tipo != null && valor != null && !valor.isBlank()) {
            try {
                var lista = switch (tipo) {
                    case "paciente" -> consultaRepo.buscarPorPaciente(Integer.parseInt(valor));
                    case "medico"   -> consultaRepo.buscarPorMedico(Integer.parseInt(valor));
                    case "data"     -> consultaRepo.buscarPorData(LocalDate.parse(valor));
                    default         -> consultaRepo.buscarTodas();
                };
                model.put("consultas", lista);
            } catch (Exception e) {
                model.put("erro", e.getMessage());
                model.put("consultas", consultaRepo.buscarTodas());
            }
        } else {
            model.put("consultas", consultaRepo.buscarTodas());
        }

        model.put("pacientes", pacienteRepo.buscarTodos());
        model.put("medicos",   medicoRepo.buscarTodos());
        model.put("tipo",      tipo);
        model.put("valor",     valor);
        ctx.html(ThymeleafConfig.render("consultas/lista", model));
    }

    public void formNova(Context ctx) {
        ctx.html(ThymeleafConfig.render("consultas/form", Map.of(
                "pacientes", pacienteRepo.buscarTodos(),
                "medicos",   medicoRepo.buscarTodos(),
                "titulo",    "Nova Consulta",
                "acao",      "/consultas/nova"
        )));
    }

    public void cadastrar(Context ctx) {
        try {
            Paciente p    = pacienteRepo.buscarPorCod(Integer.parseInt(ctx.formParam("codP")));
            Medico   m    = medicoRepo.buscarPorCod(Integer.parseInt(ctx.formParam("codM")));
            LocalDate data = LocalDate.parse(ctx.formParam("data"));
            double preco  = Double.parseDouble(ctx.formParam("preco"));
            Consulta c    = new Consulta(p, m, data, ctx.formParam("diagnostico"), preco);
            consultaRepo.salvar(c);
            ctx.redirect("/consultas");
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("pacientes", pacienteRepo.buscarTodos());
            model.put("medicos",   medicoRepo.buscarTodos());
            model.put("titulo",    "Nova Consulta");
            model.put("acao",      "/consultas/nova");
            model.put("erro",      e.getMessage());
            ctx.html(ThymeleafConfig.render("consultas/form", model));
        }
    }

    public void formEditar(Context ctx) {
        Consulta c = consultaRepo.buscarPorCod(Integer.parseInt(ctx.pathParam("cod")));
        ctx.html(ThymeleafConfig.render("consultas/form", Map.of(
                "consulta", c,
                "pacientes", pacienteRepo.buscarTodos(),
                "medicos",   medicoRepo.buscarTodos(),
                "titulo",    "Editar Consulta",
                "acao",      "/consultas/" + c.getCodC() + "/editar"
        )));
    }

    public void atualizar(Context ctx) {
        try {
            Consulta c = consultaRepo.buscarPorCod(Integer.parseInt(ctx.pathParam("cod")));
            Paciente p = pacienteRepo.buscarPorCod(Integer.parseInt(ctx.formParam("codP")));
            Medico   m = medicoRepo.buscarPorCod(Integer.parseInt(ctx.formParam("codM")));
            LocalDate data = LocalDate.parse(ctx.formParam("data"));
            double preco  = Double.parseDouble(ctx.formParam("preco"));
            c.setPaciente(p);
            c.setMedico(m);
            c.setData(data);
            c.setDiagnostico(ctx.formParam("diagnostico"));
            c.setPreco(preco);
            consultaRepo.atualizar(c);
            ctx.redirect("/consultas");
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("consulta", consultaRepo.buscarPorCod(Integer.parseInt(ctx.pathParam("cod"))));
            model.put("pacientes", pacienteRepo.buscarTodos());
            model.put("medicos",   medicoRepo.buscarTodos());
            model.put("titulo",    "Editar Consulta");
            model.put("acao",      "/consultas/" + ctx.pathParam("cod") + "/editar");
            model.put("erro",      e.getMessage());
            ctx.html(ThymeleafConfig.render("consultas/form", model));
        }
    }

    public void cancelar(Context ctx) {
        consultaRepo.remover(Integer.parseInt(ctx.pathParam("cod")));
        ctx.redirect("/consultas");
    }
}