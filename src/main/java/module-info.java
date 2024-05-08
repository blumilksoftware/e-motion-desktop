module com.emotion.emotiondesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.json;
    requires com.sothawo.mapjfx;


    opens com.emotion.emotiondesktop to javafx.fxml;
    exports com.emotion.emotiondesktop;
}
