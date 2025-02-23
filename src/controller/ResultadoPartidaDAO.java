package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.ResultadoPartida;
import model.Move;


public class ResultadoPartidaDAO {

    // Ajuste conforme seu ambiente
    private static final String URL  = "jdbc:mysql://localhost:3306/jogo_pedra_papel_tesoura";
    private static final String USER = "root";       // usuário do MySQL
    private static final String PASS = "X1a7b2605***";       // senha do MySQL

    public void inserirResultado(ResultadoPartida partida) {
        String sql = "INSERT INTO historico (data_jogo, jogada_jogador, jogada_maquina, resultado) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Atribuir valores
            ps.setTimestamp(1, Timestamp.valueOf(partida.getDataJogo()));
            ps.setString(2, partida.getJogadaJogador().name());
            ps.setString(3, partida.getJogadaMaquina().name());
            ps.setString(4, partida.getResultado());

            // Executar
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ResultadoPartida> consultarHistorico() {
        List<ResultadoPartida> lista = new ArrayList<>();
        String sql = "SELECT data_jogo, jogada_jogador, jogada_maquina, resultado FROM historico";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ResultadoPartida partida = new ResultadoPartida();
                partida.setDataJogo(rs.getTimestamp("data_jogo").toLocalDateTime());
                partida.setJogadaJogador(Move.valueOf(rs.getString("jogada_jogador")));
                partida.setJogadaMaquina(Move.valueOf(rs.getString("jogada_maquina")));
                partida.setResultado(rs.getString("resultado"));
                lista.add(partida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public double consultarPorcentagemVitorias() {
        String sqlTotal = "SELECT COUNT(*) AS total FROM historico";
        String sqlVitorias = "SELECT COUNT(*) AS vitorias FROM historico WHERE resultado = 'VITORIA'";

        int total = 0;
        int vitorias = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // total
            try (PreparedStatement ps = conn.prepareStatement(sqlTotal);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
            // vitorias
            try (PreparedStatement ps = conn.prepareStatement(sqlVitorias);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vitorias = rs.getInt("vitorias");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (total == 0) {
            return 0.0;
        }
        return (vitorias / (double) total) * 100.0;
    }
}