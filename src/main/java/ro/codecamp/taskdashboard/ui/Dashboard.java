package ro.codecamp.taskdashboard.ui;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ro.codecamp.taskdashboard.util.ImprovedListChangeListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard extends AnchorPane implements Initializable {
    private final MouseOverEventHandler MOUSE_OVER_EVENT_HANDLER = new MouseOverEventHandler();
    private final MouseClickedEventHandler MOUSE_CLICKED_HANDLER = new MouseClickedEventHandler(this);
    @FXML
    private StackPane stackPane;
    @FXML
    private Text title;
    @FXML
    private Button closeButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TilePane itemsPane;
    private ObservableList<WorkItemModel> model;
    private RotateTransition rotateTransition;

    public Dashboard() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getClass().getSimpleName() + ".fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to instantiate Dashboard UI element", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        rotateTransition = RotateTransitionBuilder.create()
                .node(refreshButton)
                .cycleCount(RotateTransition.INDEFINITE)
                .byAngle(360)
                .duration(Duration.millis(200))
                .build();
    }

    public void setModel(ObservableList<WorkItemModel> model, EventHandler<ActionEvent> onRefresh) {
        this.model = model;
        refreshButton.setOnAction(onRefresh);
        model.addListener(new ImprovedListChangeListener<WorkItemModel>() {
            @Override
            public void onRemoved(WorkItemModel removed) {
                itemsPane.getChildren().clear();
            }

            @Override
            public void onAdded(WorkItemModel added) {
                AsyncWorkItemUIFactory asyncFactory = new AsyncWorkItemUIFactory(added);
                asyncFactory.valueProperty().addListener(new ChangeListener<WorkItemUI>() {
                    @Override
                    public void changed(ObservableValue<? extends WorkItemUI> observableValue,
                                        WorkItemUI oldItem, WorkItemUI newItem) {
                        itemsPane.getChildren().add(newItem);
                    }
                });
                asyncFactory.start();
            }
        });
    }

    public void busyFetchingData() {
        title.setText("Busy fetching data");
        rotateTransition.play();
    }

    public void done() {
        title.setText("My Tasks");
        rotateTransition.stop();
        refreshButton.setRotate(0);
    }

    private static class MouseOverEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            final Node source = (Node) mouseEvent.getSource();
            source.setCache(true);
            ScaleTransitionBuilder.create()
                    .node(source)
                    .autoReverse(true)
                    .duration(Duration.millis(200))
                    .cycleCount(0)
                    .fromY(0.95)
                    .toY(1)
                    .interpolator(Interpolator.EASE_OUT)
                    .onFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            source.setCache(false);
                        }
                    })
                    .build()
                    .play();
        }
    }

    private class MouseClickedEventHandler implements EventHandler<MouseEvent> {
        private final Dashboard dashboard;

        private MouseClickedEventHandler(Dashboard dashboard) {
            this.dashboard = dashboard;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            final Node source = (Node) mouseEvent.getSource();
            final int index = dashboard.itemsPane.getChildren().indexOf(source);
            dashboard.stackPane.getChildren().add(source);
            dashboard.itemsPane.setEffect(new GaussianBlur(5));
            dashboard.itemsPane.setDisable(true);
            source.setOnMouseEntered(null);
            refreshButton.setDisable(true);
            final EventHandler<MouseEvent> baseEventHandler = this;
            source.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dashboard.itemsPane.getChildren().add(index, source);
                    dashboard.itemsPane.setDisable(false);
                    dashboard.itemsPane.setEffect(null);
                    source.setScaleX(1);
                    source.setScaleY(1);
                    source.setOnMouseEntered(MOUSE_OVER_EVENT_HANDLER);
                    source.setOnMouseClicked(baseEventHandler);
                    refreshButton.setDisable(false);
                }
            });
            source.setCache(true);
            ParallelTransitionBuilder.create()
                    .children(
                            ScaleTransitionBuilder.create()
                                    .fromX(0)
                                    .fromY(0)
                                    .toX(3)
                                    .toY(3)
                                    .duration(Duration.millis(400))
                                    .node(source)
                                    .interpolator(Interpolator.EASE_BOTH)
                                    .build(),
                            FadeTransitionBuilder.create()
                                    .node(source)
                                    .fromValue(0.1)
                                    .toValue(1.0)
                                    .duration(Duration.millis(1000))
                                    .interpolator(Interpolator.EASE_OUT)
                                    .build()
                    )
                    .onFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            source.setCache(false);
                        }
                    })
                    .build()
                    .play();
        }
    }

    private class AsyncWorkItemUIFactory extends Service<WorkItemUI> {
        private WorkItemModel added;

        public AsyncWorkItemUIFactory(WorkItemModel added) {
            this.added = added;
        }

        @Override
        protected Task<WorkItemUI> createTask() {
            return new Task<WorkItemUI>() {
                @Override
                protected WorkItemUI call() throws Exception {
                    WorkItemUI workItemUI = new WorkItemUI();
                    workItemUI.setOnMouseEntered(MOUSE_OVER_EVENT_HANDLER);
                    workItemUI.setOnMouseClicked(MOUSE_CLICKED_HANDLER);
                    workItemUI.renderModel(added);
                    workItemUI.setOpacity(0);
                    return workItemUI;
                }
            };
        }

    }
}
