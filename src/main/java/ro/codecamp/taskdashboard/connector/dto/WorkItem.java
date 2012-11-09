package ro.codecamp.taskdashboard.connector.dto;

import org.joda.time.DateTime;

public final class WorkItem {
    private Integer id;
    private String type;
    private String title;
    private String description;
    private String asignee;
    private TrackingInfo trackingInfo;
    private DateTime createdAt;
    private String priority;
    private String status;
    private DateTime dueDate;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAsignee() {
        return asignee;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public TrackingInfo getTrackingInfo() {
        return trackingInfo;
    }

    public String getPriority() {
        return priority;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public static class Builder {
        private WorkItem workItem;

        private Builder() {
            workItem = new WorkItem();
        }

        public Builder withId(Integer id) {
            workItem.id = id;
            return this;
        }

        public Builder withType(String type) {
            workItem.type = type;
            return this;
        }

        public Builder withTitle(String title) {
            workItem.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            workItem.description = description;
            return this;
        }

        public Builder withAsignee(String asignee) {
            workItem.asignee = asignee;
            return this;
        }

        public Builder withTrackingInfo(TrackingInfo trackingInfo) {
            workItem.trackingInfo = trackingInfo;
            return this;
        }

        public Builder withCreatedAt(DateTime createdAt) {
            workItem.createdAt = createdAt;
            return this;
        }

        public Builder withPriority(String priority) {
            workItem.priority = priority;
            return this;
        }

        public Builder withStatus(String status) {
            workItem.status = status;
            return this;
        }

        public Builder withDueDate(DateTime dueDate) {
            workItem.dueDate = dueDate;
            return this;
        }

        public static Builder create() {
            return new Builder();
        }

        public WorkItem build() {
            return workItem;
        }
    }
}
