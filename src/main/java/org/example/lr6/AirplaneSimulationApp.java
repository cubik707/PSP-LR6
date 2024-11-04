package org.example.lr6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirplaneSimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("airplane-simulation.fxml"));
        Parent root = loader.load();

        AirplaneSimulationController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Airplane Simulation");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}