package com.emotion.emotiondesktop.Controller;

import com.emotion.emotiondesktop.EmotionApplication;
import com.emotion.emotiondesktop.Helper.HttpRequest;
import com.emotion.emotiondesktop.Helper.HttpResponse;
import com.emotion.emotiondesktop.Model.Provider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ProvidersCrudController {

    @FXML
    private TextField saveNameField;
    @FXML
    private TextField editNameField;
    @FXML
    private TextField saveFileField;
    @FXML
    private TextField editFileField;
    @FXML
    private TextField saveColorField;
    @FXML
    private TextField editColorField;
    @FXML
    private TextField saveWebUrlField;
    @FXML
    private TextField saveAndroidUrlField;
    @FXML
    private TextField saveIosUrlField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Provider> tableView;
    @FXML
    private TableColumn<Provider, String> nameColumn;
    @FXML
    private TableColumn<Provider, String> urlColumn;
    @FXML
    private TableColumn<Provider, String> androidUrlColumn;
    @FXML
    private TableColumn<Provider, String> iosUrlColumn;
    @FXML
    private TableColumn<Provider, String> fileColumn;
    @FXML
    private TableColumn<Provider, String> colorColumn;
    private final ObservableList<Provider> providersData = FXCollections.observableArrayList();
    @FXML
    private Label saveInfo;
    @FXML
    private Label editInfo;

    public void initialize() {
        fetchProviderRecords();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        urlColumn.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        androidUrlColumn.setCellValueFactory(cellData -> cellData.getValue().getAndroidUrl());
        iosUrlColumn.setCellValueFactory(cellData -> cellData.getValue().getIosUrl());
        fileColumn.setCellValueFactory(cellData -> cellData.getValue().getFile());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().getColor());
        tableView.setItems(providersData);
    }

    public void refresh() {
        providersData.clear();
        fetchProviderRecords();
    }

    private void fetchProviderRecords() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/providers";
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "GET", null);

            JSONObject jsonResponse = new JSONObject(httpResponse.getResponseBody());
            JSONArray providersArray = jsonResponse.getJSONArray("providers");

            for (int i = 0; i < providersArray.length(); i++) {
                JSONObject providerObject = providersArray.getJSONObject(i);
                String name = providerObject.getString("name");
                String webUrl = providerObject.optString("url", null);
                String androidUrl = providerObject.optString("android_url", null);
                String iosUrl = providerObject.optString("ios_url", null);
                String file = providerObject.optString("file", null);
                String color = providerObject.getString("color");

                Provider provider = new Provider(name, webUrl, androidUrl, iosUrl, file, color);
                providersData.add(provider);
            }

            tableView.setItems(providersData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProvider() {
        String name = saveNameField.getText();
        String file = saveFileField.getText();
        String color = saveColorField.getText();
        String webUrl = saveWebUrlField.getText();
        String androidUrl = saveAndroidUrlField.getText();
        String iosUrl = saveIosUrlField.getText();
        if (color.isEmpty() || name.isEmpty() || file.isEmpty()) {
            saveInfo.setText("Please fill in all fields");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (color.length() != 7) {
            saveInfo.setText("Color must be in #000000 format");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/providers";
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "POST", "{\"name\": \"" + name + "\", \"color\": \"" + color + "\", \"file\": \"" + file + "\", \"url\": \"" + webUrl + "\", \"android_url\": \"" + androidUrl + "\", \"ios_url\": \"" + iosUrl + "\"}");

            int responseCode = httpResponse.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                saveInfo.setText("Provider saved");
                saveInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                saveInfo.setText("Error while saving provider");
                saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            saveInfo.setText("Error while saving provider");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void selectProvider() {
        Provider selectedProvider = tableView.getSelectionModel().getSelectedItem();
        if (selectedProvider == null) {
            saveInfo.setText("Please select a provider to edit");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        editNameField.setText(selectedProvider.getName().getValue());
        editFileField.setText(selectedProvider.getFile().getValue());
        editColorField.setText(String.valueOf(selectedProvider.getColor().get()));
    }

    public void editProvider() {
        Provider selectedProvider = tableView.getSelectionModel().getSelectedItem();
        if (selectedProvider == null) {
            editInfo.setText("Please select a provider to edit");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        String name = editNameField.getText();
        String file = editFileField.getText();
        String color = editColorField.getText();
        if (color.isEmpty() || name.isEmpty()) {
            editInfo.setText("Please fill in all fields");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (color.length() != 7) {
            editInfo.setText("Color must be in #000000 format");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/providers/" + selectedProvider.getName();
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "PUT", "{\"name\": \"" + name + "\", \"color\": \"" + color + "\", \"file\": \"" + file + "\"}");

            int responseCode = httpResponse.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Provider edited");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while editing provider");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while editing provider");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void deleteProvider() {
        Provider selectedProvider = tableView.getSelectionModel().getSelectedItem();
        if (selectedProvider == null) {
            editInfo.setText("Please select a provider to delete");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/providers/" + selectedProvider.getName();
            HttpResponse httpResponse = HttpRequest.sendHttpRequest(apiUrl, "DELETE", null);

            int responseCode = httpResponse.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Provider deleted");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while deleting provider");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while deleting provider");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void searchInTable() {
        String search = searchField.getText();
        if (search.isEmpty()) {
            tableView.setItems(providersData);
            return;
        }
        ObservableList<Provider> filteredProviders = FXCollections.observableArrayList();
        for (Provider provider : providersData) {
            if (provider.getName().getValue().contains(search) || provider.getColor().getValue().contains(search)) {
                filteredProviders.add(provider);
            }
        }
        tableView.setItems(filteredProviders);
    }

    public void showCitiesCrudView() throws IOException {
        EmotionApplication.showCitiesCrudView();
    }

    public void showCountriesCrudView() throws IOException {
        EmotionApplication.showCountriesCrudView();
    }

    public void showOpinionsCrudView() throws IOException {

    }

    public void showUsersCrudView() throws IOException {

    }

    public void showImportersView() throws IOException {
        EmotionApplication.showImportersView();
    }

    public void showMapView() throws IOException {
        EmotionApplication.showMapView();
    }
}
