package ro.codecamp.taskdashboard.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkItemUI extends AnchorPane implements Initializable {
    @FXML
    private Label issueTypeIcon;
    @FXML
    private WorkItemModel workItemModel;

    public WorkItemUI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getClass().getSimpleName() + ".fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to instantiate model", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void renderModel(WorkItemModel workItem) {
        workItemModel.bindTo(workItem);
        issueTypeIcon.getStyleClass().add(workItem.getIssueType().getValue().toLowerCase());
        getStyleClass().add(workItem.getPriority().getValue().toLowerCase());
    }

}
