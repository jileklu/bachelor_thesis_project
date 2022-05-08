package com.jileklu2.bakalarska_prace_app.gui.dateIntervalHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.handlers.DateTimeHandler;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.DayOfWeek.*;

public class DateIntervalWindowController implements DateIntervalWindowContext, Initializable {
    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ComboBox<String> fromTimeComboBox;

    @FXML
    private ComboBox<String> toTimeComboBox;

    @FXML
    private ComboBox<String> intervalComboBox;

    @FXML
    private CheckBox mondayCheckBox;

    @FXML
    private CheckBox tuesdayCheckBox;

    @FXML
    private CheckBox wednesdayCheckBox;

    @FXML
    private CheckBox thursdayCheckBox;

    @FXML
    private CheckBox fridayCheckBox;

    @FXML
    private CheckBox saturdayCheckBox;

    @FXML
    private CheckBox sundayCheckBox;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private RoutesContext routesContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    private MapViewContext mapViewContext;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePickersInit();
        timeChoiceBoxesInit();
        intervalChoiceBoxInit();
    }

    private void datePickersInit() {
        fromDatePickerLimitation();
        toDatePickerLimitation();
    }

    private void fromDatePickerLimitation() {
        final Callback<DatePicker, DateCell> limits = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now().plusYears(2))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        fromDatePicker.setDayCellFactory(limits);
    }

    private void toDatePickerLimitation() {
        final Callback<DatePicker, DateCell> limits = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now().plusYears(2))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if(item.isBefore(fromDatePicker.getValue() == null ?
                            LocalDate.now().plusDays(1) :
                            fromDatePicker.getValue().plusDays(1))
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        toDatePicker.setDayCellFactory(limits);
    }

    private void timeChoiceBoxesInit() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for(int hour = 0; hour < 24; hour++) {
            for(int minutes = 0; minutes < 60; minutes += 15) {
                LocalTime time = LocalTime.of(hour, minutes);
                fromTimeComboBox.getItems().add(timeFormatter.format(time));
                toTimeComboBox.getItems().add(timeFormatter.format(time));
            }
        }
    }

    private void intervalChoiceBoxInit() {
        for(int minutes = 15; minutes < 60; minutes += 15) {
            intervalComboBox.getItems().add(minutes + " min");
        }

        for(int hours = 1; hours < 3; hours ++) {
            intervalComboBox.getItems().add(hours + " h");
        }
    }

    @FXML
    private void okButtonAction() {
        try {
            checkValues();

            int interval = intervalToMins(intervalComboBox.getValue());
            LocalTime fromTime = LocalTime.parse(fromTimeComboBox.getValue());
            LocalTime toTime = LocalTime.parse(toTimeComboBox.getValue());

            checkTimeIntervalValidity(fromTime, toTime);

            HashSet<DayOfWeek> selectedDays = getSelectedDays();

            checkSelectedDaysValidity(selectedDays);

            List<LocalDate> selectedDates = DateTimeHandler.getDatesBetween(fromDatePicker.getValue(),
                                                                            toDatePicker.getValue());

            selectedDates = DateTimeHandler.filterDatesByDays(selectedDates, selectedDays);

            List<LocalTime> relevantTimes = DateTimeHandler.getTimesBetween(fromTime, toTime, interval);

            HashSet <LocalDateTime> selectedTimeStamps = DateTimeHandler.combineTimesDates(selectedDates, relevantTimes);

            checkTimeStamps(selectedTimeStamps);

            Route newRoute = new Route(routesContext.getDefaultRoute());
            routesContext.setTimeStamps(selectedTimeStamps);
            routesContext.findRouteInfo(newRoute);
            routesContext.setDefaultRoute(newRoute);
            routeInfoPanelContext.showDefaultRouteInfo();
            mapViewContext.showDefaultRoute();

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            //todo
            e.printStackTrace();
        }
    }

    private void checkTimeStamps(HashSet<LocalDateTime> timeStamps) throws IllegalStateException {
        if(timeStamps.size() > 60)
            throw new IllegalStateException("Too many time stamps to process.");

        for (LocalDateTime timeStamp : timeStamps) {
            if(timeStamp.isBefore(LocalDateTime.now()))
                throw new IllegalArgumentException("Dates must be after current time.");
        }
    }

    private void checkSelectedDaysValidity(HashSet<DayOfWeek> selectedDays) throws IllegalStateException {
        if(selectedDays.size() == 0)
            throw new IllegalStateException("At least one day of a week must be selected.");
    }

    private void checkTimeIntervalValidity(LocalTime fromTime, LocalTime toTime) throws IllegalStateException {
        if(toTime.isBefore(fromTime))
            throw new IllegalStateException("Illegal time interval.");
    }

    private void checkValues() throws IllegalStateException {
        if(fromDatePicker.getValue() == null || toDatePicker.getValue() == null )
            throw new IllegalStateException("Date was not selected.");

        if(intervalComboBox.getValue() == null)
            throw new IllegalStateException("Interval size was not selected.");

        if(toTimeComboBox.getValue() == null || fromTimeComboBox.getValue() == null)
            throw new IllegalStateException("Time was not selected.");

        if(fromDatePicker.getValue().isAfter(toDatePicker.getValue()) ||
            fromDatePicker.getValue().isEqual(toDatePicker.getValue()) ) {
            throw new IllegalStateException("toDate < fromDate.");
        }
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private HashSet <DayOfWeek> getSelectedDays() {
        HashSet <DayOfWeek> selectedDays = new HashSet<>();

        if(mondayCheckBox.isSelected())
            selectedDays.add(MONDAY);
        if(tuesdayCheckBox.isSelected())
            selectedDays.add(TUESDAY);
        if(wednesdayCheckBox.isSelected())
            selectedDays.add(WEDNESDAY);
        if(thursdayCheckBox.isSelected())
            selectedDays.add(THURSDAY);
        if(fridayCheckBox.isSelected())
            selectedDays.add(FRIDAY);
        if(saturdayCheckBox.isSelected())
            selectedDays.add(SATURDAY);
        if(sundayCheckBox.isSelected())
            selectedDays.add(SUNDAY);

        return selectedDays;
    }

    private int intervalToMins(String interval) throws IllegalArgumentException {
        switch (interval) {
            case "15 min":
                return 15;
            case "30 min":
                return 30;
            case "45min":
                return 45;
            case "1 h":
                return 60;
            case "2 h":
                return 120;
            default:
                throw new IllegalArgumentException("Unknown interval");
        }

    }

    @Override
    public void setRoutesContext(RoutesContext routesContext) {
        this.routesContext = routesContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        this.mapViewContext = mapViewContext;
    }
}
