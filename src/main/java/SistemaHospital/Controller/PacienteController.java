package SistemaHospital.Controller;

import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.HospitalException;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class PacienteController {

    private final GerenciaHospital hospital;

    public PacienteController(GerenciaHospital hospital) {
        this.hospital = hospital;
    }

    public void listar(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("pacientes", hospital.todosPacientes());
        ctx.html(ThymeleafConfig.render("pacientes/lista", model));
    }
    public void formNovo(Context ctx) {
        ctx.html(ThymeleafConfig.render("pacientes/form", Map.of(
                "sexos", Sexo.values(),
                "acao", "/pacientes/novo",
                "titulo", "Novo Paciente"
        )));
    }

    public void cadastrar(Context ctx) {
        try {
            hospital.cadastrarPaciente(
                    ctx.formParam("nome"),
                    ctx.formParam("cpf"),
                    Sexo.valueOf(ctx.formParam("sexo")),
                    Integer.parseInt(ctx.formParam("idade")),
                    ctx.formParam("convenio")
            );
            ctx.redirect("/pacientes");
        } catch (HospitalException e) {
            Map<String, Object> model = new HashMap<>();
            model.put("sexos", Sexo.values());
            model.put("acao", "/pacientes/novo");
            model.put("titulo", "Novo Paciente");
            model.put("erro", e.getMessage());
            ctx.html(ThymeleafConfig.render("pacientes/form", model));
        }
    }
    public void remover(Context ctx) {
        try {
            hospital.removerPaciente(Integer.parseInt(ctx.pathParam("cod")));
        } catch (HospitalException ignored) {}
        ctx.redirect("/pacientes");
    }
    public void formEditar(Context ctx) {
        try {
            int cod = Integer.parseInt(ctx.pathParam("cod"));
            var paciente = hospital.pesquisarPaciente(cod);
            Map<String, Object> model = new HashMap<>();
            model.put("paciente", paciente);
            model.put("sexos", Sexo.values());
            model.put("acao", "/pacientes/" + cod + "/editar");
            model.put("titulo", "Editar Paciente");
            ctx.html(ThymeleafConfig.render("pacientes/form", model));
        } catch (HospitalException e) {
            ctx.redirect("/pacientes");
        }
    }

    public void atualizar(Context ctx) {
        int cod = Integer.parseInt(ctx.pathParam("cod"));
        try {
            hospital.atualizarPaciente(
                    cod,
                    Optional.ofNullable(ctx.formParam("nome")),
                    Optional.ofNullable(ctx.formParam("cpf")),
                    Optional.ofNullable(ctx.formParam("sexo")).map(Sexo::valueOf),
                    ctx.formParam("idade") != null
                            ? OptionalInt.of(Integer.parseInt(ctx.formParam("idade")))
                            : OptionalInt.empty(),
                    Optional.ofNullable(ctx.formParam("convenio"))
            );
            ctx.redirect("/pacientes");
        } catch (HospitalException e) {
            ctx.redirect("/pacientes");
        }
    }
}