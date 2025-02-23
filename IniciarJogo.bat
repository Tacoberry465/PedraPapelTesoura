@echo off
cd /d "C:\Jefferson\Projeto Jogo Pedra Papel Tesoura no Eclipse e Github\PedraPapelTesoura"
java --module-path "C:\Jefferson\javafx-sdk-17.0.14\lib" --add-modules javafx.controls,javafx.fxml -jar jogo-pedra-papel-tesoura.jar
pause
