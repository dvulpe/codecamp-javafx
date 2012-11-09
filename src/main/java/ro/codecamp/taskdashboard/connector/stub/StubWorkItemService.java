package ro.codecamp.taskdashboard.connector.stub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import ro.codecamp.taskdashboard.connector.WorkItemService;
import ro.codecamp.taskdashboard.connector.dto.Project;
import ro.codecamp.taskdashboard.connector.dto.UserInfo;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;

import java.security.SecureRandom;
import java.util.List;

public class StubWorkItemService implements WorkItemService {
    private SecureRandom randomGenerator = new SecureRandom();
    private String[] statuses = new String[]{"Urgent", "High", "Normal", "Low"};
    private String[] types = new String[]{"Task", "Bug", "Feature"};

    @Override
    public List<WorkItem> getWorkItemsByProject(String projectId) {
        ImmutableList.Builder<WorkItem> builder = ImmutableList.<WorkItem>builder();
        for (int i = 0; i < 20; i++) {
            builder.add(createWorkItem(types[randomGenerator.nextInt(types.length)],
                    statuses[randomGenerator.nextInt(statuses.length)],
                    randomGenerator.nextInt(1000), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vitae facilisis diam. Pellentesque dapibus placerat arcu in molestie. Vivamus dignissim, purus non dignissim dapibus"));
        }
        return builder.build();
    }

    private WorkItem createWorkItem(String type, String priority, Integer id, String title) {
        return WorkItem.Builder.create()
                .withType(type)
                .withPriority(priority)
                .withId(id)
                .withTitle(title)
                .withAsignee("John Doe")
                .withDueDate(new DateTime().plusDays(3))
                .build();
    }

    @Override
    public WorkItem getWorkItem(String identifier) {
        return null;
    }

    @Override
    public UserInfo getCurrentUser() {
        return UserInfo.Builder.create()
                .withEmail("john.doe@somedomain.com")
                .withFirstName("John")
                .withLastName("Doe")
                .withUsername("john.doe")
                .withUserId(1)
                .build();
    }

    @Override
    public List<Project> getAllProjects() {
        return Lists.newArrayList();
    }
}
