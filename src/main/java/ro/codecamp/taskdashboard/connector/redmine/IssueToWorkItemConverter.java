package ro.codecamp.taskdashboard.connector.redmine;

import com.google.common.base.Function;
import com.taskadapter.redmineapi.bean.Issue;
import org.joda.time.DateTime;
import ro.codecamp.taskdashboard.connector.dto.TrackingInfo;
import ro.codecamp.taskdashboard.connector.dto.WorkItem;

class IssueToWorkItemConverter implements Function<Issue, WorkItem> {
    @Override
    public WorkItem apply(Issue issue) {
        WorkItem.Builder builder = WorkItem.Builder.create()
                .withId(issue.getId())
                .withType(issue.getTracker().getName())
                .withTitle(issue.getSubject())
                .withDescription(issue.getDescription())
                .withCreatedAt(new DateTime(issue.getCreatedOn()))
                .withTrackingInfo(TrackingInfo.Builder.create()
                        .withEstimate(issue.getEstimatedHours())
                        .withCompleted(issue.getSpentHours())
                        .build())
                .withDueDate(new DateTime(issue.getDueDate()))
                .withPriority(issue.getPriorityText())
                .withStatus(issue.getStatusName());
        if (issue.getAssignee() != null) {
            builder.withAsignee(issue.getAssignee().getFullName());
        }
        return builder.build();
    }
}
