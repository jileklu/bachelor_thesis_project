package com.jileklu2.bakalarska_prace_app.gui.dateIntervalHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.dateSelection.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.strings.BlankStringException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
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

import java.io.IOException;
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
    private MainContext mainContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePickersInit();
        timeChoiceBoxesInit();
        intervalChoiceBoxInit();
    }

    private void datePickersInit() {
        fromDatePicker.setShowWeekNumbers(false);
        toDatePicker.setShowWeekNumbers(false);
        fromDatePickerLimitation();
        toDatePickerLimitation();
    }

    private void fromDatePickerLimitation() {
        final Callback<DatePicker, DateCell> limits = new Callback<>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now().plusYears(2))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #990000;");
                        } else if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #990000;");
                        }
                    }
                };
            }
        };
        fromDatePicker.setDayCellFactory(limits);
    }

    private void toDatePickerLimitation() {
        final Callback<DatePicker, DateCell> limits = new Callback<>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now().plusYears(2))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #990000;");
                        } else if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #990000;");
                        } else if (item.isBefore(fromDatePicker.getValue() == null ?
                            LocalDate.now().plusDays(1) :
                            fromDatePicker.getValue().plusDays(1))
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #990000;");
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

    @Override
    public void setRoutesContext(RoutesContext routesContext) {
        if(routesContext == null)
            throw new NullPointerException("Arguments can't be null");
        this.routesContext = routesContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        if(routeInfoPanelContext == null)
            throw new NullPointerException("Arguments can't be null");
        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        if(mapViewContext == null)
            throw new NullPointerException("Arguments can't be null");
        this.mapViewContext = mapViewContext;
    }

    @Override
    public void setMainContext(MainContext mainContext) {
        if(mainContext == null)
            throw new NullPointerException();
        this.mainContext = mainContext;
    }

    @FXML
    private void okButtonAction() throws IdenticalCoordinatesException, DistanceOutOfBoundsException,
    CoordinatesOutOfBoundsException, EmptyDestinationsListException, DurationOutOfBoundsException,
    BlankScriptNameStringException, IOException {
        try {
            checkValues();
        } catch (IllegalDateIntervalException e) {
            mainContext.createErrorWindow("Date interval is not valid.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (IntervalNotSelectedException e) {
            mainContext.createErrorWindow("Time interval is not selected.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (DateNotSelectedException e) {
            mainContext.createErrorWindow("Date is not selected.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (TimeNotSelectedException e) {
            mainContext.createErrorWindow("Time is not selected.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        int interval = intervalToMins(intervalComboBox.getValue());
        LocalTime fromTime = LocalTime.parse(fromTimeComboBox.getValue());
        LocalTime toTime = LocalTime.parse(toTimeComboBox.getValue());

        try {
            checkTimeIntervalValidity(fromTime, toTime);
        } catch (IllegalTimeIntervalException e) {
            mainContext.createErrorWindow("Time interval is not valid.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        HashSet<DayOfWeek> selectedDays = getSelectedDays();

        try {
            checkSelectedDaysValidity(selectedDays);
        } catch (DayNotSelectedException e) {
            mainContext.createErrorWindow("At least one day has to be selected.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        List<LocalDate> selectedDates = DateTimeHandler.getDatesBetween(fromDatePicker.getValue(),
                                                                        toDatePicker.getValue());

        selectedDates = DateTimeHandler.filterDatesByDays(selectedDates, selectedDays);

        List<LocalTime> relevantTimes = DateTimeHandler.getTimesBetween(fromTime, toTime, interval);

        HashSet <LocalDateTime> selectedTimeStamps = DateTimeHandler.combineTimesDates(selectedDates, relevantTimes);

        try {
            checkTimeStamps(selectedTimeStamps);
        } catch (TooManyTimeStampsException e) {
            mainContext.createErrorWindow("Maximum collective number of time stamps is 60.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (IllegalDateException e) {
            mainContext.createErrorWindow("Time interval must start after current time.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        Route newRoute = null;
        try {
            newRoute = new Route(routesContext.getDefaultRoute());
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before setting an interval.");
            System.out.println("Route has to be created before setting an interval.");
            e.printStackTrace();
            return;
        }
        routesContext.setTimeStamps(selectedTimeStamps);
        try {
            routesContext.findRouteInfo(newRoute);
        }  catch (EmptyTimeStampsSetException e) {
            mainContext.createErrorWindow("At least one time stamp has to exist.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        routesContext.setDefaultRoute(newRoute);
        try {
            routeInfoPanelContext.showDefaultRouteInfo();
            mapViewContext.showDefaultRoute();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before setting an interval.");
            System.out.println("Route has to be created before showing.");
            e.printStackTrace();
            return;
        }

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    private void checkTimeStamps(HashSet<LocalDateTime> timeStamps) throws TooManyTimeStampsException,
    IllegalDateException {
        if(timeStamps.size() > 60)
            throw new TooManyTimeStampsException("Too many time stamps to process.");

        for (LocalDateTime timeStamp : timeStamps) {
            if(timeStamp.isBefore(LocalDateTime.now()))
                throw new IllegalDateException("Dates must be after current time.");
        }
    }

    private void checkSelectedDaysValidity(HashSet<DayOfWeek> selectedDays) throws DayNotSelectedException {
        if(selectedDays.size() == 0)
            throw new DayNotSelectedException("At least one day of a week must be selected.");
    }

    private void checkTimeIntervalValidity(LocalTime fromTime, LocalTime toTime) throws IllegalTimeIntervalException {
        if(toTime.isBefore(fromTime))
            throw new IllegalTimeIntervalException("Illegal time interval.");
    }

    private void checkValues() throws IllegalDateIntervalException, IntervalNotSelectedException,
    DateNotSelectedException, TimeNotSelectedException {
        if(fromDatePicker.getValue() == null || toDatePicker.getValue() == null )
            throw new DateNotSelectedException("Date was not selected.");

        if(intervalComboBox.getValue() == null)
            throw new IntervalNotSelectedException("Interval size was not selected.");

        if(toTimeComboBox.getValue() == null || fromTimeComboBox.getValue() == null)
            throw new TimeNotSelectedException("Time was not selected.");

        if(fromDatePicker.getValue().isAfter(toDatePicker.getValue()) ||
            fromDatePicker.getValue().isEqual(toDatePicker.getValue()) ) {
            throw new IllegalDateIntervalException("toDate < fromDate.");
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
                throw new IllegalStateException("Unknown interval");
        }
    }
}
