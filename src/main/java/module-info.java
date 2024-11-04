module org.example.lr6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lr6 to javafx.fxml;
    exports org.example.lr6;
}