package ro.codecamp.taskdashboard.connector.stub;

import ro.codecamp.taskdashboard.connector.ConnectorConfiguration;
import ro.codecamp.taskdashboard.connector.WorkItemService;

import java.util.Properties;

public class StubConnectorConfiguration implements ConnectorConfiguration {
    @Override
    public WorkItemService buildWorkItemService(Properties configurationOptions) {
        return new StubWorkItemService();
    }
}
