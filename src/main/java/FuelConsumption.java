import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class FuelConsumption extends Application {

    private ResourceBundle bundle;

    @Override
    public void start(Stage stage) {
        bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        stage.setTitle("Johannes Liikanen :)");

        Label distanceLabel = new Label(bundle.getString("distance.label"));
        Label fuelLabel = new Label(bundle.getString("fuel.label"));
        TextField distanceInput = new TextField();
        TextField fuelInput = new TextField();
        Button calculateButton = new Button(bundle.getString("calculate.button"));
        Label resultLabel = new Label();
        ComboBox<String> languageBox = new ComboBox<>();

        languageBox.getItems().addAll("English", "French", "Japanese", "Persian");
        languageBox.setValue("English");

        languageBox.setOnAction(e -> {
            String selectedLanguage = languageBox.getValue();
            switch (selectedLanguage) {
                case "French":
                    bundle = ResourceBundle.getBundle("messages", Locale.FRENCH);
                    break;
                case "Japanese":
                    bundle = ResourceBundle.getBundle("messages", Locale.JAPANESE);
                    break;
                case "Persian":
                    bundle = ResourceBundle.getBundle("messages", new Locale("fa"));
                    break;
                default:
                    bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
            }
            updateLabels(distanceLabel, fuelLabel, calculateButton, resultLabel);
        });

        calculateButton.setOnAction(e -> {
            try {
                double distance = Double.parseDouble(distanceInput.getText());
                double fuel = Double.parseDouble(fuelInput.getText());
                double consumption = (fuel / distance) * 100;
                resultLabel.setText(String.format(bundle.getString("result.label"), String.format("%.2f", consumption)));
            } catch (NumberFormatException ex) {
                resultLabel.setText(bundle.getString("invalid.input"));
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(languageBox, 0, 0);
        grid.add(distanceLabel, 0, 1);
        grid.add(distanceInput, 1, 1);
        grid.add(fuelLabel, 0, 2);
        grid.add(fuelInput, 1, 2);
        grid.add(calculateButton, 0, 3);
        grid.add(resultLabel, 1, 3);

        Scene scene = new Scene(grid, 400, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void updateLabels(Label distanceLabel, Label fuelLabel, Button calculateButton, Label resultLabel) {
        distanceLabel.setText(bundle.getString("distance.label"));
        fuelLabel.setText(bundle.getString("fuel.label"));
        calculateButton.setText(bundle.getString("calculate.button"));
        resultLabel.setText("");
    }
}
