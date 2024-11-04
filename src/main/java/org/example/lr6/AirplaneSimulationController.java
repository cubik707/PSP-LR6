package org.example.lr6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AirplaneSimulationController {
    @FXML
    private Pane airplanePane;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private final List<Airplane> airplanes = new ArrayList<>();
    private boolean running = false;

    private Stage stage; // Ссылка на Stage

    // Метод для инициализации контроллера
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleStart() {
        if (!running) {
            running = true;
            airplanes.clear();
            airplanePane.getChildren().clear();

            // Создаем самолеты с разной скоростью
            for (int i = 0; i < 4; i++) {
                Airplane airplane = new Airplane(airplanePane, i + 1, airplanes, stage);
                airplanes.add(airplane);
                airplane.start();
            }
        }
    }

    @FXML
    private void handleStop() {
        running = false;
        for (Airplane airplane : airplanes) {
            airplane.stop();
            airplane.showDistance();
        }
    }
}
