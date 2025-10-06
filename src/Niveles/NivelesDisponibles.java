package Niveles;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import rpgingles.Personaje;

public class NivelesDisponibles {

    @FXML private Pane lienzoNiveles;
    @FXML private Label resistenciaLabel;
    
    private Personaje jugador;
    private List<Nivel> listaDeNiveles;
    private Map<Integer, Point> coordenadasNiveles;

    public void initData(Personaje personaje) {
        this.jugador = personaje;
        this.listaDeNiveles = new ArrayList<>();
        listaDeNiveles.add(new Nivel(1, "Fuerza Bruta", true));
        listaDeNiveles.add(new Nivel(2, "Valle de Ratas", true));
        listaDeNiveles.add(new Nivel(3, "Ciudad de Duendes", true));
        for(Nivel n : listaDeNiveles) {
            if(n.getId() <= jugador.getMaxNivelDesbloqueado()) {
                n.desbloquear();
            }
        }
        definirCoordenadas();
        dibujarCaminos();
        dibujarNiveles();
        actualizarUI();
    }
    
    private void dibujarNiveles() {
        for (Nivel nivel : listaDeNiveles) {
            Point coords = coordenadasNiveles.get(nivel.getId());
            if (coords == null) continue;
            Button botonNivel = new Button(String.valueOf(nivel.getId()));
            botonNivel.setLayoutX(coords.x);
            botonNivel.setLayoutY(coords.y);
            botonNivel.setOnAction(e -> {
                if (jugador.puedeJugarNivel(nivel)) {
                    iniciarPrueba(nivel);
                } else {
                    System.out.println("No puedes jugar el nivel: " + nivel.getNombre());
                }
            });
            if (nivel.isBloqueado()) {
                botonNivel.setDisable(true);
                botonNivel.setStyle("-fx-background-color: grey;");
            } else {
                 botonNivel.setStyle("-fx-background-color: orange; -fx-font-weight: bold; -fx-text-fill: white;");
            }
            lienzoNiveles.getChildren().add(botonNivel);
        }
    }

    private void iniciarPrueba(Nivel nivel) {
        try {
            // CAMBIO: Cargar el FXML con su nuevo nombre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Niveles/AjustesNiveles.fxml"));
            Parent pruebaRoot = loader.load();
            
            // CAMBIO: Obtener el controlador con su nuevo nombre de clase
            AjustesNiveles controller = loader.getController();
            controller.initData(jugador, nivel);

            Stage stage = (Stage) lienzoNiveles.getScene().getWindow();
            Scene scene = new Scene(pruebaRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de la prueba:");
            ex.printStackTrace();
        }
    }
    
    // Métodos sin cambios
    private void dibujarCaminos() {}
    private void definirCoordenadas() {
        coordenadasNiveles = new HashMap<>();
        coordenadasNiveles.put(1, new Point(100, 200));
        coordenadasNiveles.put(2, new Point(250, 280));
        coordenadasNiveles.put(3, new Point(400, 200));
    }
    private void actualizarUI() {
        resistenciaLabel.setText("Resistencia: " + (int)jugador.getResistencia());
    }
}