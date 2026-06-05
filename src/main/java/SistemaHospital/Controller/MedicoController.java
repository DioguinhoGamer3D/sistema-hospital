package SistemaHospital.Controller;

import SistemaHospital.Uteis.Enum.Sexo;
import SistemaHospital.Model.Medico;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class MedicoController {

    private final MedicoRepository repo;

    public MedicoController(MedicoRepository repo) {
        this.repo = repo;
    }

    public void listar(Context ctx) {
        String busca   = ctx.queryParam("busca");
        String tipo    = ctx.queryParam("tipo");
        Map<String, Object> model = new HashMap<>();
        if (busca != null && !busca.isBlank()) {
            var lista = "especialidade".equals(tipo)
                    ? repo.buscarPorEspecialidade(busca)
                    : repo.buscarPorNome(busca);
            model.put("medicos", lista);
            model.put("busca", busca);
            model.put("tipo",  tipo);
        } else {
            model.put("medicos", repo.buscarTodos());
        }
        ctx.html(ThymeleafConfig.render("medicos/lista", model));
    }

    public void formNovo(Context ctx) {
        ctx.html(ThymeleafConfig.render("medicos/form", Map.of(
                "sexos",  Sexo.values(),
                "acao",   "/medicos/novo",
                "titulo", "Novo Médico"
        )));
    }

    public void cadastrar(Context ctx) {
        try {
            Medico m = new Medico(
                    ctx.formParam("nome"),
                    ctx.formParam("CPF"),
                    Sexo.valueOf(ctx.formParam("sexo")),
                    ctx.formParam("especialidade"),
                    ctx.formParam("turno"),
                    Double.parseDouble(ctx.formParam("salario"))
            );
            repo.salvar(m);
            ctx.redirect("/medicos");
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("sexos",  Sexo.values());
            model.put("acao",   "/medicos/novo");
            model.put("titulo", "Novo Médico");
            model.put("erro",   e.getMessage());
            ctx.html(ThymeleafConfig.render("medicos/form", model));
        }
    }

    public void formEditar(Context ctx) {
        int cod = Integer.parseInt(ctx.pathParam("cod"));
        Medico m = repo.buscarPorCod(cod);
        if (m == null) { ctx.redirect("/medicos"); return; }
        Map<String, Object> model = new HashMap<>();
        model.put("medico",  m);
        model.put("sexos",   Sexo.values());
        model.put("acao",    "/medicos/" + cod + "/editar");
        model.put("titulo",  "Editar Médico");
        ctx.html(ThymeleafConfig.render("medicos/form", model));
    }

    public void atualizar(Context ctx) {
        int cod = Integer.parseInt(ctx.pathParam("cod"));
        Medico m = repo.buscarPorCod(cod);
        if (m == null) { ctx.redirect("/medicos"); return; }
        m.setNome(ctx.formParam("nome"));
        m.setCPF(ctx.formParam("CPF"));
        m.setSexo(Sexo.valueOf(ctx.formParam("sexo")));
        m.setEspecialidade(ctx.formParam("especialidade"));
        m.setTurno(ctx.formParam("turno"));
        m.setSalario(Double.parseDouble(ctx.formParam("salario")));
        repo.atualizar(m);
        ctx.redirect("/medicos");
    }

    public void remover(Context ctx) {
        repo.remover(Integer.parseInt(ctx.pathParam("cod")));
        ctx.redirect("/medicos");
    }

}