package ro.codecamp.taskdashboard.main;

import javafx.stage.Stage;

public class FXApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        TaskDashboardConfiguration config = new TaskDashboardConfiguration();
        DashboardPresenter dashboardPresenter = new DashboardPresenter(config.javaFXworkItemService(), stage);
        dashboardPresenter.showDashboard();
    }

    public static void main(String[] args) {
        launch(FXApplication.class);
    }
}
