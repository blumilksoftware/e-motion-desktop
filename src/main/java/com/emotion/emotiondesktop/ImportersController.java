package com.emotion.emotiondesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

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
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "GET", null);

            JSONObject jsonResponse = new JSONObject(httpResponse.getResponseBody());
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

            tableView.setItems(importersData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runImporters() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/run-importers";
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "POST", null);

            int responseCode = httpResponse.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Importers run successfully");
                alert.showAndWait();
            }

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
            if (String.valueOf(importer.getId().getValue()).contains(search) ||
                    importer.getWho().getValue().contains(search) ||
                    importer.getStatus().getValue().contains(search) ||
                    importer.getProviderName().getValue().contains(search) ||
                    String.valueOf(importer.getCode().getValue()).contains(search) ||
                    importer.getWhen().getValue().contains(search)) {
                filteredImporters.add(importer);
            }
        }
        tableView.setItems(filteredImporters);
    }

    public void showCitiesCrudView() throws IOException {
        EmotionApplication.showCitiesCrudView();
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
