package com.emotion.emotiondesktop.Controller;

import com.emotion.emotiondesktop.EmotionApplication;
import com.emotion.emotiondesktop.Helper.HttpRequest;
import com.emotion.emotiondesktop.Helper.HttpResponse;
import com.emotion.emotiondesktop.Helper.Validation;
import com.emotion.emotiondesktop.Model.Country;
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

public class CountriesCrudController {

    @FXML
    private TextField editIdField;
    @FXML
    private TextField saveNameField;
    @FXML
    private TextField editNameField;
    @FXML
    private TextField saveLongitudeField;
    @FXML
    private TextField editLongitudeField;
    @FXML
    private TextField saveLatitudeField;
    @FXML
    private TextField editLatitudeField;
    @FXML
    private TextField saveIsoField;
    @FXML
    private TextField editIsoField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Country> tableView;
    @FXML
    private TableColumn<Country, Integer> idColumn;
    @FXML
    private TableColumn<Country, String> nameColumn;
    @FXML
    private TableColumn<Country, Double> longitudeColumn;
    @FXML
    private TableColumn<Country, Double> latitudeColumn;
    @FXML
    private TableColumn<Country, String> isoColumn;
    private final ObservableList<Country> countriesData = FXCollections.observableArrayList();
    @FXML
    private Label saveInfo;
    @FXML
    private Label editInfo;

    public void initialize() {
        fetchCountryRecords();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        longitudeColumn.setCellValueFactory(cellData -> cellData.getValue().getLongitude().asObject());
        latitudeColumn.setCellValueFactory(cellData -> cellData.getValue().getLatitude().asObject());
        isoColumn.setCellValueFactory(cellData -> cellData.getValue().getIso());
        tableView.setItems(countriesData);
    }

    public void refresh() {
        countriesData.clear();
        fetchCountryRecords();
    }

    private void fetchCountryRecords() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries";
            HttpResponse response = HttpRequest.sendHttpRequest(apiUrl, "GET", null);

            JSONObject jsonResponse = new JSONObject(response.getResponseBody());
            JSONArray countriesArray = jsonResponse.getJSONArray("countries");

            for (int i = 0; i < countriesArray.length(); i++) {
                JSONObject countryObject = countriesArray.getJSONObject(i);
                int id = countryObject.getInt("id");
                String name = countryObject.getString("name");
                double longitude = countryObject.getDouble("longitude");
                double latitude = countryObject.getDouble("latitude");
                String iso = countryObject.getString("iso");

                Country country = new Country(id, name, longitude, latitude, iso);
                countriesData.add(country);
            }

            tableView.setItems(countriesData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCountry() {
        String name = saveNameField.getText();
        String longitude = saveLongitudeField.getText();
        String latitude = saveLatitudeField.getText();
        String iso = saveIsoField.getText();
        if (!Validation.isNotEmpty(name, longitude, latitude, iso)) {
            saveInfo.setText("Please fill in all fields");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (!Validation.isValidNumber(longitude) || !Validation.isValidNumber(latitude)){
            saveInfo.setText("Longitude and latitude must be numbers");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (iso.length() != 2) {
            saveInfo.setText("ISO code must be 2 characters long");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries";
            HttpResponse response = HttpRequest.sendHttpRequest(apiUrl, "POST", "{\"name\": \"" + name +  "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"iso\": \"" + iso + "\"}");

            int responseCode = response.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                saveInfo.setText("Country saved");
                saveInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                saveInfo.setText("Error while saving country");
                saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            saveInfo.setText("Error while saving country");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void selectCountry() {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            saveInfo.setText("Please select a country to edit");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        editIdField.setText(String.valueOf(selectedCountry.getId().get()));
        editNameField.setText(selectedCountry.getName().getValue());
        editLongitudeField.setText(String.valueOf(selectedCountry.getLongitude().get()));
        editLatitudeField.setText(String.valueOf(selectedCountry.getLatitude().get()));
        editIsoField.setText(selectedCountry.getIso().getValue());
    }

    public void editCountry() {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            editInfo.setText("Please select a country to edit");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        String name = editNameField.getText();
        String longitude = editLongitudeField.getText();
        String latitude = editLatitudeField.getText();
        String iso = editIsoField.getText();
        if (!Validation.isNotEmpty(name, longitude, latitude, iso)) {
            editInfo.setText("Please fill in all fields");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (!Validation.isValidNumber(longitude) || !Validation.isValidNumber(latitude)){
            editInfo.setText("Longitude and latitude must be numbers");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (iso.length() != 2) {
            saveInfo.setText("ISO code must be 2 characters long");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries/" + selectedCountry.getId().get();
            HttpResponse response = HttpRequest.sendHttpRequest(apiUrl, "PUT", "{\"name\": \"" + name +  "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"iso\": \"" + iso + "\"}");

            int responseCode = response.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Country edited");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while editing country");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while editing country");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void deleteCountry() {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            editInfo.setText("Please select a country to delete");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries/" + selectedCountry.getId().get();
            HttpResponse response = HttpRequest.sendHttpRequest(apiUrl, "DELETE", null);

            int responseCode = response.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Country deleted");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while deleting country");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while deleting country");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void searchInTable() {
        String search = searchField.getText();
        if (search.isEmpty()) {
            tableView.setItems(countriesData);
            return;
        }
        ObservableList<Country> filteredCountries = FXCollections.observableArrayList();
        for (Country country : countriesData) {
            if (String.valueOf(country.getId().getValue()).contains(search) ||
                    country.getName().getValue().contains(search) ||
                    String.valueOf(country.getLongitude().getValue()).contains(search) ||
                    String.valueOf(country.getLatitude().getValue()).contains(search) ||
                    String.valueOf(country.getIso().getValue()).contains(search)) {
                filteredCountries.add(country);
            }
        }
        tableView.setItems(filteredCountries);
    }

    public void showCitiesCrudView() throws IOException {
        EmotionApplication.showCitiesCrudView();
    }

    public void showProvidersCrudView() throws IOException {
        EmotionApplication.showProvidersCrudView();
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
