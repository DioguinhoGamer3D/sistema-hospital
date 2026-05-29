package SistemaHospital.Controller;

import SistemaHospital.Gerencias.GerenciaHospital;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class RelatorioController {

    private final GerenciaHospital hospital;

    public RelatorioController(GerenciaHospital hospital) {
        this.hospital = hospital;
    }

    public void index(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("faturamento",    hospital.calcularFaturamento());
        model.put("topMedicos",     hospital.medicoComMaisConsultas());
        model.put("topPacientes",   hospital.pacienteMaisFrequente());
        model.put("totalPacientes", hospital.todosPacientes().size());
        model.put("totalMedicos",   hospital.todosMedicos().size());
        model.put("totalConsultas", hospital.todasConsultas().size());
        ctx.html(ThymeleafConfig.render("relatorios/index", model));
    }
}