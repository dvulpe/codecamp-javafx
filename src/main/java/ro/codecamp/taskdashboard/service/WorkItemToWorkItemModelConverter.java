package ro.codecamp.taskdashboard.service;

import com.google.common.base.Function;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;
import ro.codecamp.taskdashboard.ui.WorkItemModel;

public class WorkItemToWorkItemModelConverter implements Function<WorkItem, WorkItemModel> {
    @Override
    public WorkItemModel apply(WorkItem input) {
        WorkItemModel workItemModel = new WorkItemModel();
        workItemModel.getPriority().setValue(input.getPriority());
        workItemModel.getWorkItemInfo().setValue(input.getTitle());
        workItemModel.getTitle().setValue(input.getType() + " " + input.getId());
        workItemModel.getIssueType().setValue(input.getType());
        workItemModel.getAsignee().setValue(input.getAsignee());
        workItemModel.getDueDate().setValue(input.getDueDate().toString("dd.MM.yyyy"));
        return workItemModel;
    }
}
