package view;

import controller.ResultadoPartidaDAO;
import model.Move;
import model.ResultadoPartida;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDateTime;
import java.util.Random;

public class Main extends Application {

    private Label lblResultado;
    private ImageView imgJogadaMaquina;
    private ResultadoPartidaDAO dao = new ResultadoPartidaDAO();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jogo Pedra-Papel-Tesoura");

        // Labels
        Label lblEscolha = new Label("Escolha sua jogada:");
        lblResultado = new Label("Resultado: ");
        Label lblJogadaMaquina = new Label("Jogada da máquina:");

        // Imagens clicáveis para o jogador
        ImageView imgPedra = criarImagem("pedra.png", Move.PEDRA);
        ImageView imgPapel = criarImagem("papel.png", Move.PAPEL);
        ImageView imgTesoura = criarImagem("tesoura.png", Move.TESOURA);

        // Imagem da jogada da máquina (inicialmente vazia)
        imgJogadaMaquina = new ImageView();
        imgJogadaMaquina.setFitWidth(100);
        imgJogadaMaquina.setPreserveRatio(true);

        // Botão para consultar histórico
        Button btnHistorico = new Button("Consultar Histórico");
        btnHistorico.setOnAction(e -> mostrarHistorico());

        // Layouts
        HBox hboxEscolhas = new HBox(20, imgPedra, imgPapel, imgTesoura);
        VBox vboxMaquina = new VBox(10, lblJogadaMaquina, imgJogadaMaquina, lblResultado);
        VBox root = new VBox(20, lblEscolha, hboxEscolhas, vboxMaquina, btnHistorico);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para criar imagem clicável
    private ImageView criarImagem(String nomeArquivo, Move jogada) {
        String caminhoImagem = "file:resources/" + nomeArquivo;
        Image img = new Image(caminhoImagem);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(100);
        imgView.setPreserveRatio(true);
        imgView.setOnMouseClicked(event -> processarJogada(jogada));
        return imgView;
    }

    // Método para processar a jogada do jogador
    private void processarJogada(Move jogadaJogador) {
        Move jogadaMaquina = jogadaAleatoria();

        // Atualiza a imagem da jogada da máquina
        imgJogadaMaquina.setImage(new Image("file:resources/" + jogadaMaquina.name().toLowerCase() + ".png"));



        // Determinar resultado
        String resultado = determinarResultado(jogadaJogador, jogadaMaquina);
        lblResultado.setText("Resultado: " + resultado);

        // Salvar no banco
        ResultadoPartida partida = new ResultadoPartida(LocalDateTime.now(), jogadaJogador, jogadaMaquina, resultado);
        dao.inserirResultado(partida);
    }

    // Método para escolher jogada aleatória da máquina
    private Move jogadaAleatoria() {
        Move[] moves = Move.values();
        return moves[new Random().nextInt(moves.length)];
    }

    // Método para determinar o resultado do jogo
    private String determinarResultado(Move jogador, Move maquina) {
        if (jogador == maquina) {
            return "EMPATE";
        }
        switch (jogador) {
            case PEDRA:
                return (maquina == Move.TESOURA) ? "VITORIA" : "DERROTA";
            case PAPEL:
                return (maquina == Move.PEDRA) ? "VITORIA" : "DERROTA";
            case TESOURA:
                return (maquina == Move.PAPEL) ? "VITORIA" : "DERROTA";
            default:
                return "EMPATE";
        }
    }

    // Método para mostrar o histórico de vitórias
    private void mostrarHistorico() {
        double porcentagem = dao.consultarPorcentagemVitorias();

        // Criando um alerta para exibir a porcentagem na interface
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Histórico de Vitórias");
        alert.setHeaderText("Estatísticas do Jogo");
        alert.setContentText(String.format("Sua porcentagem de vitórias é: %.2f%%", porcentagem));

        alert.showAndWait(); // Exibe a caixa de diálogo
    }

    public static void main(String[] args) {
        launch(args);
    }
}
