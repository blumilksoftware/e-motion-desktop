module com.emotion.emotiondesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.json;
    requires com.sothawo.mapjfx;


    opens com.emotion.emotiondesktop to javafx.fxml;
    exports com.emotion.emotiondesktop;
    exports com.emotion.emotiondesktop.Model;
    opens com.emotion.emotiondesktop.Model to javafx.fxml;
    exports com.emotion.emotiondesktop.Helper;
    opens com.emotion.emotiondesktop.Helper to javafx.fxml;
    exports com.emotion.emotiondesktop.Controller;
    opens com.emotion.emotiondesktop.Controller to javafx.fxml;
}
