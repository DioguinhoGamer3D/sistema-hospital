package SistemaHospital.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Enum.Sexo;
import SistemaHospital.Model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {

    public Medico salvar(Medico m) {
        String sql = "INSERT INTO medicos (nome, cpf, sexo, especialidade, turno, salario) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING cod_m";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getCPF());
            stmt.setString(3, m.getSexo().name());
            stmt.setString(4, m.getEspecialidade());
            stmt.setString(5, m.getTurno());
            stmt.setDouble(6, m.getSalario());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) m.setCodM(rs.getInt("cod_m"));
            return m;
        } catch (SQLException e) {
        if (e.getSQLState().equals("23505")) {
            throw new RuntimeException("CPF já cadastrado no sistema.");
        }
        throw new RuntimeException("Erro ao salvar médico: " + e.getMessage());
    }
    }

    public List<Medico> buscarTodos() {
        String sql = "SELECT * FROM medicos";
        List<Medico> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médicos: " + e.getMessage());
        }
        return lista;
    }

    public Medico buscarPorCod(int cod) {
        String sql = "SELECT * FROM medicos WHERE cod_m = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico: " + e.getMessage());
        }
        return null;
    }

    public List<Medico> buscarPorEspecialidade(String especialidade) {
        String sql = "SELECT * FROM medicos WHERE especialidade ILIKE ?";
        List<Medico> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + especialidade + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por especialidade: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Medico m) {
        String sql = "UPDATE medicos SET nome=?, cpf=?, sexo=?, especialidade=?, turno=?, salario=? " +
                "WHERE cod_m=?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getCPF());
            stmt.setString(3, m.getSexo().name());
            stmt.setString(4, m.getEspecialidade());
            stmt.setString(5, m.getTurno());
            stmt.setDouble(6, m.getSalario());
            stmt.setInt   (7, m.getCodM());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico: " + e.getMessage());
        }
    }

    public void remover(int cod) {
        String sql = "DELETE FROM medicos WHERE cod_m = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover médico: " + e.getMessage());
        }
    }

    public Medico mapear(ResultSet rs) throws SQLException {
        Medico m = new Medico(
                rs.getString("nome"),
                rs.getString("cpf"),
                Sexo.valueOf(rs.getString("sexo")),
                rs.getString("especialidade"),
                rs.getString("turno"),
                rs.getDouble("salario")
        );
        m.setCodM(rs.getInt("cod_m"));
        return m;
    }
}