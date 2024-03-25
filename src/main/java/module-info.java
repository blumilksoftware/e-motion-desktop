module com.emotion.emotiondesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.emotion.emotiondesktop to javafx.fxml;
    exports com.emotion.emotiondesktop;
}