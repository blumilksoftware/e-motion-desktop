module com.emotion.emotiondesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.json;


    opens com.emotion.emotiondesktop to javafx.fxml;
    exports com.emotion.emotiondesktop;
}
