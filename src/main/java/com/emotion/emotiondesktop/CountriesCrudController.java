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

            connection.disconnect();
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
        if (longitude.isEmpty() || latitude.isEmpty() || iso.isEmpty() || name.isEmpty()) {
            saveInfo.setText("Please fill in all fields");
            return;
        } else if (!longitude.matches("[-+]?[0-9]*\\.?[0-9]+") || !latitude.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            saveInfo.setText("Longitude and latitude must be numbers");
            return;
        } else if (iso.length() != 2) {
            saveInfo.setText("ISO code must be 2 characters long");
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries";
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"name\": \"" + name +  "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"iso\": \"" + iso + "\"}";
            connection.getOutputStream().write(jsonInputString.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                saveInfo.setText("Country saved");
                refresh();
            } else {
                saveInfo.setText("Error while saving country");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            saveInfo.setText("Error while saving country");
        }
    }

    public void selectCountry() {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            saveInfo.setText("Please select a country to edit");
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
            return;
        }

        String name = editNameField.getText();
        String longitude = editLongitudeField.getText();
        String latitude = editLatitudeField.getText();
        String iso = editIsoField.getText();
        if (longitude.isEmpty() || latitude.isEmpty() || iso.isEmpty() || name.isEmpty()) {
            editInfo.setText("Please fill in all fields");
            return;
        } else if (!longitude.matches("[-+]?[0-9]*\\.?[0-9]+") || !latitude.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            editInfo.setText("Longitude and latitude must be numbers");
            return;
        } else if (iso.length() != 2) {
            saveInfo.setText("ISO code must be 2 characters long");
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries/" + selectedCountry.getId().get();
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"name\": \"" + name +  "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"iso\": \"" + iso + "\"}";
            connection.getOutputStream().write(jsonInputString.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Country edited");
                refresh();
            } else {
                editInfo.setText("Error while editing country");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while editing country");
        }
    }

    public void deleteCountry() {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            editInfo.setText("Please select a country to delete");
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/countries/" + selectedCountry.getId().get();
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("Country deleted");
                refresh();
            } else {
                editInfo.setText("Error while deleting country");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while deleting country");
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
            if (country.getName().getValue().contains(search)) {
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
        EmotionApplication.showOpinionsCrudView();
    }

    public void showUsersCrudView() throws IOException {
        EmotionApplication.showUsersCrudView();
    }

    public void showImportersView() throws IOException {
        EmotionApplication.showImportersView();
    }

    public void showMapView() throws IOException {
        EmotionApplication.showMapView();
    }
}
