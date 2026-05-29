package SistemaHospital.Controller;

import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.HospitalException;
import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.*;

public class MedicoController {
    private final GerenciaHospital hospital;

    public MedicoController(GerenciaHospital hospital) {
        this.hospital = hospital;
    }

    public void listar(Context ctx) {
        Map<String, Object> model = Map.of("medicos", hospital.todosMedicos());
        ctx.html(ThymeleafConfig.render("medicos/lista", model));
    }
    public void formNovo(Context ctx) {
        ctx.html(ThymeleafConfig.render("medicos/form", Map.of(
                "sexos", Sexo.values(),
                "acao", "/medicos/novo",
                "titulo", "Novo Médico"
        )));
    }
    public void cadastrar(Context ctx) {
        try{
            hospital.cadastrarMedico(
                    ctx.formParam("nome"),
                    ctx.formParam("CPF"),
                    Sexo.valueOf(ctx.formParam("sexo")),
                    ctx.formParam("especialidade"),
                    ctx.formParam("turno"),
                    Double.parseDouble(Objects.requireNonNull(ctx.formParam("salario")))
            );
            ctx.redirect("/medicos");
        } catch (HospitalException e){
            Map<String, Object> model = Map.of(
                    "acao", "/medicos/novo",
                    "titulo", "Novo Médico",
                    "erro", e.getMessage()
            );
            ctx.html(ThymeleafConfig.render("medicos/form", model));
        }
    }
    public void remover(Context ctx) {
        try {
            hospital.removerMedico(Integer.parseInt(ctx.pathParam("cod")));
        } catch (HospitalException ignored) {}
        ctx.redirect("/medicos");
    }
    public void formEditar(Context ctx) {
        try {
            int cod = Integer.parseInt(ctx.pathParam("cod"));
            var medico = hospital.pesquisarMedico(cod);
            Map<String, Object> model = new HashMap<>();
            model.put("medico", medico);
            model.put("sexos", Sexo.values());
            model.put("acao", "/medicos/" + cod + "/editar");
            model.put("titulo", "Editar Médico");
            ctx.html(ThymeleafConfig.render("medicos/form", model));
        } catch (HospitalException e) {
            ctx.redirect("/medicos");
        }
    }
    public void atualizar(Context ctx) {
        try {
            int cod = Integer.parseInt(ctx.pathParam("cod"));
            hospital.atualizarMedico(
                    cod,
                    Optional.ofNullable(ctx.formParam("nome")),
                    Optional.ofNullable(ctx.formParam("CPF")),
                    Optional.ofNullable(ctx.formParam("sexo")).map(Sexo::valueOf),
                    Optional.ofNullable(ctx.formParam("especialidade")),
                    Optional.ofNullable(ctx.formParam("turno")),
                    OptionalDouble.of(Double.parseDouble(ctx.formParam("salario")))
            );
            ctx.redirect("/medicos");
        } catch (HospitalException e) {
            ctx.redirect("/medicos");
        }
    }
}