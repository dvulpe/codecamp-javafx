package ro.codecamp.taskdashboard.connector;

import ro.codecamp.taskdashboard.connector.dto.Project;
import ro.codecamp.taskdashboard.connector.dto.UserInfo;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;

import java.util.List;

public interface WorkItemService {
    List<WorkItem> getWorkItemsByProject(String projectId);

    WorkItem getWorkItem(String identifier);

    UserInfo getCurrentUser();

    List<Project> getAllProjects();
}
