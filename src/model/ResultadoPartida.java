package model;

import java.time.LocalDateTime;

public class ResultadoPartida {

    private LocalDateTime dataJogo;
    private Move jogadaJogador;
    private Move jogadaMaquina;
    private String resultado; // "VITORIA", "DERROTA" ou "EMPATE"

    public ResultadoPartida() {
    }

    public ResultadoPartida(LocalDateTime dataJogo, Move jogadaJogador, Move jogadaMaquina, String resultado) {
        this.dataJogo = dataJogo;
        this.jogadaJogador = jogadaJogador;
        this.jogadaMaquina = jogadaMaquina;
        this.resultado = resultado;
    }

    public LocalDateTime getDataJogo() {
        return dataJogo;
    }

    public void setDataJogo(LocalDateTime dataJogo) {
        this.dataJogo = dataJogo;
    }

    public Move getJogadaJogador() {
        return jogadaJogador;
    }

    public void setJogadaJogador(Move jogadaJogador) {
        this.jogadaJogador = jogadaJogador;
    }

    public Move getJogadaMaquina() {
        return jogadaMaquina;
    }

    public void setJogadaMaquina(Move jogadaMaquina) {
        this.jogadaMaquina = jogadaMaquina;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "ResultadoPartida{" +
                "dataJogo=" + dataJogo +
                ", jogadaJogador=" + jogadaJogador +
                ", jogadaMaquina=" + jogadaMaquina +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
