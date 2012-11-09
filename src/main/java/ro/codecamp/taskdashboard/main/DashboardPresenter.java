package ro.codecamp.taskdashboard.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.codecamp.taskdashboard.service.JavaFXWorkItemService;
import ro.codecamp.taskdashboard.ui.Dashboard;
import ro.codecamp.taskdashboard.ui.WorkItemModel;

import java.util.List;

public class DashboardPresenter {
    private final JavaFXWorkItemService workItemService;
    private final Stage stage;
    private final ObservableList<WorkItemModel> workItems = FXCollections.observableArrayList();

    public DashboardPresenter(JavaFXWorkItemService workItemService, Stage stage) {
        this.workItemService = workItemService;
        this.stage = stage;
    }

    public void showDashboard() {
        final Dashboard dashboard = new Dashboard();
        dashboard.setModel(workItems, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                workItemService.restart();
            }
        });
        Scene scene = new Scene(dashboard);
        scene.getStylesheets().add(DashboardPresenter.class.getResource("stylesheet.css").toExternalForm());
        scene.setFill(null);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        workItemService.valueProperty().addListener(new ChangeListener<List<WorkItemModel>>() {
            @Override
            public void changed(ObservableValue<? extends List<WorkItemModel>> observableValue, List<WorkItemModel> oldValue,
                                List<WorkItemModel> newValue) {
                if (newValue != null) {
                    workItems.setAll(newValue);
                } else {
                    workItems.clear();
                }
            }
        });
        workItemService.runningProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    dashboard.busyFetchingData();
                } else {
                    dashboard.done();
                }
            }
        });
        workItemService.start();
    }
}
