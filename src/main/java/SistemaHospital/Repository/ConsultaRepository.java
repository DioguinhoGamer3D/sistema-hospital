package SistemaHospital.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Model.Consulta;
import SistemaHospital.Model.Medico;
import SistemaHospital.Model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    private final PacienteRepository pacienteRepo;
    private final MedicoRepository   medicoRepo;

    public ConsultaRepository(PacienteRepository pacienteRepo, MedicoRepository medicoRepo) {
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo   = medicoRepo;
    }

    public Consulta salvar(Consulta c) {
        String sql = "INSERT INTO consultas (cod_p, cod_m, data, diagnostico, preco) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING cod_c";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt   (1, c.getPaciente().getCodP());
            stmt.setInt   (2, c.getMedico().getCodM());
            stmt.setDate  (3, Date.valueOf(c.getData()));
            stmt.setString(4, c.getDiagnostico());
            stmt.setDouble(5, c.getPreco());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) c.setCodC(rs.getInt("cod_c"));
            return c;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage());
        }
    }

    public List<Consulta> buscarTodas() {
        String sql = "SELECT * FROM consultas";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage());
        }
        return lista;
    }

    public Consulta buscarPorCod(int cod) {
        String sql = "SELECT * FROM consultas WHERE cod_c = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage());
        }
        return null;
    }

    public List<Consulta> buscarPorPaciente(int codP) {
        String sql = "SELECT * FROM consultas WHERE cod_p = ?";
        return buscarLista(sql, codP);
    }

    public List<Consulta> buscarPorMedico(int codM) {
        String sql = "SELECT * FROM consultas WHERE cod_m = ?";
        return buscarLista(sql, codM);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        String sql = "SELECT * FROM consultas WHERE data = ?";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por data: " + e.getMessage());
        }
        return lista;
    }

    public void remover(int cod) {
        String sql = "DELETE FROM consultas WHERE cod_c = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover consulta: " + e.getMessage());
        }
    }

    public double calcularFaturamento() {
        String sql = "SELECT COALESCE(SUM(preco), 0) FROM consultas";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular faturamento: " + e.getMessage());
        }
        return 0;
    }

    private List<Consulta> buscarLista(String sql, int cod) {
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Consulta c) {
        String sql = "UPDATE consultas SET cod_p=?, cod_m=?, data=?, diagnostico=?, preco=? " +
                "WHERE cod_c=?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt   (1, c.getPaciente().getCodP());
            stmt.setInt   (2, c.getMedico().getCodM());
            stmt.setDate  (3, Date.valueOf(c.getData()));
            stmt.setString(4, c.getDiagnostico());
            stmt.setDouble(5, c.getPreco());
            stmt.setInt   (6, c.getCodC());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        Paciente paciente = pacienteRepo.buscarPorCod(rs.getInt("cod_p"));
        Medico   medico   = medicoRepo.buscarPorCod(rs.getInt("cod_m"));
        Consulta c = new Consulta(
                paciente,
                medico,
                rs.getDate("data").toLocalDate(),
                rs.getString("diagnostico"),
                rs.getDouble("preco")
        );
        c.setCodC(rs.getInt("cod_c"));
        return c;
    }
}