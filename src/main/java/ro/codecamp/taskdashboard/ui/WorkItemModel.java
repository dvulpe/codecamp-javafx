package ro.codecamp.taskdashboard.ui;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkItemModel {
    private Property<String> title = new SimpleStringProperty();
    private Property<String> issueType = new SimpleStringProperty();
    private Property<String> workItemInfo = new SimpleStringProperty();
    private Property<String> asignee = new SimpleStringProperty();
    private Property<String> dueDate = new SimpleStringProperty();
    private Property<String> priority = new SimpleStringProperty();

    public Property<String> getTitle() {
        return title;
    }

    public Property<String> getIssueType() {
        return issueType;
    }

    public Property<String> getWorkItemInfo() {
        return workItemInfo;
    }

    public Property<String> getAsignee() {
        return asignee;
    }

    public Property<String> getDueDate() {
        return dueDate;
    }

    public void setTitle(StringProperty title) {
        this.title = title;
    }

    public void setIssueType(Property<String> issueType) {
        this.issueType = issueType;
    }

    public void setWorkItemInfo(Property<String> workItemInfo) {
        this.workItemInfo = workItemInfo;
    }

    public void setAsignee(Property<String> asignee) {
        this.asignee = asignee;
    }

    public void setDueDate(Property<String> dueDate) {
        this.dueDate = dueDate;
    }


    public Property<String> getPriority() {
        return priority;
    }

    public void bindTo(WorkItemModel workItem) {
        title.bindBidirectional(workItem.title);
        issueType.bindBidirectional(workItem.issueType);
        workItemInfo.bindBidirectional(workItem.workItemInfo);
        asignee.bindBidirectional(workItem.asignee);
        dueDate.bindBidirectional(workItem.dueDate);
        priority.bindBidirectional(workItem.priority);
    }
}
