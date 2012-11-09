package ro.codecamp.taskdashboard.connector.redmine;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.taskadapter.redmineapi.RedmineManager;
import ro.codecamp.taskdashboard.connector.ConnectorConfiguration;
import ro.codecamp.taskdashboard.connector.WorkItemService;
import ro.codecamp.taskdashboard.util.ConversionService;

import java.util.List;
import java.util.Properties;

public class RedmineConnectorConfiguration implements ConnectorConfiguration {

    public static final String REDMINE_URL = "REDMINE_URL";
    public static final String API_KEY = "API_KEY";

    @Override
    public WorkItemService buildWorkItemService(Properties configurationOptions) {
        String url = configurationOptions.getProperty(REDMINE_URL);
        String apiKey = configurationOptions.getProperty(API_KEY);
        return new RedmineWorkItemService(redmineManager(url, apiKey), conversionService());
    }

    private RedmineManager redmineManager(String url, String apiKey) {
        return new RedmineManager(url, apiKey);
    }

    private ConversionService conversionService() {
        return new ConversionService(converterList());
    }

    private List<Function> converterList() {
        return ImmutableList.<Function>builder()
                .add(new IssueToWorkItemConverter())
                .add(new UserToUserInfoConverter())
                .add(new RedmineProjectToProjectConverter())
                .build();
    }
}
