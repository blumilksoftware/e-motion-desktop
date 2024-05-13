package com.emotion.emotiondesktop.Unit;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;


class CitiesCrudControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

}