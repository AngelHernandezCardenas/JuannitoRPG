package rpgingles;

import Niveles.NivelesDisponibles;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModPersonaje {

    @FXML private VBox rootPane;
    @FXML private TextField nombreField;
    @FXML private Slider edadSlider;
    @FXML private Label edadLabel;
    @FXML private ComboBox<String> nivelInglesBox;
    @FXML private ComboBox<String> tipoUsuarioBox;
    @FXML private ComboBox<String> sexoBox;
    
    private Personaje personajeCreado;

    @FXML
    public void initialize() {
        edadSlider.setMin(6);
        edadSlider.setMax(99);
        edadSlider.setValue(18);
        edadLabel.setText("Edad: " + (int) edadSlider.getValue());
        edadSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            edadLabel.setText("Edad: " + newVal.intValue());
        });
        nivelInglesBox.setItems(FXCollections.observableArrayList("Básico", "Normal", "Difícil", "Migthy Force"));
        tipoUsuarioBox.setItems(FXCollections.observableArrayList("Estudiante", "Docente", "Hobbie"));
        sexoBox.setItems(FXCollections.observableArrayList("Hombre", "Mujer"));
    }

    @FXML
    private void crearPersonaje() {
        // Si la validación no es exitosa, el método se detiene aquí.
        if (!validarDatos()) {
            return;
        }
        
        // Si todo es correcto, se procede a crear el personaje y cambiar de ventana.
        String nombre = nombreField.getText();
        int edad = (int) edadSlider.getValue();
        String nivelIngles = nivelInglesBox.getValue();
        String tipoUsuario = tipoUsuarioBox.getValue();
        String sexo = sexoBox.getValue();
        int avanceDiario = 10;
        
        this.personajeCreado = new Personaje(nombre, nivelIngles, edad, avanceDiario, tipoUsuario, sexo);
        
        System.out.println("Personaje creado con éxito: " + personajeCreado.getNombre());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Niveles/NivelesDisponibles.fxml"));
            Parent mapaRoot = loader.load();
            NivelesDisponibles controllerDelMapa = loader.getController();
            controllerDelMapa.initData(this.personajeCreado);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = new Scene(mapaRoot);
            stage.setScene(scene);
            stage.setTitle("Mapa de Aventura - " + personajeCreado.getNombre());
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la ventana del mapa de niveles:");
            e.printStackTrace();
        }
    }
    
    /**
     * Novedad: Valida cada campo y muestra una alerta específica para el primer error encontrado.
     * @return true si todos los campos son válidos, false si alguno falla.
     */
    private boolean validarDatos() {
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Nombre Requerido", "Por favor, escribe un nombre para tu personaje.");
            return false;
        }
        if (nivelInglesBox.getValue() == null) {
            mostrarAlerta("Dificultad Requerida", "Por favor, selecciona un nivel de inglés.");
            return false;
        }
        if (tipoUsuarioBox.getValue() == null) {
            mostrarAlerta("Ocupación Requerida", "Por favor, selecciona tu ocupación.");
            return false;
        }
        if (sexoBox.getValue() == null) {
            mostrarAlerta("Sexo Requerido", "Por favor, selecciona un sexo.");
            return false;
        }
        
        // Si todas las validaciones pasan, devuelve true.
        return true;
    }
    
    /**
     * Muestra una ventana de alerta simple.
     * @param titulo El título de la ventana de alerta.
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Campo Incompleto");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}