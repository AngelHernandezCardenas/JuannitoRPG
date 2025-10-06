/*To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.*/
package rpgingles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RPGIngles extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el archivo FXML que define la interfaz de la ventana de creación
        Parent root = FXMLLoader.load(getClass().getResource("creacion_personaje.fxml"));
        
        primaryStage.setTitle("RPG Inglés - Creación de Personaje");
        primaryStage.setScene(new Scene(root, 400, 500)); // Ancho y alto de la ventana
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}