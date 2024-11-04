package org.example.lr6;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Airplane {
    private final ImageView airplaneImageView;
    private final Timeline timeline;
    private int x = 0;
    private int y;
    private final int speed;
    private final int id;
    private int distance = 0;
    private final Pane airplanePane;

    public Airplane(Pane pane, int id, List<Airplane> airplanes, Stage stage) {
        this.airplanePane = pane;
        this.id = id;
        this.speed = new Random().nextInt(5) + 1;

        // Загрузка изображения самолета
        Image airplaneImage = new Image(getClass().getResource("/org/example/lr6/images/airplane.png").toExternalForm());
        this.airplaneImageView = new ImageView(airplaneImage);
        airplaneImageView.setFitWidth(150);  // Ширина изображения
        airplaneImageView.setFitHeight(150); // Высота изображения
        airplaneImageView.setLayoutX(x);
        airplaneImageView.setLayoutY(y);
        pane.getChildren().add(airplaneImageView);

        do {
            this.y = new Random().nextInt((int) (stage.getHeight() - airplaneImageView.getFitHeight()));
        } while (isOverlapping(airplanes) || !isWithinBounds(stage));

        // Настройка анимации
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> move()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    private void move() {
        x += speed;
        distance += speed;
        if (x > airplanePane.getWidth()) {
            x = 0;
            y = new Random().nextInt((int) (airplanePane.getHeight() - airplaneImageView.getFitHeight()));
        }
        airplaneImageView.setLayoutX(x);
        airplaneImageView.setLayoutY(y);
    }

    public void showDistance() {
        airplanePane.getChildren().remove(airplaneImageView);
        Text distanceText = new Text("Самолет " + id + ": Дистанция = " + distance + " px");
        distanceText.setX(10);
        distanceText.setY(y + 20);
        airplanePane.getChildren().add(distanceText);
    }

    private boolean isOverlapping(List<Airplane> airplanes) {
        for (Airplane other : airplanes) {
            // Проверяем, находится ли самолет на том же уровне Y, что и другой самолет
            if (Math.abs(this.y - other.y) < 30) { // Минимальное расстояние между самолетами
                return true; // Есть наложение
            }
        }
        return false; // Нет наложения
    }

    // метод для проверки, помещается ли самолет в области видимости
    private boolean isWithinBounds(Stage stage) {
        return this.y >= 0 && (this.y + airplaneImageView.getFitHeight() <= stage.getHeight());
    }
}
