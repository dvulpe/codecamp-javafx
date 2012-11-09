package ro.codecamp.taskdashboard.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ro.codecamp.taskdashboard.connector.WorkItemService;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;
import ro.codecamp.taskdashboard.ui.WorkItemModel;
import ro.codecamp.taskdashboard.util.ConversionService;

import java.util.List;

public class JavaFXWorkItemService extends Service<List<WorkItemModel>> {
    private final ConversionService conversionService;
    private final WorkItemService workItemService;

    public JavaFXWorkItemService(ConversionService conversionService, WorkItemService workItemService) {
        this.conversionService = conversionService;
        this.workItemService = workItemService;
    }

    @Override
    protected Task<List<WorkItemModel>> createTask() {
        return new Task<List<WorkItemModel>>() {
            @Override
            protected List<WorkItemModel> call() throws Exception {
                List<WorkItem> workItems = workItemService.getWorkItemsByProject(null);
                return convert(workItems);
            }

            private List<WorkItemModel> convert(List<WorkItem> workItems) {
                return Lists.transform(workItems, new Function<WorkItem, WorkItemModel>() {
                    @Override
                    public WorkItemModel apply(WorkItem input) {
                        return conversionService.convert(input, WorkItemModel.class);
                    }
                });
            }
        };
    }
}
