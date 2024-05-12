package com.emotion.emotiondesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class EmotionApplication extends Application {

    @Getter
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        showLoginView();
    }

    private static void initializeStage(String title, String fxmlResource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource(fxmlResource));
        Parent root = fxmlLoader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1366, 768));
        stage.show();
    }

    public static void showCitiesCrudView() throws IOException {
        initializeStage("E-motions Cities CRUD", "cities-crud-view.fxml");
    }

    public static void showCountriesCrudView() throws IOException {
        initializeStage("E-motions Countries CRUD", "countries-crud-view.fxml");
    }

    public static void showOpinionsCrudView() throws IOException {
        initializeStage("E-motions Opinions CRUD", "opinions-crud-view.fxml");
    }

    public static void showProvidersCrudView() throws IOException {
        initializeStage("E-motions Providers CRUD", "providers-crud-view.fxml");
    }

    public static void showUsersCrudView() throws IOException {
        initializeStage("E-motions Users CRUD", "users-crud-view.fxml");
    }

    public static void showImportersView() throws IOException {
        initializeStage("E-motions Importers", "importers-view.fxml");
    }

    public static void showLoginView() throws IOException {
        initializeStage("Login to E-Motion Desktop App", "login-view.fxml");
    }

    public static void showMapView() throws IOException {
        initializeStage("E-motions Map", "map-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}