package ro.codecamp.taskdashboard.connector.redmine;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.User;
import ro.codecamp.taskdashboard.connector.WorkItemService;
import ro.codecamp.taskdashboard.connector.dto.Project;
import ro.codecamp.taskdashboard.connector.dto.UserInfo;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;
import ro.codecamp.taskdashboard.util.ConversionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.partition;
import static com.google.common.collect.Lists.transform;

class RedmineWorkItemService implements WorkItemService {

    public static final int MAX_ITEMS = 20;
    private final RedmineManager redmineManager;
    private final ConversionService conversionService;

    public RedmineWorkItemService(RedmineManager redmineManager, ConversionService conversionService) {
        this.redmineManager = redmineManager;
        this.conversionService = conversionService;
    }

    @Override
    public List<WorkItem> getWorkItemsByProject(String projectId) {
        try {
            List<Issue> issues = redmineManager.getIssues(ImmutableMap.of("page", "1", "offset", "0",
                    "project_id", "codecamp"));
            List<Issue> copy = Lists.newArrayList(issues);
            Collections.sort(copy, new Comparator<Issue>() {
                @Override
                public int compare(Issue o1, Issue o2) {
                    return o2.getPriorityId().compareTo(o1.getPriorityId());
                }
            });
            return transform(partition(copy, MAX_ITEMS).get(0), new Function<Issue, WorkItem>() {
                @Override
                public WorkItem apply(Issue issue) {
                    return conversionService.convert(issue, WorkItem.class);
                }
            });
        } catch (RedmineException e) {
            throw new RuntimeException("Remote exception", e);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public WorkItem getWorkItem(String identifier) {
        try {
            Issue issue = redmineManager.getIssueById(Integer.parseInt(identifier));
            return conversionService.convert(issue, WorkItem.class);
        } catch (RedmineException e) {
            throw new RuntimeException("Remote exception", e);
        }
    }

    @Override
    public UserInfo getCurrentUser() {
        try {
            User user = redmineManager.getCurrentUser();
            return conversionService.convert(user, UserInfo.class);
        } catch (RedmineException e) {
            throw new RuntimeException("Remote exception", e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try {
            List<com.taskadapter.redmineapi.bean.Project> projects = redmineManager.getProjects();
            return transform(projects, new Function<com.taskadapter.redmineapi.bean.Project, Project>() {
                @Override
                public Project apply(com.taskadapter.redmineapi.bean.Project input) {
                    return conversionService.convert(input, Project.class);
                }
            });
        } catch (RedmineException e) {
            throw new RuntimeException("Remote exception", e);
        }
    }

}
