package SistemaHospital.Repository;

import SistemaHospital.ConexaoDB;
import SistemaHospital.Enum.Sexo;
import SistemaHospital.Model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    public Paciente salvar(Paciente p) {
        String sql = "INSERT INTO pacientes (nome, cpf, sexo, idade, convenio) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING cod_p";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCPF());
            stmt.setString(3, p.getSexo().name());
            stmt.setInt   (4, p.getIdade());
            stmt.setString(5, p.getConvenio());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p.setCodP(rs.getInt("cod_p"));
            }
            return p;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new RuntimeException("CPF já cadastrado no sistema.");
            }
            throw new RuntimeException("Erro ao salvar médico: " + e.getMessage());
        }
    }

    public List<Paciente> buscarTodos() {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pacientes: " + e.getMessage());
        }
        return lista;
    }

    public Paciente buscarPorCod(int cod) {
        String sql = "SELECT * FROM pacientes WHERE cod_p = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente: " + e.getMessage());
        }
        return null;
    }

    public Paciente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM pacientes WHERE cpf = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por CPF: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Paciente p) {
        String sql = "UPDATE pacientes SET nome=?, cpf=?, sexo=?, idade=?, convenio=? " +
                "WHERE cod_p=?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCPF());
            stmt.setString(3, p.getSexo().name());
            stmt.setInt   (4, p.getIdade());
            stmt.setString(5, p.getConvenio());
            stmt.setInt   (6, p.getCodP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    public void remover(int cod) {
        String sql = "DELETE FROM pacientes WHERE cod_p = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover paciente: " + e.getMessage());
        }
    }

    public List<Paciente> buscarPorNome(String nome) {
        String sql = "SELECT * FROM pacientes WHERE nome ILIKE ?";
        List<Paciente> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pacientes por nome: " + e.getMessage());
        }
        return lista;
    }

    public List<Paciente> buscarPorConvenio(String convenio) {
        String sql = "SELECT * FROM pacientes WHERE convenio ILIKE ?";
        List<Paciente> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + convenio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pacientes por convênio: " + e.getMessage());
        }
        return lista;
    }

    public Paciente mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente(
                rs.getString("nome"),
                rs.getString("cpf"),
                Sexo.valueOf(rs.getString("sexo")),
                rs.getInt("idade"),
                rs.getString("convenio")
        );
        p.setCodP(rs.getInt("cod_p"));
        return p;
    }
}