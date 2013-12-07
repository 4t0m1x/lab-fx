package labfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import labfx.controllers.page.Page;
import labfx.data.BufferedDAO;
import labfx.models.User;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 07.12.13
 * Time: 10:05
 */
public class ChartController extends Page {
    private static final Random RANDOM = new Random();

    @FXML
    private Button btnNextValue;

    @FXML
    private GridPane chartHost;

    @Override
    protected void onLoaded() {
        loadLineChart();
    }

    public static XYChart.Data<Number, Number> getNextPoint(int i) {
        return new XYChart.Data<Number, Number>(10 * i + 5, RANDOM.nextDouble() * 100);
    }

    public void loadLineChart() {
        btnNextValue.setDisable(false);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Random values");

        for (int j = 0; j < 2; j++) {
            XYChart.Series series = new XYChart.Series();

            for (int i = 0; i < 10; i++) {
                series.getData().add(getNextPoint(i));
            }

            lineChart.getData().add(series);
        }

        chartHost.getChildren().clear();
        chartHost.getChildren().add(lineChart);
    }

    public void loadPieChart() {
        btnNextValue.setDisable(true);

        ObservableList<User> users = new BufferedDAO<>(User.class).getEntries();

        Map<String, Integer> countriesMap = new HashMap<>();
        for (User user : users) {
            if (countriesMap.containsKey(user.getCountry())) {
                countriesMap.put(user.getCountry(), countriesMap.get(user.getCountry()) + 1);
            }
            else {
                countriesMap.put(user.getCountry(), 1);
            }
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> country : countriesMap.entrySet()) {
            if (country.getKey().equals("")) {
                chartData.add(new PieChart.Data("Unknown", country.getValue()));
            } else {
                chartData.add(new PieChart.Data(country.getKey(), country.getValue()));
            }
        }

        final PieChart pieChart = new PieChart();
        pieChart.setTitle("Users countries");
        pieChart.setData(chartData);

        chartHost.getChildren().clear();
        chartHost.getChildren().add(pieChart);
    }

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    public void loadBarChart() {
        btnNextValue.setDisable(true);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Country Summary");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(austria, 25601.34));
        series1.getData().add(new XYChart.Data(brazil, 20148.82));
        series1.getData().add(new XYChart.Data(france, 10000));
        series1.getData().add(new XYChart.Data(italy, 35407.15));
        series1.getData().add(new XYChart.Data(usa, 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(austria, 57401.85));
        series2.getData().add(new XYChart.Data(brazil, 41941.19));
        series2.getData().add(new XYChart.Data(france, 45263.37));
        series2.getData().add(new XYChart.Data(italy, 117320.16));
        series2.getData().add(new XYChart.Data(usa, 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(austria, 45000.65));
        series3.getData().add(new XYChart.Data(brazil, 44835.76));
        series3.getData().add(new XYChart.Data(france, 18722.18));
        series3.getData().add(new XYChart.Data(italy, 17557.31));
        series3.getData().add(new XYChart.Data(usa, 92633.68));

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("2020");
        series4.getData().add(new XYChart.Data(austria, 65000.65));
        series4.getData().add(new XYChart.Data(brazil, 48835.76));
        series4.getData().add(new XYChart.Data(france, 23722.18));
        series4.getData().add(new XYChart.Data(italy, 25557.31));
        series4.getData().add(new XYChart.Data(usa, 140633.68));

        bc.getData().addAll(series1, series2, series3, series4);
        chartHost.getChildren().clear();
        chartHost.getChildren().add(bc);
    }

    public void nextValue() {
        if (chartHost.getChildren().size() > 0 && chartHost.getChildren().get(0) instanceof LineChart) {
            LineChart<Number, Number> lineChart = (LineChart<Number, Number>)chartHost.getChildren().get(0);
            for (XYChart.Series<Number, Number> series : lineChart.getData()) {
                series.getData().add(getNextPoint(series.getData().size() + 1));
            }
        }
    }
}
