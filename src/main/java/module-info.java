module com.emotion.emotiondesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.emotion.emotiondesktop to javafx.fxml;
    exports com.emotion.emotiondesktop;
}
