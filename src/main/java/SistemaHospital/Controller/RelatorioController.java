package SistemaHospital.Controller;

import SistemaHospital.Repository.ConsultaRepository;
import SistemaHospital.Repository.MedicoRepository;
import SistemaHospital.Repository.PacienteRepository;
import SistemaHospital.ThymeleafConfig;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class RelatorioController {

    private final PacienteRepository pacienteRepo;
    private final MedicoRepository   medicoRepo;
    private final ConsultaRepository consultaRepo;

    public RelatorioController(PacienteRepository pacienteRepo,
                               MedicoRepository   medicoRepo,
                               ConsultaRepository consultaRepo) {
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo   = medicoRepo;
        this.consultaRepo = consultaRepo;
    }

    public void index(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("faturamento",    consultaRepo.calcularFaturamento());
        model.put("totalPacientes", pacienteRepo.buscarTodos().size());
        model.put("totalMedicos",   medicoRepo.buscarTodos().size());
        model.put("totalConsultas", consultaRepo.buscarTodas().size());
        ctx.html(ThymeleafConfig.render("relatorios/index", model));
    }
}