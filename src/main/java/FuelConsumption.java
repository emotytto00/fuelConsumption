import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FuelConsumption extends Application {

    private ResourceBundle bundle;
    private TextField distanceField;
    private TextField fuelField;
    private Label resultLabel;
    private ComboBox<String> languageComboBox;
    private TableView<ConsumptionRecord> tableView;
    private ObservableList<ConsumptionRecord> recordData;
    private GridPane grid;

    private Label distanceLabel;
    private Label fuelLabel;
    private Button calculateButton;

    @Override
    public void start(Stage primaryStage) {
        loadResourceBundle("en");

        primaryStage.setTitle("Johannes Liikanen :)");

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        distanceField = new TextField();
        fuelField = new TextField();
        resultLabel = new Label();
        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "French", "Japanese", "Persian");
        languageComboBox.setValue("English");

        distanceLabel = new Label(bundle.getString("distanceLabel"));
        fuelLabel = new Label(bundle.getString("fuelLabel"));
        calculateButton = new Button(bundle.getString("calculateButton"));

        tableView = new TableView<>();
        setupTableView();

        recordData = FXCollections.observableArrayList();

        grid.add(distanceLabel, 0, 0);
        grid.add(distanceField, 1, 0);
        grid.add(fuelLabel, 0, 1);
        grid.add(fuelField, 1, 1);
        grid.add(languageComboBox, 1, 2);
        grid.add(calculateButton, 0, 3);
        grid.add(resultLabel, 1, 3);

        loadRecordsFromDatabase();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(grid, tableView);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        languageComboBox.setOnAction(e -> changeLanguage());

        calculateButton.setOnAction(e -> calculateAndSaveConsumption());

        updateUI();
    }

    private void loadResourceBundle(String languageCode) {
        Locale locale;
        switch (languageCode) {
            case "fr":
                locale = new Locale("fr", "FR");
                break;
            case "ja":
                locale = new Locale("ja", "JP");
                break;
            case "fa":
                locale = new Locale("fa", "IR");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    private void changeLanguage() {
        String selectedLanguage = languageComboBox.getValue();
        switch (selectedLanguage) {
            case "French":
                loadResourceBundle("fr");
                break;
            case "Japanese":
                loadResourceBundle("ja");
                break;
            case "Persian":
                loadResourceBundle("fa");
                break;
            default:
                loadResourceBundle("en");
                break;
        }
        updateUI();
    }

    private void updateUI() {
        distanceLabel.setText(bundle.getString("distanceLabel"));
        fuelLabel.setText(bundle.getString("fuelLabel"));
        calculateButton.setText(bundle.getString("calculateButton"));

        resultLabel.setText("");

        setupTableView();
    }

    private void calculateAndSaveConsumption() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            double fuel = Double.parseDouble(fuelField.getText());
            double consumption = (fuel / distance) * 100;

            resultLabel.setText(String.format(bundle.getString("fuelConsumption"), consumption));

            ConsumptionRecord record = new ConsumptionRecord(distance, fuel, consumption, languageComboBox.getValue());
            ConsumptionDAO dao = new ConsumptionDAO();
            dao.insert(record);

            recordData.add(record);
            tableView.setItems(recordData);
            tableView.refresh();

        } catch (NumberFormatException ex) {
            resultLabel.setText(bundle.getString("invalidInput"));
        }
    }


    private void loadRecordsFromDatabase() {
        ConsumptionDAO dao = new ConsumptionDAO();
        List<ConsumptionRecord> records = dao.getAllRecords();
        recordData.addAll(records);
    }

    private void setupTableView() {
        tableView.getColumns().clear();

        TableColumn<ConsumptionRecord, Double> distanceColumn = new TableColumn<>(bundle.getString("distanceLabel"));
        distanceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("distance"));

        TableColumn<ConsumptionRecord, Double> fuelColumn = new TableColumn<>(bundle.getString("fuelLabel"));
        fuelColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fuel"));

        TableColumn<ConsumptionRecord, Double> consumptionColumn = new TableColumn<>(bundle.getString("resultLabel"));
        consumptionColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("consumption"));

        TableColumn<ConsumptionRecord, String> languageColumn = new TableColumn<>(bundle.getString("languageLabel"));
        languageColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("language"));

        tableView.getColumns().addAll(distanceColumn, fuelColumn, consumptionColumn, languageColumn);
        tableView.setItems(recordData);
    }
}
