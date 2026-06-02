package SistemaHospital;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static Connection conectar() {
        String url   = get("DB_URL",      "jdbc:postgresql://localhost:5432/hospital");
        String user  = get("DB_USER",     "postgres");
        String senha = get("DB_PASSWORD", "");
        try {
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco: " + e.getMessage());
        }
    }

    private static String get(String key, String defaultValue) {
        String env = System.getenv(key);
        if (env != null) return env;
        String dot = dotenv.get(key, null);
        if (dot != null) return dot;
        return defaultValue;
    }
}