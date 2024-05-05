package com.emotion.emotiondesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmotionApplication extends Application {

    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        showLoginView();
    }

    public static void showCitiesCrudView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("cities-crud-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Cities CRUD");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showCountriesCrudView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("countries-crud-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Countries CRUD");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showOpinionsCrudView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("opinions-crud-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Opinions CRUD");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showProvidersCrudView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("providers-crud-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Providers CRUD");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showUsersCrudView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("users-crud-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Users CRUD");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showImportersView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("importers-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Importers");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showLoginView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Login to E-Motion Desktop App");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void showMapView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionApplication.class.getResource("map-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("E-motions Map");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
