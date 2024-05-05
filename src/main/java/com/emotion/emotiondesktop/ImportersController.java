package com.emotion.emotiondesktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ImportersController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Importer> tableView;
    @FXML
    private TableColumn<Importer, Integer> idColumn;
    @FXML
    private TableColumn<Importer, String> whoColumn;
    @FXML
    private TableColumn<Importer, String> statusColumn;
    @FXML
    private TableColumn<Importer, String> providerNameColumn;
    @FXML
    private TableColumn<Importer, Integer> codeColumn;
    @FXML
    private TableColumn<Importer, String> whenColumn;
    private final ObservableList<Importer> importersData = FXCollections.observableArrayList();

    public void initialize() {
        fetchImporterRecords();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        whoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        providerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProviderName());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCode().asObject());
        whenColumn.setCellValueFactory(cellData -> cellData.getValue().getWhen());
        tableView.setItems(importersData);
    }

    public void refresh() {
        importersData.clear();
        fetchImporterRecords();
    }

    private void fetchImporterRecords() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/importers";
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray importersArray = jsonResponse.getJSONArray("importInfo");

            for (int i = 0; i < importersArray.length(); i++) {
                JSONObject importerObject = importersArray.getJSONObject(i);
                int id = importerObject.getInt("id");
                String who = importerObject.getString("who_runs_it");
                String status = importerObject.getString("status");
                String when = importerObject.getString("created_at");

                for (int j = 0; j < importerObject.getJSONArray("import_info_details").length(); j++) {
                    JSONObject importInfoDetails = importerObject.getJSONArray("import_info_details").getJSONObject(j);
                        String providerName = importInfoDetails.getString("provider_name");
                        int code = importInfoDetails.getInt("code");
                        Importer importer = new Importer(id, who, status, providerName, code, when);
                        importersData.add(importer);
                }
            }

            connection.disconnect();
            tableView.setItems(importersData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runImporters() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/run-importers";
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Importers run successfully");
                alert.showAndWait();
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchInTable() {
        String search = searchField.getText();
        if (search.isEmpty()) {
            tableView.setItems(importersData);
            return;
        }
        ObservableList<Importer> filteredImporters = FXCollections.observableArrayList();
        for (Importer importer : importersData) {
            if (importer.getProviderName().getValue().contains(search)) {
                filteredImporters.add(importer);
            }
        }
        tableView.setItems(filteredImporters);
    }

    public void showCitiesCrudView() throws IOException {
        EmotionApplication.showCountriesCrudView();
    }

    public void showCountriesCrudView() throws IOException {
        EmotionApplication.showCountriesCrudView();
    }

    public void showProvidersCrudView() throws IOException {
        EmotionApplication.showProvidersCrudView();
    }

    public void showOpinionsCrudView() throws IOException {
        EmotionApplication.showOpinionsCrudView();
    }

    public void showUsersCrudView() throws IOException {
        EmotionApplication.showUsersCrudView();
    }

    public void showMapView() throws IOException {
        EmotionApplication.showMapView();
    }
}
