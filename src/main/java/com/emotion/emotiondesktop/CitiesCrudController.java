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
import lombok.Data;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
public class CitiesCrudController {

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
    private TextField saveCountryIdField;
    @FXML
    private TextField editCountryIdField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<City> tableView;
    @FXML
    private TableColumn<City, Integer> idColumn;
    @FXML
    private TableColumn<City, String> nameColumn;
    @FXML
    private TableColumn<City, Double> longitudeColumn;
    @FXML
    private TableColumn<City, Double> latitudeColumn;
    @FXML
    private TableColumn<City, Integer> countryIdColumn;
    @Getter
    private final ObservableList<City> citiesData = FXCollections.observableArrayList();
    @FXML
    private Label saveInfo;
    @FXML
    private Label editInfo;

    public void initialize() {
        fetchCityRecords();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        longitudeColumn.setCellValueFactory(cellData -> cellData.getValue().getLongitude().asObject());
        latitudeColumn.setCellValueFactory(cellData -> cellData.getValue().getLatitude().asObject());
        countryIdColumn.setCellValueFactory(cellData -> cellData.getValue().getCountryId().asObject());
        tableView.setItems(citiesData);
    }

    public void refresh() {
        citiesData.clear();
        fetchCityRecords();
    }

    private void fetchCityRecords() {
        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/cities";
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
            JSONArray citiesArray = jsonResponse.getJSONArray("cities");

            for (int i = 0; i < citiesArray.length(); i++) {
                JSONObject cityObject = citiesArray.getJSONObject(i);
                int id = cityObject.getInt("id");
                String name = cityObject.getString("name");
                double longitude = cityObject.optDouble("longitude", 0);
                double latitude = cityObject.optDouble("latitude", 0);
                int countryId = cityObject.getJSONObject("country").getInt("id");

                City city = new City(id, name, longitude, latitude, countryId);
                citiesData.add(city);
            }

            connection.disconnect();
            tableView.setItems(citiesData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCity() {
        String name = saveNameField.getText();
        String longitude = saveLongitudeField.getText();
        String latitude = saveLatitudeField.getText();
        String countryId = saveCountryIdField.getText();
        if (longitude.isEmpty() || latitude.isEmpty() || countryId.isEmpty() || name.isEmpty()) {
            saveInfo.setText("Please fill in all fields");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (!longitude.matches("[-+]?[0-9]*\\.?[0-9]+") || !latitude.matches("[-+]?[0-9]*\\.?[0-9]+") || !countryId.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            saveInfo.setText("Longitude, latitude and country id must be numbers");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/cities";
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"name\": \"" + name + "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"country_id\": " + countryId + "}";
            connection.getOutputStream().write(jsonInputString.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                saveInfo.setText("City saved");
                saveInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                saveInfo.setText("Error while saving city");
                saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            saveInfo.setText("Error while saving city");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void selectCity() {
        City selectedCity = tableView.getSelectionModel().getSelectedItem();
        if (selectedCity == null) {
            saveInfo.setText("Please select a city to edit");
            saveInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        editIdField.setText(String.valueOf(selectedCity.getId().get()));
        editNameField.setText(selectedCity.getName().getValue());
        editLongitudeField.setText(String.valueOf(selectedCity.getLongitude().get()));
        editLatitudeField.setText(String.valueOf(selectedCity.getLatitude().get()));
        editCountryIdField.setText(String.valueOf(selectedCity.getCountryId().get()));
    }

    public void editCity() {
        City selectedCity = tableView.getSelectionModel().getSelectedItem();
        if (selectedCity == null) {
            editInfo.setText("Please select a city to edit");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        String name = editNameField.getText();
        String longitude = editLongitudeField.getText();
        String latitude = editLatitudeField.getText();
        String countryId = editCountryIdField.getText();
        if (longitude.isEmpty() || latitude.isEmpty() || countryId.isEmpty() || name.isEmpty()) {
            editInfo.setText("Please fill in all fields");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        } else if (!longitude.matches("[-+]?[0-9]*\\.?[0-9]+") || !latitude.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            editInfo.setText("Longitude and latitude must be numbers");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/cities/" + selectedCity.getId().get();
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"name\": \"" + name + "\", \"longitude\": " + longitude + ", \"latitude\": " + latitude + ", \"country_id\": " + countryId + "}";
            connection.getOutputStream().write(jsonInputString.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("City edited");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while editing city");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while editing city");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void deleteCity() {
        City selectedCity = tableView.getSelectionModel().getSelectedItem();
        if (selectedCity == null) {
            editInfo.setText("Please select a city to delete");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            String apiUrl = "https://dev.escooters.blumilk.pl/api/admin/cities/" + selectedCity.getId().get();
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + LoginController.getAccessToken());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                editInfo.setText("City deleted");
                editInfo.setTextFill(javafx.scene.paint.Color.GREEN);
                refresh();
            } else {
                editInfo.setText("Error while deleting city");
                editInfo.setTextFill(javafx.scene.paint.Color.RED);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            editInfo.setText("Error while deleting city");
            editInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    public void searchInTable() {
        String search = searchField.getText();
        if (search.isEmpty()) {
            tableView.setItems(citiesData);
            return;
        }
        ObservableList<City> filteredCities = FXCollections.observableArrayList();
        for (City city : citiesData) {
            if (String.valueOf(city.getId().getValue()).contains(search) ||
                    city.getName().getValue().contains(search) ||
                    String.valueOf(city.getLongitude().getValue()).contains(search) ||
                    String.valueOf(city.getLatitude().getValue()).contains(search) ||
                    String.valueOf(city.getCountryId().getValue()).contains(search)) {
                filteredCities.add(city);
            }
        }
        tableView.setItems(filteredCities);
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

    public void showImportersView() throws IOException {
        EmotionApplication.showImportersView();
    }

    public void showMapView() throws IOException {
        EmotionApplication.showMapView();
    }
}
