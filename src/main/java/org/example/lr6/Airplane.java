package org.example.lr6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Airplane implements Runnable {
    private final ImageView airplaneImageView;
    private final Pane airplanePane;
    private final int id;
    private int x = 0;
    private int y;
    private final int speed;
    private int distance = 0;
    private boolean running = true;
    private final Thread thread;

    public Airplane(Pane pane, int id, List<Airplane> airplanes, Stage stage) {
        this.airplanePane = pane;
        this.id = id;
        this.speed = new Random().nextInt(5) + 1;

        Image airplaneImage = new Image(getClass().getResource("/org/example/lr6/images/airplane.png").toExternalForm());
        this.airplaneImageView = new ImageView(airplaneImage);
        airplaneImageView.setFitWidth(150);
        airplaneImageView.setFitHeight(150);

        do {
            this.y = new Random().nextInt((int) (stage.getHeight() - airplaneImageView.getFitHeight()));
        } while (isOverlapping(airplanes) || !isWithinBounds(stage));

        airplaneImageView.setLayoutX(x);
        airplaneImageView.setLayoutY(y);
        pane.getChildren().add(airplaneImageView);

        thread = new Thread(this);
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            move();
            try {
                Thread.sleep(50); // Скорость обновления
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void move() {
        x += speed;
        distance += speed;

        if (x > airplanePane.getWidth()) {
            x = 0;
            y = new Random().nextInt((int) (airplanePane.getHeight() - airplaneImageView.getFitHeight()));
        }

        // Обновляем позицию самолета на JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            airplaneImageView.setLayoutX(x);
            airplaneImageView.setLayoutY(y);
        });
    }

    public void showDistance() {
        javafx.application.Platform.runLater(() -> {
            airplanePane.getChildren().remove(airplaneImageView);
            Text distanceText = new Text("Самолет " + id + ": Дистанция = " + distance + " px");
            distanceText.setX(10);
            distanceText.setY(y + 20);
            airplanePane.getChildren().add(distanceText);
        });
    }

    private boolean isOverlapping(List<Airplane> airplanes) {
        for (Airplane other : airplanes) {
            if (Math.abs(this.y - other.y) < 30) {
                return true;
            }
        }
        return false;
    }

    private boolean isWithinBounds(Stage stage) {
        return this.y >= 0 && (this.y + airplaneImageView.getFitHeight() <= stage.getHeight());
    }
}

