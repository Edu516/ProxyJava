package Servidor;

/**
 *
 * @author Usuario
 */

import Base.Anotacao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgresDatabase {
    private String url;
    private String user;
    private String password;

    public PostgresDatabase(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Anotacao getAnotacao(int id) {
        Anotacao anotacao = null;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, conteudo FROM anotacoes WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        anotacao = new Anotacao(rs.getInt("id"), rs.getString("conteudo"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anotacao;
    }
}