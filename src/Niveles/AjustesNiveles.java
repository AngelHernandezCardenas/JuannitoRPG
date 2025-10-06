package Niveles;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;
import rpgingles.Personaje;

public class AjustesNiveles {

    @FXML private ProgressBar energiaBar;
    
    private Personaje jugador;
    private Nivel nivelActual;
    
    private Timeline timeline;
    private IntegerProperty tiempoRestante;
    private int tiempoTotal;

    public void initData(Personaje personaje, Nivel nivel) {
        this.jugador = personaje;
        this.nivelActual = nivel;
        configurarPrueba();
    }
    //Cada jugador tiene distinto tiempo en su dificultad
    private void configurarPrueba() {
        switch (jugador.getNivelIngles()) {
            case "Básico":       tiempoTotal = 150; break;
            case "Normal":       tiempoTotal = 120; break;
            case "Difícil":      tiempoTotal = 90;  break;
            case "Migthy Force": tiempoTotal = 59;  break;
            default:             tiempoTotal = 120;
        }
        this.tiempoRestante = new SimpleIntegerProperty(tiempoTotal);
        iniciarTimer();
    }
    
    private void iniciarTimer() {
        actualizarUI();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante.set(tiempoRestante.get() - 1);
            actualizarUI();
            if (tiempoRestante.get() <= 0) {
                timeline.stop();
                fallarPrueba(); // Se llama cuando el tiempo llega a cero
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    private void actualizarUI() {
        energiaBar.setProgress((double) tiempoRestante.get() / tiempoTotal);
    }
    
    /**
     * CAMBIO: Este método ahora contiene la lógica para volver al mapa.
     */
    private void fallarPrueba() {
        System.out.println("¡Energía agotada! Volviendo al mapa.");
        
        try {
            // Cargar el FXML del mapa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Niveles/NivelesDisponibles.fxml"));
            Parent mapaRoot = loader.load();

            // Obtener el controlador del mapa para pasarle los datos actualizados
            NivelesDisponibles controller = loader.getController();
            controller.initData(jugador); // Le pasamos el jugador de vuelta

            // Obtener la ventana actual y cambiar la escena
            Stage stage = (Stage) energiaBar.getScene().getWindow();
            Scene scene = new Scene(mapaRoot);
            stage.setScene(scene);
            stage.setTitle("Mapa de Aventura - " + jugador.getNombre());
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al volver a la pantalla del mapa:");
            e.printStackTrace();
        }
    }
    
    public void superarPrueba() {
        timeline.stop();
        System.out.println("¡Correcto! Has superado la prueba.");
        // Aquí iría la lógica para volver al mapa pero con un mensaje de "ganaste"
    }
}