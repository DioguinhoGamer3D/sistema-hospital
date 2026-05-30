package SistemaHospital.Controller;

import SistemaHospital.Enum.Sexo;
import SistemaHospital.Model.Paciente;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class PacienteController {

    private final PacienteRepository repo;

    public PacienteController(PacienteRepository repo) {
        this.repo = repo;
    }

    public void listar(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("pacientes", repo.buscarTodos());
        ctx.html(ThymeleafConfig.render("pacientes/lista", model));
    }

    public void formNovo(Context ctx) {
        ctx.html(ThymeleafConfig.render("pacientes/form", Map.of(
                "sexos",  Sexo.values(),
                "acao",   "/pacientes/novo",
                "titulo", "Novo Paciente"
        )));
    }

    public void cadastrar(Context ctx) {
        try {
            Paciente p = new Paciente(
                    ctx.formParam("nome"),
                    ctx.formParam("cpf"),
                    Sexo.valueOf(ctx.formParam("sexo")),
                    Integer.parseInt(ctx.formParam("idade")),
                    ctx.formParam("convenio")
            );
            repo.salvar(p);
            ctx.redirect("/pacientes");
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("sexos",  Sexo.values());
            model.put("acao",   "/pacientes/novo");
            model.put("titulo", "Novo Paciente");
            model.put("erro",   e.getMessage());
            ctx.html(ThymeleafConfig.render("pacientes/form", model));
        }
    }

    public void formEditar(Context ctx) {
        int cod = Integer.parseInt(ctx.pathParam("cod"));
        Paciente p = repo.buscarPorCod(cod);
        if (p == null) { ctx.redirect("/pacientes"); return; }
        Map<String, Object> model = new HashMap<>();
        model.put("paciente", p);
        model.put("sexos",    Sexo.values());
        model.put("acao",     "/pacientes/" + cod + "/editar");
        model.put("titulo",   "Editar Paciente");
        ctx.html(ThymeleafConfig.render("pacientes/form", model));
    }

    public void atualizar(Context ctx) {
        int cod = Integer.parseInt(ctx.pathParam("cod"));
        Paciente p = repo.buscarPorCod(cod);
        if (p == null) { ctx.redirect("/pacientes"); return; }
        p.setNome(ctx.formParam("nome"));
        p.setCPF(ctx.formParam("cpf"));
        p.setSexo(Sexo.valueOf(ctx.formParam("sexo")));
        p.setIdade(Integer.parseInt(ctx.formParam("idade")));
        p.setConvenio(ctx.formParam("convenio"));
        repo.atualizar(p);
        ctx.redirect("/pacientes");
    }

    public void remover(Context ctx) {
        repo.remover(Integer.parseInt(ctx.pathParam("cod")));
        ctx.redirect("/pacientes");
    }

    public void buscarPorCpf(Context ctx) {
        String cpf = ctx.queryParam("cpf");
        Map<String, Object> model = new HashMap<>();
        if (cpf != null && !cpf.isBlank()) {
            Paciente p = repo.buscarPorCpf(cpf);
            if (p != null) model.put("resultado", p);
            else model.put("erro", "Paciente não encontrado para o CPF: " + cpf);
            model.put("cpf", cpf);
        }
        ctx.html(ThymeleafConfig.render("pacientes/buscar", model));
    }
}