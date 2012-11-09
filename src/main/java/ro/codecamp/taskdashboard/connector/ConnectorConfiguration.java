package ro.codecamp.taskdashboard.connector;

import java.util.Map;
import java.util.Properties;

public interface ConnectorConfiguration {
    WorkItemService buildWorkItemService(Properties configurationOptions);
}
