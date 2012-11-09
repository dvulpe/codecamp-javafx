package ro.codecamp.taskdashboard.main;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import ro.codecamp.taskdashboard.connector.WorkItemService;
import ro.codecamp.taskdashboard.connector.stub.StubConnectorConfiguration;
import ro.codecamp.taskdashboard.service.JavaFXWorkItemService;
import ro.codecamp.taskdashboard.service.WorkItemToWorkItemModelConverter;
import ro.codecamp.taskdashboard.util.ConversionService;

import java.util.List;
import java.util.Properties;

public class TaskDashboardConfiguration {

    public WorkItemService workItemService() {
        return new StubConnectorConfiguration().buildWorkItemService(new Properties());
    }

//    public WorkItemService workItemService() {
//        Properties properties = new Properties();
////        properties.put(RedmineConnectorConfiguration.API_KEY, null);
//        properties.put(RedmineConnectorConfiguration.REDMINE_URL, "http://demo.redmine.org");
//        return new RedmineConnectorConfiguration().buildWorkItemService(properties);
//    }

    public JavaFXWorkItemService javaFXworkItemService() {
        return new JavaFXWorkItemService(conversionService(), workItemService());
    }

    private ConversionService conversionService() {
        return new ConversionService(getConverters());
    }

    private List<Function> getConverters() {
        return ImmutableList.<Function>builder()
                .add(new WorkItemToWorkItemModelConverter())
                .build();
    }
}
