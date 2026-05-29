package SistemaHospital.Controller;

import SistemaHospital.Exceptions.HospitalException;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ConsultaController {

    private final GerenciaHospital hospital;

    public ConsultaController(GerenciaHospital hospital) {
        this.hospital = hospital;
    }

    public void listar(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("consultas", hospital.todasConsultas());
        ctx.html(ThymeleafConfig.render("consultas/lista", model));
    }

    public void formNova(Context ctx) {
        ctx.html(ThymeleafConfig.render("consultas/form", Map.of(
                "pacientes", hospital.todosPacientes(),
                "medicos",   hospital.todosMedicos(),
                "titulo",    "Nova Consulta",
                "acao",      "/consultas/nova"
        )));
    }

    public void cadastrar(Context ctx) {
        try {
            var paciente = hospital.pesquisarPaciente(Integer.parseInt(ctx.formParam("codP")));
            var medico   = hospital.pesquisarMedico(Integer.parseInt(ctx.formParam("codM")));
            var data     = LocalDate.parse(ctx.formParam("data"));
            double preco = Double.parseDouble(ctx.formParam("preco"));
            hospital.cadastrarConsulta(paciente, medico, data, ctx.formParam("diagnostico"), preco);
            ctx.redirect("/consultas");
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("pacientes",  hospital.todosPacientes());
            model.put("medicos",    hospital.todosMedicos());
            model.put("titulo",     "Nova Consulta");
            model.put("acao",       "/consultas/nova");
            model.put("erro",       e.getMessage());
            ctx.html(ThymeleafConfig.render("consultas/form", model));
        }
    }

    public void cancelar(Context ctx) {
        try {
            hospital.cancelarConsulta(Integer.parseInt(ctx.pathParam("cod")));
        } catch (HospitalException ignored) {}
        ctx.redirect("/consultas");
    }
}