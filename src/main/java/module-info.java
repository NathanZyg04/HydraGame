module com.nathan.hydragame.hydragame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nathan.hydragame.hydragame to javafx.fxml;
    exports com.nathan.hydragame.hydragame;
}